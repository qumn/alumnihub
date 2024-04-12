package infrasturcture

import arb.comment
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.qumn.domain.comment.infrastructure.CommentDomainModelMapper
import io.github.qumn.domain.comment.infrastructure.CommentRepository
import io.github.qumn.test.DBIntegrationSpec
import io.github.qumn.test.arb.id
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import org.springframework.context.annotation.Import

@Import(value = [CommentRepository::class, CommentDomainModelMapper::class])
class CommentRepositoryTest(
    private val repository: CommentRepository,
) : DBIntegrationSpec({
    val logger = KotlinLogging.logger {}
    "save comment should work" {
        checkAll(100, Arb.comment()) { comment ->
            repository.save(comment)
            repository.findById(comment.id) shouldBe comment
        }
    }
    "like should work" {
        checkAll(100, Arb.comment(), Arb.id()) { comment, uid ->
            val comment = comment.likeBy(uid)
            comment.likedUids shouldContain uid
            repository.save(comment)
            repository.findById(comment.id) shouldBe comment
        }
    }

    "unlike should work" {
        checkAll(100, Arb.comment(), Arb.id()) { comment, uid ->
            if (comment.likedUids.isNotEmpty()) {
                val uid = comment.likedUids.random()
                val comment = comment.unlikeBy(uid)
                comment.likedUids shouldNotContain uid
                repository.save(comment)
                repository.findById(comment.id) shouldBe comment
            }
        }
    }

    "replay should be save" {
        checkAll(1, Arb.comment(), Arb.string(maxSize = 200), Arb.id()) { comment, replayContent, uid ->
            val (comment, replay) = comment.replayBy(uid, replayContent)
            repository.save(comment)

            repository.findById(comment.id) shouldBe comment
        }
    }
})