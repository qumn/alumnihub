package io.github.qumn.domain.trade.model

import java.math.BigDecimal


data class Img(
    val url: String
)

data class Goods(
    val id: Long,
    val desc: String,
    val imgs: List<Img>,
    val price: BigDecimal
) {

}