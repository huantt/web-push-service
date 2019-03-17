package com.huantt.webpush.business.logic

import com.huantt.webpush.business.model.Client
import com.huantt.webpush.business.transformer.ClientTransformer
import com.huantt.webpush.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

/**
 * @author huantt on 11/15/18
 */

@Service
class ClientLogic {

    @Autowired
    HttpServletResponse response
    @Autowired
    ClientRepository clientRepository
    @Autowired
    ClientTransformer transformer

    Map save(Client client) {
        transformer.fillClientInfo(client)
        transformer.trackUniqueClient(client)

        Client insertedClient = clientRepository.save(client)
        return ['_id': insertedClient._id.toString()]
    }
}
