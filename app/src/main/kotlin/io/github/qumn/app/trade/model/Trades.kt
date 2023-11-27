package io.github.qumn.app.trade.model

interface TradeRepository {
    fun findById(id: Long) : Trade?

    fun findPendingTrade(id: Long): PendingTrade?

    fun findReservedTrade(id: Long): ReservedTrade?

    fun findCompletedTrade(id: Long): CompletedTrade?

    fun save(trade: Trade)

}