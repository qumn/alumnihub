package io.github.qumn.domain.comment.infrastructure

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.comment.api.query.CommentDetails
import io.github.qumn.domain.system.api.user.query.UserQuery
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.count
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.springframework.stereotype.Component

@Component
class CommentDomainModelMapper(val db: Database, val userQuery: UserQuery) {
    fun toDomain(entity: CommentEntity, isLoadChildren: Boolean = true): Comment {
        val replayTo = entity.parentId?.let { parentId ->
            db.comment.find { it.id eq parentId }
        }?.let {
            toDomain(it, false)
        }
        val replays = if (isLoadChildren) {
            entity.loadReplays(db).map { toDomain(it, false) }
        } else {
            listOf()
        }

        return Comment(entity.id,
            replayTo,
            entity.commentedBy,
            entity.subjectId,
            entity.subjectType,
            entity.content,
            db.commentLike.filter { it.cid eq entity.id }.map { it.uid }.toSet(),
            replays, // for performance, we don't load replay's replay
            createAt = entity.createdAt
        )
    }

    fun toDetails(entity: CommentEntity, isLoadChildren: Boolean = true): CommentDetails {
        val replayTo = entity.parentId?.let { parentId ->
            db.comment.find { it.id eq parentId }
        }?.let {
            toDetails(it, false)
        }
        val replays = if (isLoadChildren) {
            entity.loadReplays(db).map { toDetails(it, false) }
        } else {
            listOf()
        }

        return CommentDetails(
            entity.id,
            pid = entity.parentId,
            commenter = userQuery.queryBy(entity.commentedBy),
            content = entity.content,
            thumpUpCount = db.commentLike.count { it.cid eq entity.id },
            parent = replayTo,
            replays = replays,
            createAt = entity.createdAt
        )
    }
}
