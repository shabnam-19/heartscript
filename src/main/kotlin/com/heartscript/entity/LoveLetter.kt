package com.heartscript.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "love_letters")
data class LoveLetter (

    @Id
    val id: String? = null,
    val senderName: String,
    val receiverName: String,
    val receiverNickname: String? = null,
    val relation: String,
    val occasion: String,
    val tone: String,
    val signatureStyle: String? = null,
    val title: String,
    val content: String,
    val length: String? = null,
    val memories: String,
    val createdAt: Instant
)
