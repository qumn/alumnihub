package io.github.qumn.domain.system.api.user.model

import java.time.Instant


enum class Gender {
    FEMALE, MALE, UNKNOWN
}

data class User(
    val uid: Long,
    val name: String,
    val password: String,
    val gender: Gender,
    val birthDay: Instant?,
    val phone: Phone,
    val email: Email?,
)

data class Phone(val number: String) {
    init {
        require(number.length == 11) {
            "the length of phone number must be 11"
        }
    }
}

data class Email(val address: String) {
    init {
        require(address.contains("@")) {
            "the email address must contains @"
        }
    }
}