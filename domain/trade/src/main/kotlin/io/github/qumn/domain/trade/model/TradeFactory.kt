package io.github.qumn.domain.trade.model

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URN
import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId
import java.math.BigDecimal

class TradeFactory {
    companion object {

        fun create(sellerId: UID, goodsDesc: String, goodsPrice: Int, goodsImgs: List<URN>): PendingTrade {
            val goods = createGoods(goodsDesc, goodsPrice, goodsImgs)
            return create(
                sellerId,
                goods,
            )
        }

        fun create(sellerId: UID, goods: Goods): PendingTrade {
            return PendingTrade(
                info = TradeInfo(
                    id = IdUtil.nextId(),
                    sellerId = sellerId,
                    goods = goods,
                ),
                desiredBuyerIds = mutableListOf()
            )
        }

        private fun createGoods(goodsDesc: String, goodsPrice: Int, goodsImgs: List<URN>): Goods {
            return Goods(
                desc = goodsDesc,
                imgs = goodsImgs,
                price = BigDecimal(goodsPrice)
            )
        }
    }
}