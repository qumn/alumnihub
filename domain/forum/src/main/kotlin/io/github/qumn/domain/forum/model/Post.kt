package io.github.qumn.domain.forum.model

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.exception.BizNotAllowedException
import io.github.qumn.framework.storage.model.URN
import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId
import java.time.Instant

@JvmInline
value class PostId(val value: Long) : Comparable<PostId> {

    companion object {
        fun generate(): PostId {
            return PostId(IdUtil.nextId())
        }
    }

    init {
        require(value > 0) {
            throw BizNotAllowedException("id should greater zero")
        }
    }

    override fun compareTo(other: PostId): Int =
        value.compareTo(other.value)
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


    fun addSharer(uid: UID): Post {
        return this.copy(sharedBy = this.sharedBy + uid)
    }

    fun addThumbUp(uid: UID): Post {
        return this.copy(thumbUpBy = this.thumbUpBy + uid)
    }

}