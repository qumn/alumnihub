package io.github.qumn.domain.comment.infrastructure

import io.github.qumn.domain.comment.api.model.CommentId
import io.github.qumn.domain.comment.api.model.SubjectType
import io.github.qumn.domain.system.api.user.model.UID
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.Entity
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.schema.*
import java.time.Instant

interface CommentEntity : Entity<CommentEntity> {
    companion object : Entity.Factory<CommentEntity>()

    var id: CommentId
    var parentId: CommentId?
    var commentedBy: UID
    var subjectType: SubjectType
    var subjectId: Long
    var content: String
    var createdAt: Instant

    fun loadReplays(db: Database): List<CommentEntity> {
        var replays = this["replays"]
        if (replays == null) {
            replays = db.comment.filter { it.parentId eq id }.toList()
            this["replays"] = replays
        }
        @Suppress("UNCHECKED_CAST")
        return replays as List<CommentEntity>
    }
}


object CommentTable : Table<CommentEntity>("biz_comment") {
    val id = long("id").transform({ CommentId(it) }, { it.value }).primaryKey().bindTo { it.id }
    val parentId = long("parent_id").transform({ CommentId(it) }, { it.value }).bindTo { it.parentId }
    val commentedBy = long("commented_by").transform({ UID(it) }, { it.value }).bindTo { it.commentedBy }
    val subjectType = enum<SubjectType>("subject_type").bindTo { it.subjectType }
    val subjectId = long("subject_id").bindTo { it.subjectId }
    val content = varchar("content").bindTo { it.content }
    val createAt = timestamp("created_at").bindTo { it.createdAt }
}


interface CommentLikeEntity : Entity<CommentLikeEntity> {
    companion object : Entity.Factory<CommentLikeEntity>()

    var cid: CommentId
    var uid: UID
    var createdAt: Instant
}

object CommentLikeTable : Table<CommentLikeEntity>("biz_comment_like") {
    val cid = long("cid").transform({ CommentId(it) }, { it.value }).primaryKey().bindTo { it.cid }
    val uid = long("uid").transform({ UID(it) }, { it.value }).primaryKey().bindTo { it.uid }
    val createdAt = timestamp("created_at").bindTo { it.createdAt }
}

val Database.comment
    get() = this.sequenceOf(CommentTable)

val Database.commentLike
    get() = this.sequenceOf(CommentLikeTable)