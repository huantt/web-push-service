package com.huantt.webpush.repository

import com.huantt.webpush.business.model.Client
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

/**
 * @author huantt on 11/6/18
 */

interface ClientRepository extends MongoRepository<Client, ObjectId> {

    @Query(value = "?0")
    Page<Client> getClientsByFilter(Document filter, Pageable pageable)

    @Query(value = "?0", count = true)
    Long countByFilter(Document filter)

    @Query(value = "?0", fields = "{token:1}")
    Page<Client> getTokensByFilter(Document filter, Pageable pageable)

}
