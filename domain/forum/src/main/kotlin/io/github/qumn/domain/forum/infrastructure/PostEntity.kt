package io.github.qumn.domain.forum.infrastructure

import io.github.qumn.domain.forum.model.PostId
import io.github.qumn.domain.forum.model.Tag
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URN
import io.github.qumn.ktorm.base.BaseEntity
import io.github.qumn.ktorm.base.BaseTable
import io.github.qumn.ktorm.ext.longArray
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.long
import org.ktorm.schema.text
import org.ktorm.schema.varchar
import org.ktorm.support.postgresql.textArray

interface PostEntity : BaseEntity<PostEntity> {
    companion object : Entity.Factory<PostEntity>()

    var id: PostId

    var title: String
    var content: String
    var tags: List<Tag>
    var imgs: List<URN>

    var thumbUpBy: List<UID>
    var sharedBy: List<UID>
}

object PostTable : BaseTable<PostEntity>("biz_post") {
    val id = long("id").transform({ PostId(it) }, { it.value }).primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val content = text("content").bindTo { it.content }
    val tags =
        textArray("tags").transform({ it.map { Tag(it!!) } }, { it.map { it.value }.toTypedArray() }).bindTo { it.tags }
    val imgs =
        textArray("imgs").transform({ it.map { URN(it!!) } }, { it.map { it.name }.toTypedArray() }).bindTo { it.imgs }
    val thumbupBy = longArray("thumbup_by").transform({ it.map { UID(it) } }, { it.map { it.value }.toTypedArray() })
        .bindTo { it.thumbUpBy }
    val sharedBy = longArray("shared_by").transform({ it.map { UID(it) } }, { it.map { it.value }.toTypedArray() })
        .bindTo { it.sharedBy }
}

val Database.posts
    get() = this.sequenceOf(PostTable)