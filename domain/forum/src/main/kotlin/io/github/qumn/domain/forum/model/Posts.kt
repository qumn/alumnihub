package io.github.qumn.domain.forum.model

import io.github.qumn.framework.exception.BizNotAllowedException

interface Posts {
    fun findBy(postId: PostId): Post {
        return tryFindBy(postId) ?: throw BizNotAllowedException("帖子不见了, 看看其他的")
    }

    fun tryFindBy(postId: PostId): Post?

    fun save(post: Post)

    fun delete(postId: PostId)

    fun contains(postId: PostId): Boolean
}