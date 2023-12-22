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
 * be used to delegate the trade
 * should never have used independently
 */
data class TradeInfo(
    override val id: Long,
    override val goods: Goods,
    override val sellerId: Long,
) : Trade {
    companion object {
        fun canNotBeUsedIndependently(): Nothing {
            throw IllegalArgumentException("TradeInfo should never used independently")
        }
    }
}

/**
 * waiting for buyer to reserve, only one buyer can reserve
 * and only the goods can be changed when the trade is pending
 */
data class PendingTrade(
    private val info: TradeInfo,
    val desiredBuyerIds: MutableList<Long>,
) : Trade by info {
    fun desiredBy(buyer: User) {
        this.desiredBuyerIds.add(buyer.uid)
    }

    fun reserve(buyer: User): ReservedTrade {
        return ReservedTrade(
            info,
            buyer.uid,
            nowMicros()
        )
    }

}

data class ReservedTrade(
    val info: TradeInfo,
    val buyerId: Long,
    val reservedAt: Instant,
) : Trade by info {
    fun complete(): CompletedTrade {
        return CompletedTrade(
            info,
            buyerId,
            nowMicros()
        )
    }

}

data class CompletedTrade(
    private val info: TradeInfo,
    val buyerId: Long,
    val completedAt: Instant,
) : Trade by info
