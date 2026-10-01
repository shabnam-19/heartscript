package com.heartscript.repository

import com.heartscript.entity.LoveLetter
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface LoveLetterRepository: ReactiveMongoRepository<LoveLetter, String> {
    fun findByReceiverName(receiverName: String): Flux<LoveLetter>
}