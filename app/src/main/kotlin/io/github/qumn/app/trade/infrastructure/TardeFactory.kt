package io.github.qumn.app.trade.infrastructure

import io.github.qumn.app.trade.model.*
import io.github.qumn.framework.module.user.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.toMutableList
import org.springframework.stereotype.Component

@Component
class DomainModelMapper(
    val database: Database,
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

        val seller = getUser(tradeEntity.sellerId)
        require(seller != null) { "the seller can't be found" }

        val desiredBuyers = database.users
            .filter { it.uid inList tradeEntity.desiredBuyers.toList() }
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

        val seller = getUser(tradeEntity.sellerId)
        val buyer = getUser(tradeEntity.buyerId!!)

        require(seller != null) { "the seller can't be found" }
        require(buyer != null) { "the buyer can't be found" }

        val goods = getGoods(tradeEntity.goods.id)

        return ReservedTrade(
            tradeEntity.id,
            seller = seller,
            buyer = buyer,
            goods = goods,
            reservedTime = tradeEntity.reservedTime!!
        )
    }

    private fun toCompletedTrade(tradeEntity: TradeEntity): CompletedTrade {
        require(tradeEntity.status == TradeStatus.Completed) {
            "the status of trade is not completed"
        }

        val seller = getUser(tradeEntity.sellerId)
        val buyer = getUser(tradeEntity.buyerId!!)

        require(seller != null) { "the seller can't be found" }
        require(buyer != null) { "the buyer can't be found" }

        val goods = getGoods(tradeEntity.goods.id)

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

    private fun getGoods(goodsId: Long): Goods {
        return database.goods.find { it.id eq goodsId }
            .let {
                require(it != null) { "goods not exist" }
                it.toDomainModel()
            }
    }
}
