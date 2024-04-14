package io.github.qumn.domain.system.api.user.query

import io.github.qumn.domain.system.api.user.model.Email
import io.github.qumn.domain.system.api.user.model.Gender
import io.github.qumn.domain.system.api.user.model.Phone
import java.time.Instant

data class UserDetails(
    val uid: Long,
    val name: String,
    val gender: Gender,
    val birthDay: Instant?,
    val phone: Phone,
    val email: Email?,
)
