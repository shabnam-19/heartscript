package com.heartscript.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class CreateLoveLetterRequest (

    @field:NotBlank
    @Schema(example = "Shabnam")
    val senderName: String,

    @Schema(example = "Surya")
    @field:NotBlank
    val receiverName: String,

    val receiverNickname: String? = null,

    @Schema(example = "Husband")
    @field:NotBlank
    val relation: String,

    @Schema(example = "LongDistance")
    @field:NotBlank
    val occasion: String,

    @Schema(example = "Harsh")
    @field:NotBlank
    val tone: String,

    @field:NotBlank
    val signatureStyle: String,

    @field:NotBlank
    val length: String,

    @field:NotBlank
    val memories: String
)