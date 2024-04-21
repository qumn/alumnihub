package io.github.qumn.domain.forum.infrastructure

import io.github.qumn.domain.forum.model.Post
import io.github.qumn.domain.forum.query.PostDetails
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.domain.system.api.user.query.UserQuery

object PostDomainMapper {
    fun toDomain(entity: PostEntity): Post {
        return Post(
            entity.id,
            title = entity.title,
            content = entity.content,
            tags = entity.tags,
            imgs = entity.imgs,
            thumbUpBy = entity.thumbUpBy,
            sharedBy = entity.sharedBy,
            creatorId = UID(entity.createdBy),
            createdAt = entity.createdAt
        )
    }

    fun toEntity(domain: Post): PostEntity {
        return PostEntity {
            id = domain.id
            title = domain.title
            content = domain.content
            tags = domain.tags
            imgs = domain.imgs
            thumbUpBy = domain.thumbUpBy
            sharedBy = domain.sharedBy
            createdBy = domain.creatorId.value
            createdAt = domain.createdAt
        }
    }

    fun toDetails(entity: PostEntity, userQuery: UserQuery): PostDetails {
        val creator = userQuery.queryBy(UID(entity.createdBy))
        return PostDetails(
            entity.id,
            title = entity.title,
            content = entity.content,
            tags = entity.tags,
            imgs = entity.imgs.map { it.toURL().location },
            thumbUpCount = entity.thumbUpBy.size,
            shareCount = entity.sharedBy.size,
            creator = creator.uid,
            createdAt = entity.createdAt,
            commentCount = 0,
            creatorAvatar = "", // TODO
            creatorName = creator.name
        )
    }
}

fun PostEntity.toDomain(): Post {
    return PostDomainMapper.toDomain(this)
}

fun PostEntity.toDetails(userQuery: UserQuery): PostDetails {
    return PostDomainMapper.toDetails(this, userQuery)
}

fun Post.toEntity(): PostEntity {
    return PostDomainMapper.toEntity(this)
}