package io.github.qumn.domain.system.api.user.model

import com.fasterxml.jackson.annotation.JsonValue
import io.github.qumn.framework.exception.BizArgumentError
import io.github.qumn.framework.exception.BizNotAllowedException
import io.github.qumn.framework.exception.bizRequire
import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId
import java.time.Instant

@JvmInline
value class UID(@get:JsonValue val value: Long) {

    companion object {
        fun generate(): UID {
            return UID(IdUtil.nextId())
        }
    }

    init {
        require(value > 0) {
            throw BizNotAllowedException("id should greater zero")
        }
    }
}

data class User(
    val uid: UID,
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

@JvmInline
value class Phone(val number: String) {
    init {
        bizRequire(number.length == 11) {
            BizArgumentError("手机号码不合法")
        }
    }
}

@JvmInline
value class Email(val address: String) {
    init {
        bizRequire(address.contains("@")) {
            BizArgumentError("邮箱地址不合法")
        }
    }
}