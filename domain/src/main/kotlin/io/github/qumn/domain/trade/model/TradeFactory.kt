package io.github.qumn.domain.trade.model

import io.github.qumn.framework.domain.user.model.User
import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId
import java.math.BigDecimal
import kotlin.random.Random

class TradeFactory {
    companion object {
        fun create(seller: User, goodsDesc: String, goodsPrice: Int, goodsImgs: List<String>): PendingTrade {
            val goods = createGoods(goodsDesc, goodsPrice, goodsImgs)
            return PendingTrade(
                id = IdUtil.nextId(),
                seller = seller,
                goods = goods,
                desiredBuyers = mutableListOf()
            )
        }

        private fun createGoods(goodsDesc: String, goodsPrice: Int, goodsImgs: List<String>): Goods {
            return Goods(
                desc = goodsDesc,
                imgs = goodsImgs.map { Img(it) },
                price = BigDecimal(goodsPrice)
            )
        }
    }
}