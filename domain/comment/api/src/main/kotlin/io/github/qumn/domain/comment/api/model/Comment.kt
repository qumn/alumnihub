package io.github.qumn.domain.comment.api.model

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId
import java.time.Instant


enum class SubjectType {
    Trade, Forum, LostFound
}

@JvmInline
value class CommentId(val value: Long) {
    companion object {
        fun generate(): CommentId {
            return CommentId(IdUtil.nextId())
        }
    }
}

/**
 * each comment is itself aggregate root, so the replays only contains one level
 */
data class Comment(
    val id: CommentId,
    var replayTo: Comment?,
    val commenterId: UID,
    val subjectId: Long,
    val subjectType: SubjectType,
    val content: String,
    val likedUids: Set<UID>,
    val replays: List<Comment>,
    val createAt: Instant,
) {

    val likeCount: Int
        get() = likedUids.count()

    val replayCount: Int
        get() = replays.count()

    /**
     * @return first is the new comment, the second is replay
     */
    fun replayBy(uid: UID, content: String): Pair<Comment, Comment> {
        val replay = CommentFactory.create(uid, this.subjectType, this.subjectId, content, this)
        return copy(replays = this.replays + replay) to replay
    }

    fun likeBy(uid: UID): Comment {
        return this.copy(likedUids = likedUids + uid)
    }

    fun unlikeBy(uid: UID): Comment {
        return this.copy(likedUids = likedUids - uid)
    }
}