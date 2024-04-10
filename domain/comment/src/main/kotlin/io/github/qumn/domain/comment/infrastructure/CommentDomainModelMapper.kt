package io.github.qumn.domain.comment.infrastructure

import io.github.qumn.domain.comment.api.model.Comment
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.springframework.stereotype.Component

@Component
class CommentDomainModelMapper(val db: Database) {
    fun toDomain(entity: CommentEntity, isRecursive: Boolean = true): Comment {
        val replayTo = if (isRecursive) {
            entity.parentId?.let { parentId ->
                db.comment.find { it.id eq parentId }
            }?.let {
                toDomain(it, false)
            }
        } else null

        return Comment(
            entity.id,
            replayTo,
            entity.commentedBy,
            entity.subjectId,
            entity.subjectType,
            entity.content,
            db.commentLike.filter { it.cid eq entity.id }.map { it.uid }.toSet(),
            replays = entity.loadReplays(db).map(::toDomain),
            createAt = entity.createdAt
        )
    }
}
