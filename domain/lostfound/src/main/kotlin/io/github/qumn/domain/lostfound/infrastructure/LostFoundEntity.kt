package io.github.qumn.domain.lostfound.infrastructure

import io.github.qumn.domain.lostfound.model.LostFoundID
import io.github.qumn.domain.lostfound.model.LostFoundStatus
import io.github.qumn.domain.lostfound.model.Question
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URN
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.jackson.json
import org.ktorm.schema.*
import org.ktorm.support.postgresql.textArray
import java.time.Instant

interface LostFoundEntity : Entity<LostFoundEntity> {
    companion object : Entity.Factory<LostFoundEntity>()

    var id: LostFoundID
    var publisherId: UID
    var ownerId: UID?
    var status: LostFoundStatus
    var location: String
    var missingDesc: String
    var missingImgs: List<URN>
    var questions: List<Question>
    var pickupTime: Instant
}

object LostFoundTable : Table<LostFoundEntity>("biz_lostfound") {
    val id = long("id").transform({ LostFoundID(it) }, { it.value }).primaryKey().bindTo { it.id }
    val publisherId = long("publisher_id").transform({ UID(it) }, { it.value }).primaryKey().bindTo { it.publisherId }
    val ownerId = long("owner_id").transform({ UID(it) }, { it.value }).primaryKey().bindTo { it.ownerId }
    val status = enum<LostFoundStatus>("status").bindTo { it.status }
    val location = varchar("location").bindTo { it.location }
    val missingDesc = varchar("missing_desc").bindTo { it.missingDesc }
    val missingImgs = textArray("missing_imgs").transform({ it.filterNotNull().map { URN(it) } },
        { it.map { it.name }.toTypedArray() }).bindTo { it.missingImgs }
    val questions = json<List<Question>>("questions").bindTo { it.questions }
    val pickupTime = timestamp("pickup_time").bindTo { it.pickupTime }
}

val Database.lostFounds get() = this.sequenceOf(LostFoundTable)