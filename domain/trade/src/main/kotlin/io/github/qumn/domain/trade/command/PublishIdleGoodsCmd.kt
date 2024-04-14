package io.github.qumn.domain.trade.command

import io.github.qumn.framework.storage.model.URN

data class PublishIdleGoodsCmd(
    val sellerId: Long,
    val desc: String,
    val imgs: List<URN>,
    val price: Int,
)
