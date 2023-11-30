package io.github.qumn.domain.trade.infrastructure

import io.github.qumn.domain.trade.model.CompletedTrade
import io.github.qumn.domain.trade.model.PendingTrade
import io.github.qumn.domain.trade.model.ReservedTrade
import io.github.qumn.domain.trade.model.Trade
import io.github.qumn.framework.domain.user.model.Users
import org.ktorm.database.Database
import org.springframework.stereotype.Component

@Component
class TradeDomainModelMapper(
    val database: Database,
    val users: Users,
) {

    fun toTrade(tradeEntity: TradeEntity): Trade {
        return when (tradeEntity.status) {
            TradeStatus.Pending -> toPendingTrade(tradeEntity)
            TradeStatus.Reserved -> toReservedTrade(tradeEntity)
            TradeStatus.Completed -> toCompletedTrade(tradeEntity)
        }
    }

    private fun toPendingTrade(tradeEntity: TradeEntity): PendingTrade {
        require(tradeEntity.status == TradeStatus.Pending) {
            "the status of trade is not pending"
        }

        val seller = users.findById(tradeEntity.sellerId)
        require(seller != null) { "the seller can't be found" }

        val desiredBuyers = users.findByIds(tradeEntity.desiredBuyers.toSet())
            .toMutableList()


        return PendingTrade(
            tradeEntity.id,
            seller = seller,
            desiredBuyers = desiredBuyers,
            goods = tradeEntity.goods.toDomainModel()
        )
    }

    private fun toReservedTrade(tradeEntity: TradeEntity): ReservedTrade {
        require(tradeEntity.status == TradeStatus.Reserved) {
            "the status of trade is not reserved"
        }

        val seller = users.findById(tradeEntity.sellerId)
        val buyer = users.findById(tradeEntity.buyerId!!)

        require(seller != null) { "the seller can't be found" }
        require(buyer != null) { "the buyer can't be found" }

        return ReservedTrade(
            tradeEntity.id,
            seller = seller,
            buyer = buyer,
            goods = tradeEntity.goods.toDomainModel(),
            reservedAt = tradeEntity.reservedAt!!
        )
    }

    private fun toCompletedTrade(tradeEntity: TradeEntity): CompletedTrade {
        require(tradeEntity.status == TradeStatus.Completed) {
            "the status of trade is not completed"
        }

        val seller = users.findById(tradeEntity.sellerId)
        val buyer = users.findById(tradeEntity.buyerId!!)

        require(seller != null) { "the seller can't be found" }
        require(buyer != null) { "the buyer can't be found" }

        return CompletedTrade(
            tradeEntity.id,
            seller = seller,
            buyer = buyer,
            goods = tradeEntity.goods.toDomainModel(),
            completedAt = tradeEntity.completedAt!!
        )
    }
}
