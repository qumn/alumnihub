package io.github.qumn.domain.comment.infrastructure

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.comment.api.model.Comments
import io.github.qumn.util.time.nowMicros
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.batchInsert
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.*
import org.springframework.stereotype.Component

@Component
class CommentRepository(val db: Database, val domainMapper: CommentDomainModelMapper) : Comments {

    override fun delete(id: Long) {
        db.comment.removeIf { it.id eq id }
        clearLikesOf(id)
    }

    override fun tryFindById(id: Long): Comment? {
        val commentEntity = tryFindEntityById(id) ?: return null
        return domainMapper.toDomain(commentEntity)
    }

    private fun tryFindEntityById(id: Long): CommentEntity? {
        return db.comment.find { it.id eq id }
    }

    private fun findByParentId(pid: Long): List<CommentEntity> {
        return db.comment.filter { it.parentId eq pid }.toList()
    }

    private fun findEntityByIds(ids: List<Long>): List<CommentEntity> {
        return if (ids.isEmpty()) {
            listOf()
        } else {
            db.comment.filter { it.id inList ids }.toList()
        }
    }

    override fun save(comment: Comment) {
        tryFindEntityById(comment.id)?.let {
            updateComment(comment)
        } ?: run {
            insertNew(comment)
        }
    }

    private fun updateComment(comment: Comment) {
        val commentEntity = comment.toEntity()
        db.comment.update(commentEntity)
        syncLikes(comment.id, comment.likedUids)
        syncReplays(comment)
    }

    private fun insertNew(comment: Comment) {
        val commentEntity = comment.toEntity()
        db.comment.add(commentEntity)
        syncLikes(comment.id, comment.likedUids)
        syncReplays(comment)
    }

    /**
     * sync the unliked in database with newLikedBy set
     */
    private fun syncLikes(commentId: Long, newLikedBy: Set<Long>) {
        val likedByExisted = likedByUid(commentId)

        val unlikedUids = likedByExisted - newLikedBy
        val newLikedUids = newLikedBy - likedByExisted

        // remove the unliked user
        if (unlikedUids.isNotEmpty()) {
            db.commentLike.removeIf {
                (it.cid eq commentId) and (it.uid inList unlikedUids)
            }
        }

        // add the new liked user
        if (newLikedUids.isEmpty()) return

        db.batchInsert(CommentLikeTable) {
            for (newLikedUid in newLikedUids) {
                item {
                    set(it.cid, commentId)
                    set(it.uid, newLikedUid)
                    set(it.createdAt, nowMicros())
                }
            }
        }
    }


    private fun syncReplays(comment: Comment) {
        val newReplays = comment.replays

        val existReplayIds = findByParentId(comment.id).map { it.id }.toSet()
        val newReplayIds = newReplays.map { it.id }.toSet()

        val shouldDeleteIds = existReplayIds.toSet() - newReplayIds
        val shouldSaveReplayIds = newReplayIds - existReplayIds
        val shouldSaveReplays = newReplays.filter { shouldSaveReplayIds.contains(it.id) }

        if (shouldDeleteIds.isNotEmpty()) {
            db.comment.removeIf { it.id inList shouldDeleteIds }
        }
        for (shouldSaveReplay in shouldSaveReplays) {
            save(shouldSaveReplay)
        }
    }

    private fun clearLikesOf(cid: Long) {
        db.commentLike.removeIf { it.cid eq cid }
    }

    fun likedByUid(cid: Long): Set<Long> {
        return db.commentLike.filter { it.cid eq cid }.map { it.uid }.toSet()
    }


    private fun Comment.toEntity(): CommentEntity {
        val comment = this
        return CommentEntity {
            id = comment.id
            parentId = comment.replayTo?.id
            commentedBy = comment.commenterId
            subjectType = comment.subjectType
            subjectId = comment.subjectId
            content = comment.content
            createdAt = comment.createAt
        }
    }

}