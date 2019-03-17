package com.huantt.webpush.controller


import com.huantt.webpush.business.logic.NotificationLogic
import com.huantt.webpush.business.model.PushingRequest
import com.huantt.webpush.business.transformer.PushingRequestTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author huantt on 11/5/18
 */

@RestController
@RequestMapping("/notifications")
class NotificationController {

    @Autowired
    PushingRequestTransformer pushingRequestTransformer
    @Autowired
    NotificationLogic business

    @PostMapping("/push")
    Map push(@RequestBody PushingRequest pushingRequest) {
        pushingRequestTransformer.getPushingTokens(pushingRequest, { List<String> clientTokens ->
            business.push(pushingRequest, clientTokens)
        })
        Long numberOfActiveTokens = pushingRequestTransformer.getNumberOfActiveTokens(pushingRequest)
        pushingRequestTransformer.transformFilter(pushingRequest)
        business.savePushingRequest(pushingRequest)

        return ["total_count": numberOfActiveTokens]
    }

    @PostMapping("/preview")
    Map preview(@RequestBody PushingRequest pushingRequest) {
        Long numberOfActiveTokens = pushingRequestTransformer.getNumberOfActiveTokens(pushingRequest)
        return ["total_count": numberOfActiveTokens]
    }
}
