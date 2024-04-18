package io.github.qumn.domain.system.user

import io.github.qumn.domain.system.api.user.model.Gender
import io.github.qumn.domain.system.api.user.model.Phone
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.domain.system.api.user.model.User
import io.github.qumn.domain.system.user.command.RegisteredUserCommand

class UserFactory {
    companion object {
        /**
         * create a new user
         */
        fun create(name: String, password: String, phoneNumber: String): User {
            val phone = Phone(phoneNumber)
            return User(
                uid = UID.generate(),
                name = name,
                phone = phone,
                password = password,
                gender = Gender.UNKNOWN,
                birthDay = null,
                email = null,
            )
        }

        fun from(command: RegisteredUserCommand): User {
            return create(
                command.username,
                command.password,
                command.phoneNumber
            )
        }

    }
}