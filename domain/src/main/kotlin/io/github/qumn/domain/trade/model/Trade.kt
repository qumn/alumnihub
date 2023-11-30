package io.github.qumn.domain.trade.model

import io.github.qumn.framework.domain.user.model.User
import java.time.Instant

sealed interface Trade {
    val id: Long
    val goods: Goods
    val seller: User
}

/**
 * waiting for buyer to reserve, only one buyer can reserve
 * and only the goods can be changed when the trade is pending
 */
data class PendingTrade(
    override val id: Long,
    override val goods: Goods,
    override val seller: User, // the seller id
    val desiredBuyers: MutableList<User>,
) : Trade {
    fun desiredBy(buyer: User) {
        this.desiredBuyers.add(buyer)
    }

    fun reserve(buyer: User): ReservedTrade {
        return ReservedTrade(
            this.id,
            this.goods,
            this.seller,
            buyer,
            Instant.now()
        )
    }

}

data class ReservedTrade(
    override val id: Long,
    override val goods: Goods,
    override val seller: User,
    val buyer: User,
    val reservedAt: Instant,
) : Trade {
    fun complete(): CompletedTrade {
        return CompletedTrade(
            this.id,
            this.goods,
            this.seller,
            this.buyer,
            Instant.now()
        )
    }

}

data class CompletedTrade(
    override val id: Long,
    override val goods: Goods,
    override val seller: User,
    val buyer: User,
    val completedAt: Instant,
) : Trade {
}