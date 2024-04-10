package io.github.qumn.domain.comment.infrastructure

import io.github.qumn.domain.comment.api.model.SubjectType
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

    var id: Long
    var parentId: Long?
    var commentedBy: Long
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
    val id = long("id").primaryKey().bindTo { it.id }
    val parentId = long("parent_id").bindTo { it.parentId }
    val commentedBy = long("commented_by").bindTo { it.commentedBy }
    val subjectType = enum<SubjectType>("subject_type").bindTo { it.subjectType }
    val subjectId = long("subject_id").bindTo { it.subjectId }
    val content = varchar("content").bindTo { it.content }
    val createAt = timestamp("created_at").bindTo { it.createdAt }
}


interface CommentLikeEntity : Entity<CommentLikeEntity> {
    companion object : Entity.Factory<CommentLikeEntity>()

    var cid: Long
    var uid: Long
    var createdAt: Instant
}

object CommentLikeTable : Table<CommentLikeEntity>("biz_comment_like") {
    val cid = long("cid").primaryKey().bindTo { it.cid }
    val uid = long("uid").primaryKey().bindTo { it.uid }
    val createdAt = timestamp("created_at").bindTo { it.createdAt }
}

val Database.comment
    get() = this.sequenceOf(CommentTable)

val Database.commentLike
    get() = this.sequenceOf(CommentLikeTable)