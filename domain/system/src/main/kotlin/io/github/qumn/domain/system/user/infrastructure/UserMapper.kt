package io.github.qumn.domain.system.user.infrastructure

import io.github.qumn.domain.system.api.user.model.Email
import io.github.qumn.domain.system.api.user.model.Phone
import io.github.qumn.domain.system.api.user.model.User
import io.github.qumn.domain.system.api.user.query.UserDetails
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toUser(entity: UserEntity): User {
        return User(
            uid = entity.uid,
            name = entity.name,
            gender = entity.gender,
            birthDay = entity.birthDay,
            phone = Phone(entity.phone),
            password = entity.password,
            email = entity.email?.let(::Email)
        )
    }

    fun toEntity(user: User): UserEntity {
        return UserEntity {
            uid = user.uid
            name = user.name
            password = user.password
            gender = user.gender
            birthDay = user.birthDay
            phone = user.phone.number
            email = user.email?.address
        }
    }

    fun toDetails(entity: UserEntity): UserDetails {
        return UserDetails(
            uid = entity.uid.value,
            name = entity.name,
            gender = entity.gender,
            birthDay = entity.birthDay,
            phone = Phone(entity.phone),
            email = entity.email?.let(::Email)
        )

    }
}