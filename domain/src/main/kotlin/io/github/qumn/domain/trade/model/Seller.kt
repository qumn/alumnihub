package io.github.qumn.domain.trade.model

import io.github.qumn.framework.domain.user.model.User

// extend the user
fun User.consign(goods: Goods): PendingTrade {
    return TradeFactory.create(this.uid, goods)
}