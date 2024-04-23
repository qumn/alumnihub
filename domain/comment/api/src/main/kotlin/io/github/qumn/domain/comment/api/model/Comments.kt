package io.github.qumn.domain.comment.api.model

import io.github.qumn.framework.exception.BizNotAllowedException

interface Comments {
    fun save(comment: Comment)

    fun delete(id: CommentId)

    fun findById(id: CommentId): Comment {
        return tryFindById(id) ?: throw BizNotAllowedException("评论不存在")
    }

    fun findBy(subjectType: SubjectType, sid: Long): List<Comment>

    fun tryFindById(id: CommentId): Comment?
}