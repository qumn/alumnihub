package io.github.qumn.app.trade.model

import io.github.qumn.framework.module.user.User
import java.time.Instant

data class School(
    val id: Long,
)

//data class User(
//    val id: Long,
//    val school: Long,
//);

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
//        require(buyer.school == seller.school) {
//            "the school of buyer and seller is not same"
//        }
        this.desiredBuyers.add(buyer)
    }

    fun reserve(buyer: User): ReservedTrade {
//        require(buyer.school == seller.school) {
//            "the school of buyer and seller is not same"
//        }
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
    val reservedTime: Instant,
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
    val completedTime: Instant,
) : Trade {
}