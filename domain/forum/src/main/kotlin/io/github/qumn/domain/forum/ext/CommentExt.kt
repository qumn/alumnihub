package io.github.qumn.domain.forum.ext

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.comment.api.model.Comments
import io.github.qumn.domain.comment.api.model.SubjectType
import io.github.qumn.domain.comment.api.query.CommentDetails
import io.github.qumn.domain.comment.api.query.CommentQuery
import io.github.qumn.domain.forum.model.Post
import io.github.qumn.domain.forum.model.PostId


fun Comments.getBy(id: PostId): List<Comment> {
    return this.findBy(SubjectType.Forum, id.value)
}

fun Post.comments(commentQuery: CommentQuery): List<CommentDetails> {
    return commentQuery.queryBy(SubjectType.Forum, this.id.value)
}