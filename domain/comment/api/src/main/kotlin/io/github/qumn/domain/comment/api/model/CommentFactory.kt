package io.github.qumn.domain.comment.api.model

import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId
import java.time.Instant

class CommentFactory {
    companion object {
        fun create(
            uid: Long,
            subjectType: SubjectType,
            subjectId: Long,
            content: String,
            replayTo: Long? = null,
        ): Comment {
            val commentId = IdUtil.nextId()

            return Comment(
                id = commentId,
                replayTo = null,
                commenterId = uid,
                subjectType = subjectType,
                subjectId = subjectId,
                content = content,
                createAt = Instant.now(),
                likedUids = setOf(),
                replays = listOf()
            )
        }
    }
}