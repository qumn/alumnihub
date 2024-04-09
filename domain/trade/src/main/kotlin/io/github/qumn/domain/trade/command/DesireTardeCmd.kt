package io.github.qumn.domain.trade.command

data class DesireTradeCmd(
    val tradeId: Long,
    val desiredBy: Long,
)
