package io.github.qumn.domain.forum.model

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URN
import io.github.qumn.util.time.nowMicros

object PostFactory {
    fun createNew(
        creator: UID, title: String, content: String,
        tags:
        List<Tag> = listOf(),
        imgs: List<URN> = listOf(),
    ): Post {
        return Post(
            PostId.generate(),
            creator,
            title,
            content,
            tags,
            imgs,
            thumbUpBy = listOf(),
            sharedBy = listOf(),
            createdAt = nowMicros()
        )
    }
}