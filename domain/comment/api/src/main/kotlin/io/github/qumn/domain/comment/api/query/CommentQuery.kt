package io.github.qumn.domain.comment.api.query

import io.github.qumn.domain.comment.api.model.CommentId
import io.github.qumn.domain.comment.api.model.SubjectType

interface CommentQuery {
    fun tryQueryBy(cid: CommentId): CommentDetails?

    fun queryBy(subjectType: SubjectType, subjectId: Long): List<CommentDetails>
}