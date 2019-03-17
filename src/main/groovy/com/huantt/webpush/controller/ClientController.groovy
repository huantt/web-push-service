package com.huantt.webpush.controller

import com.huantt.webpush.business.logic.ClientLogic
import com.huantt.webpush.business.model.Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author huantt on 11/5/18
 */

@RestController
@RequestMapping("/clients")
class ClientController {

    @Autowired
    ClientLogic clientLogic


    @PostMapping()
    @CrossOrigin(origins = "*", allowCredentials = "true")
    Map create(@RequestBody Client client) {
        return clientLogic.save(client)
    }
}
