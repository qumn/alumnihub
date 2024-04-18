package io.github.qumn.domain.comment.command

import io.github.qumn.domain.system.api.user.model.UID


data class ReplayCmd(
    val replayToId: Long,
    val replayerId: UID,
    val content: String
)