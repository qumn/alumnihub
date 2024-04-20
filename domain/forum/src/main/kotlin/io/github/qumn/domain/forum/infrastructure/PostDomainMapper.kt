package io.github.qumn.domain.forum.infrastructure

import io.github.qumn.domain.forum.model.Post
import io.github.qumn.domain.forum.query.PostDetails
import io.github.qumn.domain.system.api.user.model.UID

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

    fun toDetails(entity: PostEntity): PostDetails {
        return PostDetails(
            entity.id,
            title = entity.title,
            content = entity.content,
            tags = entity.tags,
            imgs = entity.imgs.map { it.toURL() },
            thumbUpBy = entity.thumbUpBy,
            sharedBy = entity.sharedBy,
            creatorId = UID(entity.createdBy),
            createdAt = entity.createdAt
        )
    }
}

fun PostEntity.toDomain(): Post {
    return PostDomainMapper.toDomain(this)
}

fun PostEntity.toDetails(): PostDetails {
    return PostDomainMapper.toDetails(this)
}

fun Post.toEntity(): PostEntity {
    return PostDomainMapper.toEntity(this)
}