package io.github.qumn.domain.system.api.user.model

import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId

class UserFactory {
    companion object {
        /**
         * create a new user
         */
        fun create(name: String, password: String, phoneNumber: String): User {
            val phone = Phone(phoneNumber)
            return User(
                uid = IdUtil.nextId(),
                name = name,
                phone = phone,
                password = password,
                gender = Gender.UNKNOWN,
                birthDay = null,
                email = null,
            )
        }

    }
}