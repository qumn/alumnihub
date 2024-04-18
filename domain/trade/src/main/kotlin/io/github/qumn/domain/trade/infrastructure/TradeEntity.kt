package io.github.qumn.domain.trade.infrastructure

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.domain.trade.model.TradeStatus
import io.github.qumn.ktorm.ext.longArray
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.jackson.json
import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.long
import org.ktorm.schema.timestamp
import java.time.Instant

interface TradeEntity : Entity<TradeEntity> {
    companion object : Entity.Factory<TradeEntity>()

    var id: Long
    var status: TradeStatus
    var desiredBuyerIds: Array<UID>

    var goods: GoodsEntity
    var sellerId: UID
    var buyerId: UID?

    var reservedAt: Instant?
    var completedAt: Instant?
    val createdAt: Instant
    val updatedAt: Instant
}

object Trades : Table<TradeEntity>("biz_trade") {
    val id = long("id").primaryKey().bindTo { it.id }
    val status = enum<TradeStatus>("status").bindTo { it.status }
    val goods = json<GoodsEntity>("goods").bindTo { it.goods }
    val sellerId = long("seller_id").transform({ UID(it) }, { it.value }).bindTo { it.sellerId }
    val buyerId = long("buyer_id").transform({ UID(it) }, { it.value }).bindTo { it.buyerId }
    val desiredBuyerIds =
        longArray("desired_buyer_ids").transform(
            { it.map { id -> UID(id) }.toTypedArray() },
            { it.map { it.value }.toTypedArray() })
            .bindTo { it.desiredBuyerIds }
    val reservedAt = timestamp("reserved_at").bindTo { it.reservedAt }
    val completedAt = timestamp("completed_at").bindTo { it.completedAt }
    val createdAt = timestamp("created_at").bindTo { it.createdAt }
    val updatedAt = timestamp("updated_at").bindTo { it.updatedAt }
}

val Database.trades get() = this.sequenceOf(Trades)