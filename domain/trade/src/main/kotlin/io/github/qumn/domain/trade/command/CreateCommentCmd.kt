package io.github.qumn.domain.trade.command

import io.github.qumn.domain.system.api.user.model.UID
import java.time.Instant

data class CreateCommentCmd(
    val commenterId: UID,
    val tradeId: Long,
    val content: String,
    val commentAt: Instant
)
