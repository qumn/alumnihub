package io.github.qumn.domain.comment.command

data class ReplayCmd(
    val replayToId: Long,
    val replayerId: Long,
    val content: String
)