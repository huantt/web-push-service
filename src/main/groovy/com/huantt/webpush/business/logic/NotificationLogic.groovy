package com.huantt.webpush.business.logic

import com.huantt.common.webpush.NotificationSender
import com.huantt.webpush.business.model.Client
import com.huantt.webpush.business.model.PushingRequest
import com.huantt.webpush.repository.ClientRepository
import com.huantt.webpush.repository.PushingRequestRepository
import com.huantt.webpush.repository.PushingResponseRepository
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

/**
 * @author huantt on 11/15/18
 */
@Service
class NotificationLogic {

    final int FIRST_PAGE_INDEX = 0, BATCH_SIZE = 1000

    @Value('${firebase.serverkey}')
    NotificationSender notificationSender
    @Autowired
    PushingResponseRepository pushingResponseRepository
    @Autowired
    PushingRequestRepository pushingRequestRepository
    @Autowired
    ClientRepository clientRepository
    @Autowired
    MongoTemplate mongoTemplate

    void savePushingRequest(PushingRequest pushingRequest) {
        pushingRequestRepository.save(pushingRequest)
    }

    void push(PushingRequest pushingRequest, List<String> clientTokens) {
        notificationSender.push(pushingRequest.notification, clientTokens, { pushingResponse ->
            pushingResponse.setPushingRequestId(pushingRequest._id)
            pushingResponseRepository.save(pushingResponse)

            if (pushingResponse.error) return

            List<Map> successResults = pushingResponse.results.findAll { !it.error }
            List<String> successTokens = successResults.collect { it.token }
            this.incPushedTimes(successTokens)

            List<Map> errorResults = pushingResponse.results.findAll { it.error }
            this.updateErrorClients(pushingRequest._id.toString(), errorResults)
        })
    }

    private void updateErrorClients(String pushingRequestId, List<Map> errorResults) {
        List<String> tokens = errorResults.collect { it.token }
        Map<String, String> errorResultsMap = errorResults.collectEntries { [(it.token): (it.error)] }
        Document filter = ["token": ['$in': tokens]]
        Closure updateErrorClients = { List<Client> errorClients ->
            Date modifiedAt = new Date()
            errorClients.each { client ->
                client.with {
                    setModifiedAt(modifiedAt)
                    setModifiedBy(pushingRequestId)
                    setActive(false)
                    setInactiveReason(errorResultsMap[client.token])
                }

            }
            clientRepository.saveAll(errorClients)
        }

        Pageable pageable = new PageRequest(FIRST_PAGE_INDEX, BATCH_SIZE)
        Page<Client> page = this.clientRepository.getClientsByFilter(filter, pageable)
        updateErrorClients.call(page.toList())

        while (page.hasNext()) {
            page = this.clientRepository.getClientsByFilter(filter, page.nextPageable())
            updateErrorClients.call(page.toList())
        }
    }

    private void incPushedTimes(List clientTokens) {
        Query query = new Query(Criteria.where('token').in(clientTokens))
        Update update = new Update().inc('tags.pushed_times', 1)
        mongoTemplate.updateMulti(query, update, Client.class)
    }
}