package io.github.qumn.domain.system.api.user.test

import io.github.qumn.domain.system.api.user.model.*
import io.github.qumn.test.arb.id
import io.github.qumn.test.arb.name
import io.github.qumn.test.arb.phone
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*

fun Arb.Companion.user() = Arb.bind(
    Arb.id().map { UID(it) },
    Arb.name(),
    Arb.string(range = 8..16),
    Arb.email(),
    Arb.phone(),
    Arb.instant(),
    Arb.enum<Gender>(),

    ) { uid, name, password, email, phone, birthDay, gender ->
    User(
        uid = uid,
        name = name,
        password = password,
        email = Email(email),
        phone = Phone(phone),
        birthDay = birthDay,
        gender = gender
    )
}
