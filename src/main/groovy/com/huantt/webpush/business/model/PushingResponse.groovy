package com.huantt.webpush.business.model

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author huantt on 2018-12-06
 */
@Builder(builderStrategy = SimpleStrategy)
@Document(collection = "pushing_responses")
class PushingResponse extends com.huantt.common.webpush.PushingResponse {

    PushingResponse() {
        this._id = new ObjectId()
        this.time = new Date()
    }


    @Id
    ObjectId _id
    ObjectId pushingRequestId
}
