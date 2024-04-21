package io.github.qumn.domain.forum.cmd

import io.github.qumn.domain.comment.api.model.CommentFactory
import io.github.qumn.domain.comment.api.model.CommentId
import io.github.qumn.domain.comment.api.model.Comments
import io.github.qumn.domain.comment.api.model.SubjectType
import io.github.qumn.domain.forum.model.PostFactory
import io.github.qumn.domain.forum.model.PostId
import io.github.qumn.domain.forum.model.Posts
import io.github.qumn.framework.exception.BizNotAllowedException
import org.axonframework.commandhandling.CommandHandler
import org.springframework.stereotype.Component

@Component
class CmdHandler(
    val posts: Posts,
    val comments: Comments,
) {
    @CommandHandler
    fun handle(cmd: CreatePostCmd): PostId {
        val post = PostFactory.createNew(
            cmd.creator,
            cmd.title,
            cmd.content,
            cmd.tags,
            cmd.imgs
        )
        posts.save(post)
        return post.id
    }

    @CommandHandler
    fun handle(cmd: LikePostCmd) {
        val post = posts.findBy(cmd.postId)
        val npost = post.addThumbUp(cmd.likedBy)
        posts.save(npost)
    }

    @CommandHandler
    fun handle(cmd: SharePostCmd) {
        val post = posts.findBy(cmd.postId)
        val npost = post.addSharer(cmd.sharer)
        posts.save(npost)
    }

    @CommandHandler
    fun handle(cmd: CreateCommentCmd): CommentId {
        if (posts.notContains(cmd.postId)) {
            throw BizNotAllowedException("评论的帖子不存在")
        }
        val comment = CommentFactory.create(
            cmd.commenter,
            SubjectType.Forum,
            cmd.postId.value,
            cmd.content
        )
        comments.save(comment)
        return comment.id
    }
}