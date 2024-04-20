package io.github.qumn.domain.forum.infastructure

import io.github.qumn.domain.forum.infrastructure.PostRepository
import io.github.qumn.domain.forum.post
import io.github.qumn.test.DBIntegrationSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.checkAll
import org.springframework.context.annotation.Import

@Import(value = [PostRepository::class])
class PostRepositoryTest(
    postRepository: PostRepository,
) : DBIntegrationSpec({
    "save post should work" {
        checkAll(100, Arb.post()) { post ->
            postRepository.save(post)
            postRepository.findBy(post.id) shouldBe post
        }
    }
    "contains should work" {
        checkAll(100, Arb.post()) { post ->
            postRepository.contains(post.id) shouldBe false
            postRepository.save(post)
            postRepository.contains(post.id) shouldBe true
        }
    }
    "deleted should work" {
        checkAll(100, Arb.post()) { post ->
            postRepository.save(post)
            postRepository.contains(post.id) shouldBe true
            postRepository.delete(post.id)
            postRepository.contains(post.id) shouldBe false
        }
    }
})