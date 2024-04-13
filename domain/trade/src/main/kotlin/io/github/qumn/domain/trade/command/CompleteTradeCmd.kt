package io.github.qumn.domain.trade.command

data class CompleteTradeCmd(
    val tradeId: Long,
    val operationUserId: Long,
)