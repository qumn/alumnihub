package io.github.qumn.domain.trade.model

import io.github.qumn.domain.system.api.user.model.User
import io.github.qumn.util.time.nowMicros
import java.time.Instant


sealed interface Trade {
    val id: Long
    val goods: Goods
    val sellerId: Long
}

/**
 * waiting for buyer to reserve, only one buyer can reserve
 * and only the goods can be changed when the trade is pending
 */
data class PendingTrade(
    override val id: Long,
    override val sellerId: Long, // the seller id
    override val goods: Goods,
    val desiredBuyerIds: MutableList<Long>,
) : Trade {
    fun desiredBy(buyer: User) {
        this.desiredBuyerIds.add(buyer.uid)
    }

    fun reserve(buyer: User): ReservedTrade {
        return ReservedTrade(
            this.id,
            this.goods,
            this.sellerId,
            buyer.uid,
            nowMicros()
        )
    }

}

data class ReservedTrade(
    override val id: Long,
    override val goods: Goods,
    override val sellerId: Long,
    val buyerId: Long,
    val reservedAt: Instant,
) : Trade {
    fun complete(): CompletedTrade {
        return CompletedTrade(
            this.id,
            this.goods,
            this.sellerId,
            this.buyerId,
            nowMicros()
        )
    }

}

data class CompletedTrade(
    override val id: Long,
    override val goods: Goods,
    override val sellerId: Long,
    val buyerId: Long,
    val completedAt: Instant,
) : Trade {
}