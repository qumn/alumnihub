package io.github.qumn.domain.trade.command

data class PublishIdleGoodsCmd(
    val sellerId: Long,
    val desc: String,
    val imgs: List<String>,
    val price: Int,
)
