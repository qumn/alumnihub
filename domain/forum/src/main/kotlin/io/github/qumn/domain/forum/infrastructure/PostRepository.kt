package io.github.qumn.domain.forum.infrastructure

import io.github.qumn.domain.forum.model.Post
import io.github.qumn.domain.forum.model.PostId
import io.github.qumn.domain.forum.model.Posts
import io.github.qumn.domain.forum.query.PostDetails
import io.github.qumn.domain.forum.query.PostPageParam
import io.github.qumn.domain.forum.query.PostQuery
import io.github.qumn.ktorm.ext.exist
import io.github.qumn.ktorm.page.Page
import io.github.qumn.ktorm.search.searchPage
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.removeIf
import org.ktorm.entity.update
import org.springframework.stereotype.Repository

@Repository
class PostRepository(
    val db: Database,
) : Posts, PostQuery {
    override fun tryFindBy(postId: PostId): Post? {
        return tryFindEntityBy(postId)?.toDomain()
    }

    private fun tryFindEntityBy(postId: PostId): PostEntity? {
        return db.posts.find { it.id eq postId }
    }

    override fun contains(postId: PostId): Boolean {
        return db.posts.exist { it.id eq postId }
    }

    override fun save(post: Post) {
        if (contains(post.id)) {
            db.posts.update(post.toEntity())
        } else {
            db.posts.add(post.toEntity())
        }
    }

    override fun delete(postId: PostId) {
        db.posts.removeIf { it.id eq postId }
    }

    override fun page(param: PostPageParam): Page<PostDetails> {
        return db.posts.searchPage(param).transform { it.toDetails() }
    }

    override fun tryQueryBy(postId: PostId): PostDetails? {
        return tryFindEntityBy(postId)?.toDetails()
    }
}