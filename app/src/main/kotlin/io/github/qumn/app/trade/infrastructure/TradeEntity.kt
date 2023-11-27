package io.github.qumn.app.trade.infrastructure

import io.github.qumn.ktorm.base.BaseEntity
import io.github.qumn.ktorm.base.BaseTable
import io.github.qumn.ktorm.ext.LongArray
import io.github.qumn.ktorm.ext.longArray
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.enum
import org.ktorm.schema.long
import org.ktorm.schema.timestamp
import java.time.Instant

enum class TradeStatus {
    Pending,
    Reserved,
    Completed
}

interface TradeEntity : BaseEntity<TradeEntity> {
    companion object : Entity.Factory<TradeEntity>()

    var id: Long
    var status: TradeStatus
    var desiredBuyers: LongArray

    var goods: GoodsEntity
    var sellerId: Long
    var buyerId: Long?

    var reservedTime: Instant?
    var completedTime: Instant?
}

object Trades : BaseTable<TradeEntity>("biz_trade") {
    val id = long("id").primaryKey().bindTo { it.id }
    val status = enum<TradeStatus>("status").bindTo { it.status }
    val sellerId = long("seller_id").bindTo { it.sellerId }
    val desiredBuyers = longArray("desired_buyer_ids").bindTo { it.desiredBuyers }
    val buyerId = long("buyer_id").bindTo { it.buyerId }
    val reservedTime = timestamp("reserved_time").bindTo { it.reservedTime }
    val completedTime = timestamp("completed_time").bindTo { it.completedTime }
}

val Database.trades get() = this.sequenceOf(Trades)