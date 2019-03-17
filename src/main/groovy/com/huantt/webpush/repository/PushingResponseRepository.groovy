package com.huantt.webpush.repository


import com.huantt.webpush.business.model.PushingResponse
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author huantt on 11/7/18
 */
interface PushingResponseRepository extends MongoRepository<PushingResponse, ObjectId> {

    Page<PushingResponse> findAll(Pageable pageable)
}
