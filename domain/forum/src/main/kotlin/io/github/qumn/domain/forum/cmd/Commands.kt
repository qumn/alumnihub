package io.github.qumn.domain.forum.cmd

import io.github.qumn.domain.forum.model.PostId
import io.github.qumn.domain.forum.model.Tag
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URN

data class CreatePostCmd(
    val creator: UID,
    val title: String,
    val content: String,
    val tags: List<Tag> = listOf(),
    val imgs: List<URN> = listOf(),
)

data class LikePostCmd(
    val likedBy: UID,
    val postId: PostId,
)

data class SharePostCmd(
    val sharer: UID,
    val postId: PostId,
)

data class CreateCommentCmd(
    val commenter: UID,
    val postId: PostId,
    val content: String,
)
