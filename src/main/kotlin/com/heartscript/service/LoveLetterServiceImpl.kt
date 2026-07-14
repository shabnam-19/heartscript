package com.heartscript.service

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.mapper.LoveLetterMapper
import com.heartscript.repository.LoveLetterRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class LoveLetterServiceImpl(
    private val repository: LoveLetterRepository,
    private val mapper: LoveLetterMapper
): LoveLetterService{
    override fun generateLoveLetter(request: CreateLoveLetterRequest): Mono<LoveLetterResponse> {
        val entity = mapper.toEntity(request)
        return repository
            .save(entity)
            .map(mapper::toResponse)
    }
}