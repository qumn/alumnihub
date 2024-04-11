package io.github.qumn.domain.comment.infrastructure

import io.github.qumn.domain.comment.api.model.Comment
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.ktorm.logging.Logger
import org.springframework.stereotype.Component

@Component
class CommentDomainModelMapper(val db: Database) {
    fun toDomain(entity: CommentEntity, isLoadChildren: Boolean = true): Comment {
        val replayTo = entity.parentId?.let { parentId ->
            db.comment.find { it.id eq parentId }
        }?.let {
            toDomain(it, false)
        }
        val replays = if (isLoadChildren) {
            entity.loadReplays(db)
                .map { toDomain(it, false) }
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
}
