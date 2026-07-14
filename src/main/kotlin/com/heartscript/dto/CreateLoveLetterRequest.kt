package com.heartscript.dto

import jakarta.validation.constraints.NotBlank

data class CreateLoveLetterRequest (

    @field:NotBlank
    val senderName: String,

    @field:NotBlank
    val receiverName: String,

    @field:NotBlank
    val relation: String,

    @field:NotBlank
    val occasion: String,

    @field:NotBlank
    val tone: String
)