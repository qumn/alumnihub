package io.github.qumn.app.trade.model

import io.github.qumn.framework.module.user.User
import java.math.BigDecimal
import kotlin.random.Random

class TradeFactory {
    fun create(seller: User, goodsDesc: String, goodsPrice: Int, goodsImgs: List<String>): PendingTrade {
        val goods = createGoods(goodsDesc, goodsPrice, goodsImgs)
        return PendingTrade(
            id = Random(1).nextLong(), // TODO: 使用雪花算法
            seller = seller,
            goods = goods,
            desiredBuyers = mutableListOf()
        )
    }

    private fun createGoods(goodsDesc: String, goodsPrice: Int, goodsImgs: List<String>): Goods {
        return Goods(
            id = Random(1).nextLong(), // TODO: 使用雪花算法
            desc = goodsDesc,
            imgs = goodsImgs.map { Img(it) },
            price = BigDecimal(goodsPrice)
        )
    }
}