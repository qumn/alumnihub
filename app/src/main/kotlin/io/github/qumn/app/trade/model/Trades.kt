package io.github.qumn.app.trade.model

interface TradeRepository {
    fun findById(id: Long) : Trade?

    fun save(trade: Trade)

}