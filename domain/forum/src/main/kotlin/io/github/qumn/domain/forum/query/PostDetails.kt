package io.github.qumn.domain.forum.query

import io.github.qumn.domain.forum.model.PostId
import io.github.qumn.domain.forum.model.Tag
import java.time.Instant

data class PostDetails(
    val id: PostId,
    val title: String,
    val content: String,
    val tags: List<Tag>,
    val imgs: List<String>,
    val creator: Long,
    val creatorName: String,
    val creatorAvatar: String,
    val thumbUpCount: Int,
    val commentCount: Int,
    val shareCount: Int,
    val createdAt: Instant,
)