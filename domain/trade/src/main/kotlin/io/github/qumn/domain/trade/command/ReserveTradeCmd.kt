package io.github.qumn.domain.trade.command

data class ReserveTradeCmd(
    val tradeId: Long,
    val buyerId: Long,
)