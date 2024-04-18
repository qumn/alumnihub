package io.github.qumn.domain.comment.infrastructure

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.system.api.user.model.UID
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
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
            UID(entity.commentedBy),
            entity.subjectId,
            entity.subjectType,
            entity.content,
            db.commentLike.filter { it.cid eq entity.id }.map { UID(it.uid) }.toSet(),
            replays, // for performance, we don't load replay's replay
            createAt = entity.createdAt
        )
    }
}
