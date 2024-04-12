package io.github.qumn.domain.comment.api.model

import io.github.qumn.framework.exception.BizNotAllowedException

interface Comments {
    fun save(comment: Comment)

    fun delete(id: Long)

    fun findById(id: Long): Comment {
        return tryFindById(id) ?: throw BizNotAllowedException("评论不存在")
    }

    fun findBySubjectId(subjectType: SubjectType, sid: Long) : List<Comment>

    fun tryFindById(id: Long): Comment?
}