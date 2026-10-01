package com.heartscript.util

import com.heartscript.dto.CreateLoveLetterRequest

object PromptBuilder {

    fun build(request: CreateLoveLetterRequest): String {

        return """
You are HeartScript AI.

Your task is to write a heartfelt, authentic, human-sounding letter.

Relationship:
${request.relation}

Occasion:
${request.occasion}

Writing Style:
${request.tone}

Letter Length:
${request.length}

Recipient:
${request.receiverName}

Nickname:
${request.receiverNickname}

Sender:
${request.senderName}

Signature:
${request.signatureStyle}

Special Memories:
${request.memories}

Requirements:

- Make it warm and emotional.
- Mention the memories naturally.
- Do not sound robotic.
- Avoid repeating sentences.
- Keep the writing elegant.
- Address the recipient by their name or nickname naturally.
- End exactly with:

${request.signatureStyle}
${request.senderName}

Return ONLY the letter.

""".trimIndent()

    }

}