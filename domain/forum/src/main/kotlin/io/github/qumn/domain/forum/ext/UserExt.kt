package io.github.qumn.domain.forum.ext

import io.github.qumn.domain.forum.model.Post
import io.github.qumn.domain.system.api.user.model.User

fun User.share(post: Post): Post {
    return post.addSharer(this)
}