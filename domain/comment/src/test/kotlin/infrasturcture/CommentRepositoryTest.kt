package infrasturcture

import arb.comment
import io.github.qumn.domain.comment.infrastructure.CommentDomainModelMapper
import io.github.qumn.domain.comment.infrastructure.CommentRepository
import io.github.qumn.domain.system.api.user.test.user
import io.github.qumn.test.DBIntegrationSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import org.springframework.context.annotation.Import

@Import(value = [CommentRepository::class, CommentDomainModelMapper::class])
class CommentRepositoryTest(
    private val repository: CommentRepository,
) : DBIntegrationSpec({
    "save comment should work" {
        checkAll(100, Arb.comment()) { comment ->
            repository.save(comment)
            repository.findById(comment.id) shouldBe comment
        }
    }
    "like should work" {
        checkAll(100, Arb.comment(), Arb.user()) { comment, user ->
            val comment = comment.likeBy(user)
            comment.likedUids shouldContain user.uid
            repository.save(comment)
            repository.findById(comment.id) shouldBe comment
        }
    }

    "unlike should work" {
        checkAll(100, Arb.comment(), Arb.int(), Arb.user()) { comment, idx, user ->
            if (comment.likedUids.isNotEmpty()) {
                val uid = comment.likedUids.random()
                val user = user.copy(uid = uid)
                val comment = comment.unlikeBy(user)
                comment.likedUids shouldNotContain user.uid
                repository.save(comment)
                repository.findById(comment.id) shouldBe comment
            }
        }
    }
})