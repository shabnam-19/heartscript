package com.heartscript.service

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.entity.LoveLetter
import com.heartscript.mapper.LoveLetterMapper
import com.heartscript.repository.LoveLetterRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Instant
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class LoveLetterServiceTest {

    @Mock
    lateinit var repository: LoveLetterRepository

    @Mock
    lateinit var mapper: LoveLetterMapper

    @Mock
    lateinit var geminiService: GeminiService

    @InjectMocks
    lateinit var service: LoveLetterServiceImpl

    private val sampleEntity = LoveLetter(
        id = "1",
        senderName = "John",
        receiverName = "Emma",
        receiverNickname = "Em",
        relation = "Friend",
        occasion = "Birthday",
        tone = "Funny",
        signatureStyle = "Yours,",
        title = "To Emma",
        content = "Happy Birthday!",
        length = "Short",
        memories = "First meeting in Paris",
        createdAt = Instant.parse("2026-07-21T10:15:30Z")
    )

    private val sampleResponse = LoveLetterResponse(
        id = "1",
        title = "To Emma",
        content = "Happy Birthday!",
        relation = "Friend",
        createdAt = Instant.parse("2026-07-21T10:15:30Z")
    )

    @Test
    fun `should get all letters successfully`() {
        Mockito.`when`(repository.findAll())
            .thenReturn(Flux.just(sampleEntity))

        Mockito.`when`(mapper.toResponse(sampleEntity))
            .thenReturn(sampleResponse)

        StepVerifier.create(service.getAllLetter())
            .expectNext(sampleResponse)
            .verifyComplete()
    }

    @Test
    fun `should throw 404 when get all letters is empty`() {
        Mockito.`when`(repository.findAll())
            .thenReturn(Flux.empty())

        StepVerifier.create(service.getAllLetter())
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `should get letter by receiver successfully`() {
        Mockito.`when`(repository.findByReceiverName("Emma"))
            .thenReturn(Flux.just(sampleEntity))

        Mockito.`when`(mapper.toResponse(sampleEntity))
            .thenReturn(sampleResponse)

        StepVerifier.create(service.getLetterByReceiver("Emma"))
            .expectNext(sampleResponse)
            .verifyComplete()
    }

    @Test
    fun `should generate and save love letter`() {
        val request = CreateLoveLetterRequest(
            senderName = "John",
            receiverName = "Emma",
            receiverNickname = "Em",
            relation = "Friend",
            occasion = "Birthday",
            tone = "Funny",
            signatureStyle = "Yours,",
            length = "Short",
            memories = "First meeting in Paris"
        )

        Mockito.`when`(geminiService.generateLetter(Mockito.anyString()))
            .thenReturn(Mono.just("AI generated content"))

        Mockito.`when`(mapper.toEntity(request, "AI generated content"))
            .thenReturn(sampleEntity)

        Mockito.`when`(repository.save(sampleEntity))
            .thenReturn(Mono.just(sampleEntity))

        Mockito.`when`(mapper.toResponse(sampleEntity))
            .thenReturn(sampleResponse)

        StepVerifier.create(service.generateLoveLetter(request))
            .expectNext(sampleResponse)
            .verifyComplete()
    }
}