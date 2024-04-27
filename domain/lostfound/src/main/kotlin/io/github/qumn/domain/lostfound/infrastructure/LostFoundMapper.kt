package io.github.qumn.domain.lostfound.infrastructure

import io.github.qumn.domain.lostfound.model.LostFound
import io.github.qumn.domain.lostfound.model.MissingItem
import io.github.qumn.domain.lostfound.query.LostFoundOverview
import io.github.qumn.domain.system.api.user.query.UserDetails

object LostFoundMapper {
    fun toDomain(entity: LostFoundEntity): LostFound {
        return LostFound(
            entity.id,
            entity.publisherId,
            entity.ownerId,
            entity.status,
            entity.location,
            entity.questions,
            MissingItem(
                entity.missingDesc, entity.missingImgs
            )
        )
    }

    fun toEntity(domain: LostFound): LostFoundEntity {
        return LostFoundEntity {
            id = domain.id
            publisherId = domain.publisherId
            ownerId = domain.ownerId
            status = domain.status
            location = domain.location
            questions = domain.questions
            missingDesc = domain.missingItem.desc
            missingImgs = domain.missingItem.imgs
        }
    }

    fun toOverview(entity: LostFoundEntity, publisher: UserDetails): LostFoundOverview {
        val img = if (entity.missingImgs.size > 0) entity.missingImgs[0].toURL().location else ""
        return LostFoundOverview(
            entity.id.value,
            img,
            entity.missingDesc,
            entity.location,
            entity.questions.map { it.content },
            publisher.uid,
            publisher.avatar,
            publisher.name,
            entity.pickupTime
        )

    }


}

fun LostFoundEntity.toDomain(): LostFound {
    return LostFoundMapper.toDomain(this)
}

fun LostFoundEntity.toOverview(publisher: UserDetails): LostFoundOverview {
    return LostFoundMapper.toOverview(this, publisher)
}

fun LostFound.toEntity(): LostFoundEntity {
    return LostFoundMapper.toEntity(this)
}
