package io.github.qumn.app.trade.infrastructure

import io.github.qumn.app.trade.model.Goods
import io.github.qumn.app.trade.model.Img
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar
import java.math.BigDecimal

// only a value object
interface GoodsEntity : Entity<GoodsEntity> {
    companion object : Entity.Factory<GoodsEntity>() {
        fun fromDomainModel(goods: Goods): GoodsEntity {
            return GoodsEntity {
                var id = goods.id
                var desc: String = goods.desc
                var price: Int = goods.price.toInt()
                var imgs: List<String> = goods.imgs.map { it.url }
            }
        }
    }

    var id: Long
    var desc: String
    var price: Int // in cent
    var imgs: List<String>
    fun toDomainModel(): Goods {
        return Goods(
            id = this.id,
            desc = this.desc,
            price = BigDecimal(this.price),
            imgs = this.imgs.map(::Img)
        )
    }
}

object GoodsTable : Table<GoodsEntity>("biz_goods") {
    val id = long("trade_id").primaryKey().bindTo { it.id }
    val desc = varchar("desc").bindTo { it.desc }
    val price = int("desc").bindTo { it.price }
    // TODO: make list image
}

val Database.goods get() = this.sequenceOf(GoodsTable)