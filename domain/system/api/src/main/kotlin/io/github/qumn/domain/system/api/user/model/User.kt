package io.github.qumn.domain.system.api.user.model

import io.github.qumn.framework.exception.BizArgumentError
import io.github.qumn.framework.exception.bizRequire
import java.time.Instant


data class User(
    val uid: Long,
    val name: String,
    val password: String,
    val gender: Gender,
    val birthDay: Instant?,
    val phone: Phone,
    val email: Email?,
)

enum class Gender {
    FEMALE, MALE, UNKNOWN
}

data class Phone(val number: String) {
    init {
        bizRequire(number.length == 11) {
            BizArgumentError("手机号码不合法")
        }
    }
}

data class Email(val address: String) {
    init {
        bizRequire(address.contains("@")) {
            BizArgumentError("邮箱地址不合法")
        }
    }
}