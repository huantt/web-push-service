package com.huantt.webpush.business.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

/**
 * @author huantt on 2018-12-06
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@org.springframework.data.mongodb.core.mapping.Document(collection = "pushing_requests")
class PushingRequest extends com.huantt.common.webpush.PushingRequest {

    PushingRequest() {
        this._id = new ObjectId()
        this.time = new Date()
    }

    @Id
    ObjectId _id
    Document filter

    String campaignName
    String campaignDescription
}
