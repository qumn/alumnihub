package io.github.qumn.framework.domain.user.infrastructure

import io.github.qumn.framework.domain.user.model.Email
import io.github.qumn.framework.domain.user.model.Phone
import io.github.qumn.framework.domain.user.model.User
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
            email = Email(entity.email)
        )
    }
}