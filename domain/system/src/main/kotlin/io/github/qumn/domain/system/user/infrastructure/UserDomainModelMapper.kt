package io.github.qumn.domain.system.user.infrastructure

import io.github.qumn.domain.system.api.user.model.Email
import io.github.qumn.domain.system.api.user.model.Phone
import io.github.qumn.domain.system.api.user.model.User
import org.springframework.stereotype.Component

@Component
class UserDomainModelMapper {
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
}