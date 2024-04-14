package io.github.qumn.framework.storage.arb

import io.github.qumn.framework.storage.model.URN
import io.kotest.property.Arb
import io.kotest.property.arbitrary.Codepoint
import io.kotest.property.arbitrary.alphanumeric
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.string


fun Arb.Companion.urn() = Arb.string(10, 30, Codepoint.alphanumeric()).map {
    URN("/${it}")
}