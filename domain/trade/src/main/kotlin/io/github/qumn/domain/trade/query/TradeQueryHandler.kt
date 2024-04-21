package io.github.qumn.domain.trade.query

import io.github.qumn.domain.system.api.user.query.UserQuery
import io.github.qumn.domain.trade.infrastructure.TradeEntity
import io.github.qumn.domain.trade.infrastructure.trades
import io.github.qumn.ktorm.page.Page
import io.github.qumn.ktorm.page.PageParam
import io.github.qumn.ktorm.page.page
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.springframework.stereotype.Component

class TradeSearchParam(
    val goodsDesc: String? = null,
    pageNo: Int = 1,
    pageSize: Int = 10,
    isCount: Boolean = false,
) : PageParam(pageNo, pageSize, isCount)

@Component
class TradeQueryHandler(
    val db: Database,
    val userQuery: UserQuery,
) {

    fun queryTradeDetails(id: Long): TradeDetails? {
        val entity = db.trades.find { it.id eq id } ?: return null
        return entity.toDetails()
    }


    fun queryBy(param: TradeSearchParam): Page<TradeOverview> {
        return db.trades.page(param).transform { it.toOverview() }
    }

    private fun TradeEntity.toDetails() = TradeDetails(
        this.id,
        GoodsDetails(
            this.goods.intro,
            this.goods.imgs?.map { it.toURL().location }?.toList() ?: listOf(),
            this.goods.price.toInt()
        ),
        this.status,
        this.buyerId?.let { userQuery.queryBy(it) },
        userQuery.queryBy(this.sellerId),
        this.desiredBuyerIds.size,
        this.createdAt,
        this.updatedAt
    )

    private fun TradeEntity.toOverview(): TradeOverview {
        val seller = userQuery.queryBy(this.sellerId)
        val cover = this.goods.imgs?.let {
            if (it.isEmpty()) return@let null
            else it[0].toURL().location
        } ?: ""
        return TradeOverview(
            id = this.id,
            desc = this.goods.intro,
            cover = cover,
            price = this.goods.price,
            sellerId = seller.uid,
            sellerAvatar = "",
            sellerName = seller.name
        )
    }

}