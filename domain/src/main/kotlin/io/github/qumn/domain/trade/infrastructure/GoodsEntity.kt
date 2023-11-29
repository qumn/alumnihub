package io.github.qumn.domain.trade.infrastructure

import io.github.qumn.domain.trade.model.Goods
import io.github.qumn.domain.trade.model.Img

// only a value object
data class GoodsEntity(
    var intro: String,
    var price: Int, // in cent
    val imgs: Array<String>?,
) {
    companion object {
        fun fromDomainModel(goods: Goods): GoodsEntity {
            return GoodsEntity(
                intro = goods.desc,
                price = goods.price.toInt(),
                imgs = goods.imgs.map { it.url }.toTypedArray()
            )
        }
    }

    fun toDomainModel() : Goods {
        return Goods(
            desc = intro,
            price = price.toBigDecimal(),
            imgs = imgs?.map { Img(it) } ?: emptyList()
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GoodsEntity

        if (intro != other.intro) return false
        if (price != other.price) return false
        if (imgs != null) {
            if (other.imgs == null) return false
            if (!imgs.contentEquals(other.imgs)) return false
        } else if (other.imgs != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = intro.hashCode()
        result = 31 * result + price
        result = 31 * result + (imgs?.contentHashCode() ?: 0)
        return result
    }
}