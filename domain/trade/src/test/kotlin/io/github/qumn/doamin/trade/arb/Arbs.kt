package io.github.qumn.doamin.trade.arb

import io.github.qumn.domain.trade.model.Goods
import io.github.qumn.domain.trade.model.Img
import io.github.qumn.domain.trade.model.PendingTrade
import io.github.qumn.test.arb.id
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*


fun Arb.Companion.pendingTrade() = Arb.bind(
    Arb.id(), Arb.id(), Arb.array(Arb.long()).map { it.toMutableList() }, Arb.goods()
) { tradeId, sellerId, tradeDesiredBuyerIds, tradeGoods ->
    PendingTrade(id = tradeId, sellerId = sellerId, desiredBuyerIds = tradeDesiredBuyerIds, goods = tradeGoods)
}

fun Arb.Companion.goods() = Arb.bind(
    Arb.string(10, 100),
    Arb.list(Arb.string(10, 100), 1..10).map { list -> list.map(::Img) },
    Arb.int(min = 1).map { it.toBigDecimal() }) { desc, imgs, price ->
    Goods(desc = desc, imgs = imgs, price = price)
}
