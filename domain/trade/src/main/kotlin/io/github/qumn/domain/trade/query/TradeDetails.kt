package io.github.qumn.domain.trade.query

import io.github.qumn.domain.trade.model.Goods
import io.github.qumn.domain.trade.model.Img
import io.github.qumn.domain.trade.model.TradeStatus
import java.math.BigDecimal
import java.time.Instant

data class TradeDetails(
    val id: Long,
    val goods: GoodsDetails,
    val status: TradeStatus,
    val buyerId: Long?,
    val sellerId: Long,
    val desiredCount: Int,
    val createdAt: Instant,
    val updatedAt: Instant,
)

data class GoodsDetails(
    val desc: String,
    val imgs: List<String>,
    val price: Int,
)