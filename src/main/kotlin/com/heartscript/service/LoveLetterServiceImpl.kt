package com.heartscript.service

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.mapper.LoveLetterMapper
import com.heartscript.repository.LoveLetterRepository
import com.heartscript.util.PromptBuilder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class LoveLetterServiceImpl(
    private val repository: LoveLetterRepository,
    private val mapper: LoveLetterMapper,
    private val geminiService: GeminiService
): LoveLetterService{

    override fun generateLoveLetter(
        request: CreateLoveLetterRequest
    ): Mono<LoveLetterResponse> {
        val prompt = PromptBuilder.build(request)
        return geminiService
            .generateLetter(prompt)
            .flatMap { generatedLetter ->
                val entity = mapper.toEntity(request, generatedLetter)
                repository.save(entity)
            }
            .map(mapper::toResponse)
    }

    override fun getAllLetter(): Flux<LoveLetterResponse> {
        return repository
            .findAll()
            .switchIfEmpty(
                Flux.error(
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No Letters Present"
                    )
                )
            )
            .map(mapper::toResponse)
    }

    override fun getLetterByReceiver(receiver: String): Flux<LoveLetterResponse> {
        return repository
            .findByReceiverName(receiver)
            .switchIfEmpty(
                Flux.error(
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No Letters Present"
                    )
                )
            )
            .map(mapper::toResponse)
    }
}