package io.github.qumn.app.trade.infrastructure

import io.github.qumn.app.trade.model.CompletedTrade
import io.github.qumn.app.trade.model.PendingTrade
import io.github.qumn.app.trade.model.ReservedTrade
import io.github.qumn.app.trade.model.Trade
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.springframework.stereotype.Repository

@Repository
class TradeRepository(
    val database: Database,
    val tradeFactory: TradeFactory,
) {
    fun findById(id: Long): Trade? {
        return findEntityById(id)?.let {
            tradeFactory.toTrade(it)
        }
    }

    fun findPendingTrade(id: Long): PendingTrade? {
        return findById(id) as? PendingTrade
    }

    fun findReservedTrade(id: Long): ReservedTrade? {
        return findById(id) as? ReservedTrade
    }

    fun findCompletedTrade(id: Long): CompletedTrade? {
        return findById(id) as? CompletedTrade
    }

    private fun findEntityById(id: Long): TradeEntity? {
        return database.trades.find { it.id eq id }
    }


}