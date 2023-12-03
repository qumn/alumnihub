package io.github.qumn.util.time

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * be used for the field will be persisted in postgres
 *
 * why need the function?
 * because the precision of timestamp is up to 6 in postgresql and the precision of instant is up to 9 in jvm17
 */
fun nowMicros(): Instant =
    Instant.now().truncatedTo(ChronoUnit.MICROS)