package io.github.qumn.app.trade.infrastructure

import io.github.qumn.app.trade.model.*
import io.github.qumn.ktorm.ext.LongArray
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.removeIf
import org.ktorm.entity.update
import org.springframework.stereotype.Repository

@Repository
class TradeDatabaseRepository(
    val database: Database,
    val tradeFactory: TradeFactory,
) : TradeRepository {
    override fun findById(id: Long): Trade? {
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

    /**
     * Save the trade to database
     * if the trade not exists, create a new one. otherwise update the existing one.
     */
    override fun save(trade: Trade) {
        findEntityById(trade.id)?.let {
            updateTrade(trade)
        } ?: run {
            insertNew(trade)
        }
    }

    private fun insertNew(trade: Trade) {
    }

    private fun updateTrade(trade: Trade) {
        when (trade) {
            is PendingTrade -> savePendingTrade(trade)
            is ReservedTrade -> saveReservedTrade(trade)
            is CompletedTrade -> saveCompletedTrade(trade)
        }
    }

    private fun savePendingTrade(trade: PendingTrade) {
        val tradeEntity = TradeEntity {
            id = trade.id
            desiredBuyers = trade.desiredBuyers.map { it.uid }.toTypedArray()
        }
        database.trades.update(tradeEntity)
        database.goods.removeIf { it.tradeId eq trade.id }
        database.goods.add(GoodsEntity.fromDomainModel(trade.goods))
    }

    private fun saveReservedTrade(trade: ReservedTrade) {

    }

    private fun saveCompletedTrade(trade: CompletedTrade) {

    }
}