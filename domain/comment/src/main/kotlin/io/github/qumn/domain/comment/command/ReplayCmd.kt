package io.github.qumn.domain.comment.command

import io.github.qumn.domain.comment.api.model.CommentId
import io.github.qumn.domain.system.api.user.model.UID


data class ReplayCmd(
    val replayTo: CommentId,
    val replayer: UID,
    val content: String,
)