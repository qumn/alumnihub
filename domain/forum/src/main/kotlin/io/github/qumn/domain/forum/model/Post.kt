package io.github.qumn.domain.forum.model

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.domain.system.api.user.model.User
import io.github.qumn.framework.exception.BizNotAllowedException
import io.github.qumn.framework.storage.model.URN
import java.time.Instant

@JvmInline
value class PostId(val value: Long) {
    init {
        require(value > 0) {
            throw BizNotAllowedException("id should greater zero")
        }
    }
}

@JvmInline
value class Tag(val value: String) {
    init {
        require(value.isNotEmpty() && value.length <= 10) {
            throw BizNotAllowedException("tag should not empty and lesser than ten chars")
        }
    }
}

data class Post(
    val id: PostId,
    val creatorId: UID,
    val title: String,
    val content: String,

    val tags: List<Tag>,
    val imgs: List<URN>,

    val thumbUpBy: List<UID>,
    val sharedBy: List<UID>,

    val createdAt: Instant,
) {
    val thumbUpCount: Int
        get() = thumbUpBy.size
    val sharedCount: Int
        get() = sharedBy.size


    fun addSharer(user: User): Post {
        return this.copy(sharedBy = this.sharedBy + user.uid)
    }

    fun addThumbUp(user: User): Post {
        return this.copy(thumbUpBy = this.thumbUpBy + user.uid)
    }

}