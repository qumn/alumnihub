package io.github.qumn.domain.trade.command

import java.time.Instant

data class CreateCommentCmd(
    val commenterId: Long,
    val tradeId: Long,
    val content: String,
    val commentAt: Instant
)
