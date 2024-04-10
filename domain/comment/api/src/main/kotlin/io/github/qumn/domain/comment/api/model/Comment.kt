package io.github.qumn.domain.comment.api.model

import io.github.qumn.domain.system.api.user.model.User
import java.time.Instant


enum class SubjectType {
    Trade, Forum, LostFound
}

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

    fun replayBy(uid: Long, content: String): Comment {
        val replay = CommentFactory.create(uid, this.subjectType, this.subjectId, content)
        return copy(replays = this.replays + replay)
    }

    fun likeBy(user: User): Comment {
        return this.copy(likedUids = likedUids + user.uid)
    }

    fun unlikeBy(user: User): Comment {
        return this.copy(likedUids = likedUids - user.uid)
    }
}