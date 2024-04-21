package io.github.qumn.domain.comment.api.query

import io.github.qumn.domain.comment.api.model.CommentId
import io.github.qumn.domain.system.api.user.query.UserDetails
import java.time.Instant

data class CommentDetails(
    val id: CommentId,
    var pid: CommentId?,
    val commenter: UserDetails,
    val content: String,
    val thumpUpCount: Int,
    val parent: CommentDetails?,
    val replays: List<CommentDetails>,
    val createAt: Instant,
)
