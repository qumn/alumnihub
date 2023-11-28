package io.github.qumn.domain.trade.infrastructure

import io.github.qumn.domain.trade.model.Goods
import io.github.qumn.domain.trade.model.Img
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar
import org.ktorm.support.postgresql.textArray
import java.math.BigDecimal

// only a value object
interface GoodsEntity : Entity<GoodsEntity> {
    companion object : Entity.Factory<GoodsEntity>() {
        fun fromDomainModel(goods: Goods): GoodsEntity {
            return GoodsEntity {
                id = goods.id
                intro = goods.desc
                price = goods.price.toInt()
                imgs = goods.imgs.map { it.url }.toTypedArray()
            }
        }
    }

    var id: Long
    var intro: String
    var price: Int // in cent
    var imgs: Array<String?>?
    fun toDomainModel(): Goods {
        return Goods(
            id = this.id,
            desc = this.intro,
            price = BigDecimal(this.price),
            imgs = this.imgs?.filterNotNull()?.map(::Img) ?: listOf()
        )
    }
}

object GoodsTable : Table<GoodsEntity>("biz_goods") {
    val id = long("id").primaryKey().bindTo { it.id }
    val intro = varchar("intro").bindTo { it.intro }
    val price = int("price").bindTo { it.price }
    val imgs = textArray("imgs").bindTo { it.imgs }
    // TODO: make list image
}

val Database.goods get() = this.sequenceOf(io.github.qumn.domain.trade.infrastructure.GoodsTable)