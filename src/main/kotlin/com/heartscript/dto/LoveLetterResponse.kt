package com.heartscript.dto

import java.time.Instant

data class LoveLetterResponse (

    val id: String?,
    val title: String,
    val content: String,
    val createdAt: Instant
)