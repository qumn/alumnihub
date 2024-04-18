package io.github.qumn.domain.trade.command

import io.github.qumn.domain.system.api.user.model.UID

data class ReserveTradeCmd(
    val tradeId: Long,
    val buyerId: UID,
)