package io.github.qumn.domain.trade.model

import io.github.qumn.framework.storage.model.URN
import java.math.BigDecimal


data class Img(
    val url: String
)

data class Goods(
    val desc: String,
    val imgs: List<URN>,
    val price: BigDecimal
)