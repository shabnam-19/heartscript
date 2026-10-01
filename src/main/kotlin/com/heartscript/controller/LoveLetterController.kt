package com.heartscript.controller

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.service.LoveLetterService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Tag(name = "Love Letters", description = "Endpoints for generating and retrieving love letters")
@RestController
@RequestMapping("/api/v1/letters")
class LoveLetterController(
    private val service: LoveLetterService
) {
    private val logger = LoggerFactory.getLogger(LoveLetterController::class.java)

    @Operation(summary = "Create a Love Letter")
    @PostMapping
    fun generateLoveLetter(
        @Valid
        @RequestBody request: CreateLoveLetterRequest
    ): Mono<LoveLetterResponse> {
        logger.info("Generating love letter for receiver: {}", request.receiverName)
        return service.generateLoveLetter(request)
    }

    @Operation(summary = "Get all Love Letters")
    @GetMapping
    fun getAllLetter(): Flux<LoveLetterResponse> {
        logger.info("Fetching all love letters")
        return service.getAllLetter()
    }

    @Operation(summary = "Get Love Letters by Receiver")
    @GetMapping("/receiver/{receiver}")
    fun getLetterByReceiver(
        @PathVariable("receiver") receiver: String
    ): Flux<LoveLetterResponse> {
        logger.info("Fetching love letters for receiver: {}", receiver)
        return service.getLetterByReceiver(receiver)
    }
}
