package com.huantt.webpush.repository

import com.huantt.webpush.business.model.PushingRequest
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author huantt on 2018-12-06
 */
interface PushingRequestRepository extends MongoRepository<PushingRequest, ObjectId> {

}
