package com.heartscript.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.heartscript.dto.GeminiResponse
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class GeminiServiceImpl(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,

    @Value("\${gemini.api.key}")
    private val apiKey: String,

    @Value("\${gemini.model}")
    private val model: String

) : GeminiService {

    private val logger = LoggerFactory.getLogger(GeminiServiceImpl::class.java)

    @PostConstruct
    fun verifyConfiguration() {
        logger.info("Configured Gemini model: {}", model)
        if (apiKey.isBlank()) {
            logger.warn("Gemini API key is not configured!")
        } else {
            logger.info("Gemini API key is present and configured.")
        }
    }

    override fun generateLetter(prompt: String): Mono<String> {
        val requestBody = mapOf(
            "contents" to listOf(
                mapOf(
                    "parts" to listOf(
                        mapOf(
                            "text" to prompt
                        )
                    )
                )
            )
        )

        return webClient
            .post()
            .uri("/v1beta/models/$model:generateContent?key=$apiKey")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchangeToMono { response ->
                response.bodyToMono(String::class.java)
                    .flatMap { body ->
                        logger.debug("Gemini response status: {}", response.statusCode())

                        if (response.statusCode().is2xxSuccessful) {
                            try {
                                val gemini = objectMapper.readValue(body, GeminiResponse::class.java)
                                val text = gemini.candidates
                                    .firstOrNull()
                                    ?.content
                                    ?.parts
                                    ?.firstOrNull()
                                    ?.text

                                if (text.isNullOrBlank()) {
                                    Mono.error(
                                        IllegalStateException(
                                            "Gemini API returned an empty response or content was filtered."
                                        )
                                    )
                                } else {
                                    Mono.just(text)
                                }
                            } catch (e: Exception) {
                                logger.error("Failed to parse Gemini response: {}", body, e)
                                Mono.error(e)
                            }
                        } else {
                            logger.error("Gemini API returned error status {}: {}", response.statusCode(), body)
                            Mono.error(RuntimeException("Gemini API error: ${response.statusCode()} - $body"))
                        }
                    }
            }
    }
}