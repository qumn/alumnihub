package io.github.qumn.domain.trade.infrastructure

import io.github.qumn.domain.trade.model.CompletedTrade
import io.github.qumn.domain.trade.model.PendingTrade
import io.github.qumn.domain.trade.model.ReservedTrade
import io.github.qumn.domain.trade.model.Trade


fun TradeEntity.toDomain(): Trade {
    return when (this.status) {
        TradeStatus.Pending -> toPendingTrade(this)
        TradeStatus.Reserved -> toReservedTrade(this)
        TradeStatus.Completed -> toCompletedTrade(this)
    }
}

private fun toPendingTrade(tradeEntity: TradeEntity): PendingTrade {
    require(tradeEntity.status == TradeStatus.Pending) {
        "the status of trade is not pending"
    }

    return PendingTrade(
        tradeEntity.id,
        sellerId = tradeEntity.sellerId,
        desiredBuyerIds = tradeEntity.desiredBuyerIds.toMutableList(),
        goods = tradeEntity.goods.toDomainModel()
    )
}

private fun toReservedTrade(tradeEntity: TradeEntity): ReservedTrade {
    require(tradeEntity.status == TradeStatus.Reserved) {
        "the status of trade is not reserved"
    }

    return ReservedTrade(
        tradeEntity.id,
        sellerId = tradeEntity.sellerId,
        buyerId = tradeEntity.buyerId!!,
        goods = tradeEntity.goods.toDomainModel(),
        reservedAt = tradeEntity.reservedAt!!
    )
}

private fun toCompletedTrade(tradeEntity: TradeEntity): CompletedTrade {
    require(tradeEntity.status == TradeStatus.Completed) {
        "the status of trade is not completed"
    }

    return CompletedTrade(
        tradeEntity.id,
        sellerId = tradeEntity.sellerId,
        buyerId = tradeEntity.buyerId!!,
        goods = tradeEntity.goods.toDomainModel(),
        completedAt = tradeEntity.completedAt!!
    )
}
