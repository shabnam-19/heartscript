package com.heartscript.mapper

import com.heartscript.dto.CreateLoveLetterRequest
import com.heartscript.dto.LoveLetterResponse
import com.heartscript.entity.LoveLetter
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class LoveLetterMapper {

    fun toEntity(
        request: CreateLoveLetterRequest,
        generatedContent: String
    ): LoveLetter {
        return LoveLetter(
            senderName = request.senderName,
            receiverName = request.receiverName,
            receiverNickname = request.receiverNickname,
            relation = request.relation,
            occasion = request.occasion,
            tone = request.tone,
            title = "To ${request.receiverName}",
            content = generatedContent,
            length = request.length,
            signatureStyle = request.signatureStyle,
            memories = request.memories,
            createdAt = Instant.now()
        )
    }

    fun toResponse(entity: LoveLetter): LoveLetterResponse {
        return LoveLetterResponse(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            relation = entity.relation,
            createdAt = entity.createdAt
        )
    }
}