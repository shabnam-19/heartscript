package com.heartscript.mapper

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.entity.LoveLetter
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class LoveLetterMapper {

    fun toEntity(request: CreateLoveLetterRequest): LoveLetter {
        return LoveLetter(
            senderName = request.senderName,
            receiverName = request.receiverName,
            relation = request.relation,
            occasion = request.occasion,
            tone = request.tone,
            title = "To ${request.receiverName}",
            content = """
                Dear ${request.receiverName}
                You are best Part of my life
                with Love,
                from ${request.senderName}
            """.trimIndent(),
            createdAt = Instant.now(),

            )
    }

    fun toResponse(entity: LoveLetter): LoveLetterResponse {
        return LoveLetterResponse(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            createdAt = Instant.now(),
        )
    }
}