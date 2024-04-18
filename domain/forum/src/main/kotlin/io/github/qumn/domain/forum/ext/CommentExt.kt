package io.github.qumn.domain.forum.ext

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.comment.api.model.Comments
import io.github.qumn.domain.comment.api.model.SubjectType
import io.github.qumn.domain.forum.model.Post
import io.github.qumn.domain.forum.model.PostId


fun Comments.getBy(id: PostId): List<Comment> {
    return this.findBySubjectId(SubjectType.Forum, id.value)
}

fun Post.comments(comments: Comments): List<Comment> {
    return comments.getBy(this.id)
}