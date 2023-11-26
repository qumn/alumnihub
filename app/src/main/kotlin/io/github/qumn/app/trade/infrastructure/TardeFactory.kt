package io.github.qumn.app.trade.infrastructure

import io.github.qumn.app.trade.model.*
import io.github.qumn.framework.module.user.users
import io.github.qumn.ktorm.base.database
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.springframework.stereotype.Component

@Component
class TradeFactory(
    database: Database,
) {
    fun toTrade(tradeEntity: TradeEntity): Trade {
        return when (tradeEntity.status) {
            TradeStatus.Pending -> toPendingTrade(tradeEntity)
            TradeStatus.Reserved -> toReservedTrade(tradeEntity)
            TradeStatus.Completed -> toCompletedTrade(tradeEntity)
        }
    }

    fun toPendingTrade(tradeEntity: TradeEntity): PendingTrade {
        require(tradeEntity.status == TradeStatus.Pending) {
            "the status of trade is not pending"
        }

        val seller = getUser(tradeEntity.sellerId)
        require(seller != null) { "the seller can't be found" }

        val goods = getGoods(tradeEntity.id)

        return PendingTrade(
            tradeEntity.id,
            seller = seller,
            desiredBuyers = mutableListOf(), // TODO: read from database
            goods = goods
        )
    }

    fun toReservedTrade(tradeEntity: TradeEntity): ReservedTrade {
        require(tradeEntity.status == TradeStatus.Reserved) {
            "the status of trade is not reserved"
        }

        val seller = getUser(tradeEntity.sellerId)
        val buyer = getUser(tradeEntity.buyerId!!)

        require(seller != null) { "the seller can't be found" }
        require(buyer != null) { "the buyer can't be found" }

        val goods = getGoods(tradeEntity.id)

        return ReservedTrade(
            tradeEntity.id,
            seller = seller,
            buyer = buyer,
            goods = goods,
            reserveTime = tradeEntity.reservedTime!!
        )
    }

    fun toCompletedTrade(tradeEntity: TradeEntity): CompletedTrade {
        require(tradeEntity.status == TradeStatus.Completed) {
            "the status of trade is not completed"
        }

        val seller = getUser(tradeEntity.sellerId)
        val buyer = getUser(tradeEntity.buyerId!!)

        require(seller != null) { "the seller can't be found" }
        require(buyer != null) { "the buyer can't be found" }

        val goods = getGoods(tradeEntity.id)

        return CompletedTrade(
            tradeEntity.id,
            seller = seller,
            buyer = buyer,
            goods = goods,
            completedTime = tradeEntity.completedTime!!
        )
    }

    private fun getUser(uid: Long) =
        database.users.find { it.uid eq uid }

    private fun getGoods(tradeId: Long): Goods {
        return database.goods.find { it.tradeId eq tradeId }
            .let {
                require(it != null) { "goods not exist" }
                it.toDomainModel()
            }
    }
}
