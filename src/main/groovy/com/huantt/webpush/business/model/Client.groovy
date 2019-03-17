package com.huantt.webpush.business.model


import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author huantt on 11/6/18
 */
@Document(collection = 'clients')
public class Client {

    Client() {
        _id = new ObjectId()
        firstTimeId = _id.toString()
        subscribedTimes = 1
        createdAt = new Date()
        modifiedAt = new Date()
    }

    @Id
    ObjectId _id
    String firstTimeId
    Integer subscribedTimes
    String token
    Map<String, Object> subscription
    Map<String, String> tags

    Boolean active = true
    String inactiveReason

    String ip
    String userAgent

    String referer

    Date createdAt
    Date modifiedAt
    String modifiedBy // Pushing request's _id
}
