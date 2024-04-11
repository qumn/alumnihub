package io.github.qumn.domain.comment.api.model

import java.time.Instant


enum class SubjectType {
    Trade, Forum, LostFound
}

/**
 * each comment is itself aggregate root, so the replays only contains one level
 */
data class Comment(
    val id: Long,
    var replayTo: Comment?,
    val commenterId: Long,
    val subjectId: Long,
    val subjectType: SubjectType,
    val content: String,
    val likedUids: Set<Long>,
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
    fun replayBy(uid: Long, content: String): Pair<Comment, Comment> {
        val replay = CommentFactory.create(uid, this.subjectType, this.subjectId, content, this)
        return copy(replays = this.replays + replay) to replay
    }

    fun likeBy(uid: Long): Comment {
        return this.copy(likedUids = likedUids + uid)
    }

    fun unlikeBy(uid: Long): Comment {
        return this.copy(likedUids = likedUids - uid)
    }
}