package io.github.qumn.domain.trade.query

import io.github.qumn.domain.system.api.user.model.Users
import io.github.qumn.domain.system.api.user.query.UserQuery
import io.github.qumn.domain.trade.infrastructure.trades
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.springframework.stereotype.Component

@Component
class TradeQueryHandler(
    val db: Database,
    val userQuery: UserQuery,
) {

    fun queryTradeDetails(id: Long): TradeDetails? {
        val entity = db.trades.find { it.id eq id } ?: return null
        return TradeDetails(
            entity.id,
            GoodsDetails(
                entity.goods.intro,
                entity.goods.imgs?.map { it.toURL().location }?.toList() ?: listOf(),
                entity.goods.price.toInt()
            ),
            entity.status,
            entity.buyerId?.let { userQuery.queryById(it) },
            userQuery.queryById(entity.sellerId),
            entity.desiredBuyerIds.size,
            entity.createdAt,
            entity.updatedAt
        )
    }
}