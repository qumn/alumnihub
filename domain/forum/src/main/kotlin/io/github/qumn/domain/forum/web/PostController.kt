package io.github.qumn.domain.forum.web

import io.github.qumn.domain.comment.api.model.CommentId
import io.github.qumn.domain.comment.api.query.CommentDetails
import io.github.qumn.domain.forum.cmd.CreateCommentCmd
import io.github.qumn.domain.forum.cmd.CreatePostCmd
import io.github.qumn.domain.forum.cmd.LikePostCmd
import io.github.qumn.domain.forum.cmd.SharePostCmd
import io.github.qumn.domain.forum.model.PostId
import io.github.qumn.domain.forum.model.Tag
import io.github.qumn.domain.forum.query.PostDetails
import io.github.qumn.domain.forum.query.PostPageParam
import io.github.qumn.domain.forum.query.PostQuery
import io.github.qumn.framework.security.LoginUser
import io.github.qumn.framework.storage.model.URN
import io.github.qumn.framework.web.common.Rsp
import io.github.qumn.framework.web.common.toRsp
import io.github.qumn.ktorm.page.Page
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    val cmdGateway: CommandGateway,
    val query: PostQuery,
) {
    // command
    @PostMapping
    fun create(@RequestBody req: CreatePostReq): Rsp<Long> {
        val creator = LoginUser.current.uid
        return cmdGateway.sendAndWait<Long>(
            CreatePostCmd(
                creator,
                req.title,
                req.content,
                req.tags,
                req.imgs
            )
        ).toRsp()
    }

    @PutMapping("/{pid}/liked")
    fun liked(@PathVariable("pid") pid: PostId) {
        val likedBy = LoginUser.current.uid
        cmdGateway.send<Unit>(LikePostCmd(likedBy, pid))
    }

    @PutMapping("/{pid}/shared")
    fun shared(@PathVariable("pid") pid: PostId) {
        val sharer = LoginUser.current.uid
        cmdGateway.send<Unit>(SharePostCmd(sharer, pid))
    }

    @PostMapping("/{pid}/comments")
    fun newComment(@PathVariable pid: PostId, @RequestBody newCommentReq: NewCommentReq): Rsp<CommentId> {
        val commenter = LoginUser.current.uid
        return cmdGateway.sendAndWait<CommentId>(
            CreateCommentCmd(
                commenter = commenter,
                postId = pid,
                newCommentReq.content
            )
        ).toRsp()
    }

    // query
    @GetMapping("/{pid}/comments")
    fun comments(@PathVariable pid: PostId): Rsp<List<CommentDetails>> {
        return query.queryCommentsBy(pid).toRsp()
    }

    @GetMapping("/{pid}")
    fun find(@PathVariable pid: PostId): Rsp<PostDetails> {
        return query.queryBy(pid).toRsp { "找不到帖子" }
    }

    @GetMapping("/page")
    fun page(param: PostPageParam): Rsp<Page<PostDetails>> {
        return query.page(param).toRsp()
    }

    data class NewCommentReq(
        val content: String,
    )

    data class CreatePostReq(
        val title: String,
        val content: String,
        val tags: List<Tag> = listOf(),
        val imgs: List<URN> = listOf(),
    )
}