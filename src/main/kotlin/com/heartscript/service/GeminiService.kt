package com.heartscript.service

import reactor.core.publisher.Mono

interface GeminiService {
    fun generateLetter(prompt: String): Mono<String>
}