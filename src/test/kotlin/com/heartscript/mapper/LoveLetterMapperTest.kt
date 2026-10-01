package com.heartscript.mapper

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.entity.LoveLetter
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class LoveLetterMapperTest {
    private val mapper = LoveLetterMapper()

    @Test
    fun `should convert entity to response`() {
        val entity = LoveLetter(
            id = "1",
            senderName = "John",
            receiverName = "Emma",
            receiverNickname = "Em",
            relation = "Friend",
            occasion = "Birthday",
            tone = "Funny",
            signatureStyle = "With love,",
            title = "Happy Birthday",
            content = "Enjoy!",
            length = "Short",
            memories = "Coffee on rainy mornings",
            createdAt = Instant.now()
        )

        val response = mapper.toResponse(entity)

        assertEquals(entity.id, response.id)
        assertEquals(entity.title, response.title)
        assertEquals(entity.content, response.content)
        assertEquals(entity.relation, response.relation)
        assertEquals(entity.createdAt, response.createdAt)
    }

    @Test
    fun `should convert request and content to entity`() {
        val request = CreateLoveLetterRequest(
            senderName = "John",
            receiverName = "Emma",
            receiverNickname = "Em",
            relation = "Friend",
            occasion = "Birthday",
            tone = "Funny",
            signatureStyle = "With love,",
            length = "Short",
            memories = "Coffee on rainy mornings"
        )

        val entity = mapper.toEntity(request, "Generated letter content")

        assertEquals("John", entity.senderName)
        assertEquals("Emma", entity.receiverName)
        assertEquals("Em", entity.receiverNickname)
        assertEquals("To Emma", entity.title)
        assertEquals("Generated letter content", entity.content)
        assertEquals("Coffee on rainy mornings", entity.memories)
    }
}