package io.github.qumn.domain.trade.infrastructure

import io.github.qumn.domain.trade.model.*


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
        info = TradeInfo(
            tradeEntity.id,
            goods = tradeEntity.goods.toDomainModel(),
            sellerId = tradeEntity.sellerId
        ),
        desiredBuyerIds = tradeEntity.desiredBuyerIds.toMutableList(),
    )
}

private fun toReservedTrade(tradeEntity: TradeEntity): ReservedTrade {
    require(tradeEntity.status == TradeStatus.Reserved) {
        "the status of trade is not reserved"
    }

    return ReservedTrade(
        info = TradeInfo(
            id = tradeEntity.id,
            sellerId = tradeEntity.sellerId,
            goods = tradeEntity.goods.toDomainModel(),
        ),
        buyerId = tradeEntity.buyerId!!,
        reservedAt = tradeEntity.reservedAt!!
    )
}

private fun toCompletedTrade(tradeEntity: TradeEntity): CompletedTrade {
    require(tradeEntity.status == TradeStatus.Completed) {
        "the status of trade is not completed"
    }

    return CompletedTrade(
        info = TradeInfo(
            id = tradeEntity.id,
            sellerId = tradeEntity.sellerId,
            goods = tradeEntity.goods.toDomainModel()
        ),
        buyerId = tradeEntity.buyerId!!,
        completedAt = tradeEntity.completedAt!!
    )
}
