package io.github.qumn.test.arb

import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId
import io.kotest.property.Arb
import io.kotest.property.arbitrary.Codepoint
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.of
import io.kotest.property.arbitrary.string


fun Arb.Companion.phone() = Arb.string(
    size = 11, codepoints = Arb.of(('0'.code..'9'.code).map(::Codepoint))
)

fun Arb.Companion.id() = arbitrary {
    IdUtil.nextId()
}

fun Arb.Companion.name() = Arb.string(
    minSize = 1, maxSize = 50, codepoints = Arb.of(('a'.code..'z'.code).map(::Codepoint))
)

fun Arb.Companion.address() = Arb.string(
    minSize = 1, maxSize = 100, codepoints = Arb.of(('a'.code..'z'.code).map(::Codepoint))
)

fun Arb.Companion.lonlat(): Arb<Pair<Double, Double>> = arbitrary {
    val lon = it.random.nextDouble(-180.0, 180.0)
    val lat = it.random.nextDouble(-90.0, 90.0)
    Pair(lon, lat)
}