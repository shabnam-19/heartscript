package com.heartscript.repository

import com.heartscript.entity.LoveLetter
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LoveLetterRepository: ReactiveMongoRepository<LoveLetter, String> {
}