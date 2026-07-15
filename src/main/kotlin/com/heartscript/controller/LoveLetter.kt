package com.heartscript.controller

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.service.LoveLetterService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/letters")
class LoveLetter(
    @Autowired
    private val service: LoveLetterService
) {

    @PostMapping
    fun generateLoveLetter(
        @Valid
        @RequestBody request: CreateLoveLetterRequest
    )
    : Mono<LoveLetterResponse> {
        return service.generateLoveLetter(request)
    }

    @GetMapping("/{id}")
    fun getLetterById(
        @PathVariable("id") id: String
    )
            : Mono<LoveLetterResponse> {
        return service.getLetterById(id)
    }

    @GetMapping
    fun getAllLetter(): Flux<LoveLetterResponse> {
        return service.getAllLetter()
    }
}