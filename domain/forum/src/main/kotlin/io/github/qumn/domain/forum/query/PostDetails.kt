package io.github.qumn.domain.forum.query

import io.github.qumn.domain.forum.model.PostId
import io.github.qumn.domain.forum.model.Tag
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URL
import java.time.Instant

data class PostDetails(
    val id: PostId,
    val creatorId: UID,
    val title: String,
    val content: String,
    val tags: List<Tag>,
    val imgs: List<URL>,
    val thumbUpBy: List<UID>,
    val sharedBy: List<UID>,
    val createdAt: Instant,
)