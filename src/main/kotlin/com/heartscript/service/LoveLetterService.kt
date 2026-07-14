package com.heartscript.service

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import reactor.core.publisher.Mono

interface LoveLetterService {
    fun generateLoveLetter(request: CreateLoveLetterRequest): Mono<LoveLetterResponse>
}