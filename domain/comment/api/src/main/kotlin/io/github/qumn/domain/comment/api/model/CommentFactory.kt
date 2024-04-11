package io.github.qumn.domain.comment.api.model

import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId
import io.github.qumn.util.time.nowMicros
import java.time.Instant

class CommentFactory {
    companion object {
        fun create(
            uid: Long,
            subjectType: SubjectType,
            subjectId: Long,
            content: String,
            replayTo: Comment? = null,
        ): Comment {
            val commentId = IdUtil.nextId()

            return Comment(
                id = commentId,
                replayTo = replayTo,
                commenterId = uid,
                subjectType = subjectType,
                subjectId = subjectId,
                content = content,
                createAt = nowMicros(),
                likedUids = setOf(),
                replays = listOf()
            )
        }
    }
}