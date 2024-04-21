package io.github.qumn.domain.comment.api.model

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.util.time.nowMicros

class CommentFactory {
    companion object {
        fun create(
            uid: UID,
            subjectType: SubjectType,
            subjectId: Long,
            content: String,
            replayTo: Comment? = null,
        ): Comment {
            return Comment(
                id = CommentId.generate(),
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