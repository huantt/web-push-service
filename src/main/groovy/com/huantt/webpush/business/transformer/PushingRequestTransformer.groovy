package com.huantt.webpush.business.transformer

import com.huantt.webpush.business.model.PushingRequest
import com.huantt.webpush.repository.ClientRepository
import com.huantt.webpush.business.model.Client
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

/**
 * @author huantt on 11/15/18
 */
@Component
class PushingRequestTransformer {

    final int FIRST_PAGE_INDEX = 0, BATCH_SIZE = 1000

    @Autowired
    ClientRepository clientRepository

    public void getPushingTokens(PushingRequest pushingRequest, Closure<List<String>> closure) {
        Document filter = pushingRequest.filter
        filter.append('active', true)

        Pageable pageable = new PageRequest(FIRST_PAGE_INDEX, BATCH_SIZE)
        Page<Client> page = this.clientRepository.getTokensByFilter(filter, pageable)
        closure.call(page.toList().collect { it.token })

        while (page.hasNext()) {
            page = this.clientRepository.getTokensByFilter(filter, page.nextPageable())
            closure.call(page.toList().collect { it.token })
        }
    }

    public Long getNumberOfActiveTokens(PushingRequest pushingRequest) {
        Document filter = pushingRequest.filter
        filter.append('active', true)
        return this.clientRepository.countByFilter(filter)
    }

    public void transformFilter(PushingRequest pushingRequest) {
        pushingRequest.filter = new Document("string_filter", pushingRequest.filter.toJson())
    }
}
