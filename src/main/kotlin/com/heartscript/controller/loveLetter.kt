package com.heartscript.controller

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.service.LoveLetterService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/letters")
class loveLetter(
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
}