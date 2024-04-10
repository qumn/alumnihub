package arb

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.comment.api.model.SubjectType
import io.github.qumn.test.arb.id
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import java.time.Instant
import java.time.temporal.ChronoUnit

fun Arb.Companion.MicrosInstant(
    minValue: Instant = Instant.EPOCH,
    maxValue: Instant = Instant.now(),
): Arb<Instant> =
    Arb.instant(minValue, maxValue).map { it.truncatedTo(ChronoUnit.MICROS) }

fun Arb.Companion.comment(): Arb<Comment> = Arb.bind(
    Arb.id(),
    Arb.id(),
    Arb.id(),
    Arb.enum<SubjectType>(),
    Arb.string(maxSize = 200),
    Arb.array(Arb.id()).map { it.toSet() },
    Arb.MicrosInstant()
) { commentId: Long, commenterId, subjectId, subjectType, content, likedBy, createAt ->
    Comment(commentId, null, commenterId, subjectId, subjectType, content, likedBy, listOf(), createAt)
}