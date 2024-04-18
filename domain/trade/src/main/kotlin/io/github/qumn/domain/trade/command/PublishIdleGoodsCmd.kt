package io.github.qumn.domain.trade.command

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URN

data class PublishIdleGoodsCmd(
    val sellerId: UID,
    val desc: String,
    val imgs: List<URN>,
    val price: Int,
)
