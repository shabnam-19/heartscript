package com.heartscript.service

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface LoveLetterService {
    fun generateLoveLetter(request: CreateLoveLetterRequest): Mono<LoveLetterResponse>
    fun getLetterById(id: String): Mono<LoveLetterResponse>
    fun getAllLetter(): Flux<LoveLetterResponse>
    fun deleteById(id: String): Mono<Void>
    fun deleteAll(): Mono<Void>
}