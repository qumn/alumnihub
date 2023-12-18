package io.github.qumn.domain.system.api.user.test

import io.github.qumn.domain.system.api.user.model.Email
import io.github.qumn.domain.system.api.user.model.Gender
import io.github.qumn.domain.system.api.user.model.Phone
import io.github.qumn.domain.system.api.user.model.User
import io.github.qumn.test.arb.id
import io.github.qumn.test.arb.name
import io.github.qumn.test.arb.phone
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.email
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.instant

fun Arb.Companion.user() = Arb.bind(
    Arb.id(),
    Arb.name(),
    Arb.email(),
    Arb.phone(),
    Arb.instant(),
    Arb.enum<Gender>()
) { uid, name, email, phone, birthDay, gender ->
    User(uid = uid, name = name, email = Email(email), phone = Phone(phone), birthDay = birthDay, gender = gender)
}
