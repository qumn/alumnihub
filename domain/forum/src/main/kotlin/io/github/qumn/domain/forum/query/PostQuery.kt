package io.github.qumn.domain.forum.query

import io.github.qumn.domain.forum.model.PostId
import io.github.qumn.framework.exception.BizNotAllowedException
import io.github.qumn.ktorm.page.Page
import io.github.qumn.ktorm.page.PageParam
import io.github.qumn.ktorm.search.LIKE
import io.github.qumn.ktorm.search.Operation

class PostPageParam(
    @Operation(LIKE::class)
    val title: String? = null,
    @Operation(LIKE::class)
    val content: String? = null,

    pageNo: Int = 1,
    pageSize: Int = 10,
    isCount: Boolean = false,
) : PageParam(pageNo, pageSize, isCount)

interface PostQuery {
    fun page(param: PostPageParam): Page<PostDetails>

    fun queryBy(postId: PostId): PostDetails {
        return tryQueryBy(postId) ?: throw BizNotAllowedException("找不到帖子")
    }

    fun tryQueryBy(postId: PostId): PostDetails?
}