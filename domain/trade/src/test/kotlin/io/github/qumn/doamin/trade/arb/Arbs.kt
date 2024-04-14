package io.github.qumn.doamin.trade.arb

import io.github.qumn.domain.trade.model.Goods
import io.github.qumn.domain.trade.model.PendingTrade
import io.github.qumn.domain.trade.model.TradeInfo
import io.github.qumn.framework.storage.arb.urn
import io.github.qumn.test.arb.id
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*


fun Arb.Companion.pendingTrade() = Arb.bind(
    Arb.id(), Arb.id(), Arb.array(Arb.long()).map { it.toMutableList() }, Arb.goods()
) { tradeId, sellerId, tradeDesiredBuyerIds, tradeGoods ->
    PendingTrade(
        info = TradeInfo(
            id = tradeId,
            sellerId = sellerId,
            goods = tradeGoods
        ),
        desiredBuyerIds = tradeDesiredBuyerIds
    )
}

fun Arb.Companion.goods() = Arb.bind(
    Arb.string(10, 100),
    Arb.list(Arb.urn(), 1..10),
    Arb.int(min = 1).map { it.toBigDecimal() }) { desc, imgs, price ->
    Goods(desc = desc, imgs = imgs, price = price)
}
