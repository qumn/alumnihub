package io.github.qumn.domain.trade.infrastructure

import io.github.qumn.domain.trade.model.*
import io.github.qumn.framework.domain.user.model.Users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
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

        val desiredBuyers = users.findByIds(tradeEntity.desiredBuyers.toList())
            .toMutableList()

        val goods = getGoods(tradeEntity.goods.id)

        return PendingTrade(
            tradeEntity.id,
            seller = seller,
            desiredBuyers = desiredBuyers,
            goods = goods
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

        val goods = getGoods(tradeEntity.goods.id)

        return ReservedTrade(
            tradeEntity.id,
            seller = seller,
            buyer = buyer,
            goods = goods,
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

        val goods = getGoods(tradeEntity.goods.id)

        return CompletedTrade(
            tradeEntity.id,
            seller = seller,
            buyer = buyer,
            goods = goods,
            completedAt = tradeEntity.completedAt!!
        )
    }

    private fun getGoods(goodsId: Long): Goods {
        return database.goods.find { GoodsTable.id eq goodsId }
            .let {
                require(it != null) { "goods not exist" }
                it.toDomainModel()
            }
    }
}
