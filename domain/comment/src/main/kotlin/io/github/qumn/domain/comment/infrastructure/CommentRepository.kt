package io.github.qumn.domain.comment.infrastructure

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.comment.api.model.CommentId
import io.github.qumn.domain.comment.api.model.Comments
import io.github.qumn.domain.comment.api.model.SubjectType
import io.github.qumn.domain.comment.api.query.CommentDetails
import io.github.qumn.domain.comment.api.query.CommentQuery
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.util.time.nowMicros
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.batchInsert
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.*
import org.springframework.stereotype.Component

@Component
class CommentRepository(val db: Database, val domainMapper: CommentDomainModelMapper) : Comments, CommentQuery {

    override fun delete(id: CommentId) {
        db.comment.removeIf { it.id eq id }
        clearLikesOf(id)
    }

    override fun findBySubjectId(subjectType: SubjectType, sid: Long): List<Comment> {
        return db.comment.filter { (it.subjectType eq subjectType) and (it.subjectId eq sid) }
            .map { domainMapper.toDomain(it) }
    }

    override fun tryFindById(id: CommentId): Comment? {
        val commentEntity = tryFindEntityById(id) ?: return null
        return domainMapper.toDomain(commentEntity)
    }

    private fun tryFindEntityById(id: CommentId): CommentEntity? {
        return db.comment.find { it.id eq id }
    }

    private fun findByParentId(pid: CommentId): List<CommentEntity> {
        return db.comment.filter { it.parentId eq pid }.toList()
    }

    private fun findEntityByIds(ids: List<CommentId>): List<CommentEntity> {
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
    private fun syncLikes(commentId: CommentId, newLikedBy: Set<UID>) {
        val likedByExisted = getLikedBy(commentId)

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

    private fun clearLikesOf(cid: CommentId) {
        db.commentLike.removeIf { it.cid eq cid }
    }

    fun getLikedBy(cid: CommentId): Set<UID> {
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

    override fun tryQueryBy(cid: CommentId): CommentDetails? {
        return db.comment.find { it.id eq cid }?.let { domainMapper.toDetails(it) }
    }

    override fun queryBy(subjectType: SubjectType, subjectId: Long): List<CommentDetails> {
        return db.comment.filter { (it.subjectType eq subjectType) and (it.subjectId eq subjectId) }
            .map { domainMapper.toDetails(it) }
    }

}