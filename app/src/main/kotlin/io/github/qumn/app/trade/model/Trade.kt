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

interface Trade {
    val id: Long
    val goods: Goods
    val seller: User
}

// waiting for buyer to reserve
data class PendingTrade(
    override val id: Long,
    override val goods: Goods,
    override val seller: User, // the seller id
    val desiredBuyers: MutableList<User>,
) : Trade {
    fun desire(buyer: User) {
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
    val reserveTime: Instant,
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