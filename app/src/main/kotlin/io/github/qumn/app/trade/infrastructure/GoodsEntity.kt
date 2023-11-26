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
    var tradeId: Long
    var desc: String
    var price: Int // in cent
    var imgs: List<String>
    fun toDomainModel(): Goods {
        return Goods(
            desc = this.desc,
            price = BigDecimal(this.price),
            imgs = this.imgs.map(::Img)
        )
    }
}

object GoodsTable : Table<GoodsEntity>("biz_goods") {
    val tradeId = long("trade_id").primaryKey().bindTo { it.tradeId }
    val desc = varchar("desc").bindTo { it.desc }
    val price = int("desc").bindTo { it.price }
    // TODO: make list image
}

val Database.goods get() = this.sequenceOf(GoodsTable)