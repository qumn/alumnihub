package io.github.qumn.domain.trade.query

import io.github.qumn.domain.system.api.user.query.UserDetails
import io.github.qumn.domain.trade.model.TradeStatus
import java.time.Instant

data class TradeDetails(
    val id: Long,
    val goods: GoodsDetails,
    val status: TradeStatus,
    val buyerId: UserDetails?,
    val seller: UserDetails,
    val desiredCount: Int,
    val createdAt: Instant,
    val updatedAt: Instant,
)


data class TradeOverview(
    val id: Long,
    val desc: String,
    val cover: String,
    val price: Int,
    val sellerId: Long,
    val sellerAvatar: String,
    val sellerName: String,
)

data class GoodsDetails(
    val desc: String,
    val imgs: List<String>,
    val price: Int,
)