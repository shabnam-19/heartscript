package com.heartscript.controller

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.service.LoveLetterService
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant
import kotlin.test.Test

@WebFluxTest(LoveLetterController::class)
class LoveLetterControllerTest {

    @Autowired
    lateinit var client: WebTestClient

    @MockitoBean
    lateinit var service: LoveLetterService

    private val sampleResponse = LoveLetterResponse(
        id = "687a1f8d3c8b4f2a9d7e1234",
        title = "To My Dearest",
        content = "Every moment with you is a beautiful memory.",
        relation = "Husband",
        createdAt = Instant.parse("2026-07-21T10:15:30Z")
    )

    @Test
    fun shouldGetAllLetters() {
        Mockito.`when`(service.getAllLetter())
            .thenReturn(Flux.just(sampleResponse))

        client.get()
            .uri("/api/v1/letters")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo("687a1f8d3c8b4f2a9d7e1234")
            .jsonPath("$[0].title").isEqualTo("To My Dearest")
            .jsonPath("$[0].relation").isEqualTo("Husband")
    }

    @Test
    fun shouldGetLetterByReceiver() {
        Mockito.`when`(service.getLetterByReceiver("Surya"))
            .thenReturn(Flux.just(sampleResponse))

        client.get()
            .uri("/api/v1/letters/receiver/Surya")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo("687a1f8d3c8b4f2a9d7e1234")
            .jsonPath("$[0].title").isEqualTo("To My Dearest")
    }

    @Test
    fun shouldGenerateLoveLetter() {
        val request = CreateLoveLetterRequest(
            senderName = "Shabnam",
            receiverName = "Surya",
            receiverNickname = "Suri",
            relation = "Husband",
            occasion = "Anniversary",
            tone = "Romantic",
            signatureStyle = "Forever yours,",
            length = "Medium",
            memories = "Our first trip to the mountains"
        )

        Mockito.`when`(service.generateLoveLetter(request))
            .thenReturn(Mono.just(sampleResponse))

        client.post()
            .uri("/api/v1/letters")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("687a1f8d3c8b4f2a9d7e1234")
            .jsonPath("$.title").isEqualTo("To My Dearest")
    }
}