package io.github.qumn.domain.trade.command

import io.github.qumn.domain.trade.model.Img
import java.math.BigDecimal

data class PublishIdleGoodsCommand(
    val sellerId: Long,
    val desc: String,
    val imgs: List<Img>,
    val price: BigDecimal,
)
