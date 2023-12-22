package io.github.qumn.domain.trade.infrastructure

import io.github.qumn.domain.trade.model.*
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.update
import org.springframework.stereotype.Repository

@Repository
class TradeDatabaseRepository(
    val database: Database,
) : io.github.qumn.domain.trade.model.Trades {
    override fun findById(id: Long): Trade? {
        return findEntityById(id)?.toDomain()
    }

    override fun findPendingTrade(id: Long): PendingTrade? {
        return findById(id) as? PendingTrade
    }

    override fun findReservedTrade(id: Long): ReservedTrade? {
        return findById(id) as? ReservedTrade
    }

    override fun findCompletedTrade(id: Long): CompletedTrade? {
        return findById(id) as? CompletedTrade
    }

    private fun findEntityById(id: Long): TradeEntity? {
        return database.trades.find { Trades.id eq id }
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

    /**
     * Insert a new trade to database
     */
    private fun insertNew(trade: Trade) {
        when (trade) {
            is PendingTrade -> insertPendingTrade(trade)
            is ReservedTrade -> throw IllegalArgumentException("can not directly insert a reserved trade")
            is CompletedTrade -> throw IllegalArgumentException("can not directly insert a completed trade")
            is TradeInfo -> TradeInfo.canNotBeUsedIndependently()
        }
    }

    private fun insertPendingTrade(trade: PendingTrade) {
        val goodsEntity = GoodsEntity.fromDomainModel(trade.goods)
        val tradeEntity = TradeEntity {
            id = trade.id
            status = TradeStatus.Pending
            desiredBuyerIds = trade.desiredBuyerIds.toTypedArray()
            goods = goodsEntity
            sellerId = trade.sellerId
        }
        database.trades.add(tradeEntity)
    }


    private fun updateTrade(trade: Trade) {
        when (trade) {
            is PendingTrade -> updatePendingTrade(trade)
            is ReservedTrade -> updateReservedTrade(trade)
            is CompletedTrade -> updateCompletedTrade(trade)
            is TradeInfo -> TradeInfo.canNotBeUsedIndependently()
        }
    }

    private fun updatePendingTrade(trade: PendingTrade) {
        val goodsEntity = GoodsEntity.fromDomainModel(trade.goods)
        val tradeEntity = TradeEntity {
            id = trade.id
            status = TradeStatus.Pending
            goods = goodsEntity
            desiredBuyerIds = trade.desiredBuyerIds.toTypedArray()
        }
        database.trades.update(tradeEntity)
    }

    private fun updateReservedTrade(trade: ReservedTrade) {
        val tradeEntity = TradeEntity {
            id = trade.id
            status = TradeStatus.Reserved
            buyerId = trade.buyerId
            reservedAt = trade.reservedAt
        }
        database.trades.update(tradeEntity)
    }

    private fun updateCompletedTrade(trade: CompletedTrade) {
        val tradeEntity = TradeEntity {
            id = trade.id
            status = TradeStatus.Completed
            completedAt = trade.completedAt
        }
        database.trades.update(tradeEntity)
    }
}