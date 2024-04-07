package io.github.qumn.domain.trade.command

import java.time.Instant

data class CreateCommentCommand(
    val commenterId: Long,
    val tradeId: Long,
    val content: String,
    val commentAt: Instant
)
