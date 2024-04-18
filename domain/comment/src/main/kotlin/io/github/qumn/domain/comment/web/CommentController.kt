package io.github.qumn.domain.comment.web;

import io.github.qumn.domain.comment.command.LikeCmd
import io.github.qumn.domain.comment.command.ReplayCmd
import io.github.qumn.domain.comment.command.UnlikeCmd
import io.github.qumn.framework.security.LoginUser
import io.github.qumn.framework.web.common.Rsp
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(
    val gateway: CommandGateway,
) {
    @PutMapping("{cid}/like")
    fun likeCommand(@PathVariable("cid") cid: Long): Rsp<Unit> {
        val uid = LoginUser.current.uid
        gateway.send<Unit>(LikeCmd(cid, uid))
        return Rsp.success()
    }

    @PutMapping("{cid}/unlike")
    fun unlikeCommand(@PathVariable("cid") cid: Long): Rsp<Unit> {
        val uid = LoginUser.current.uid
        gateway.send<Unit>(UnlikeCmd(cid, uid))
        return Rsp.success()
    }


    @PostMapping("{cid}/replay")
    fun replay(@PathVariable("cid") cid: Long, @RequestBody replayReq: ReplayReq) {
        val uid = LoginUser.current.uid
        gateway.send<Unit>(ReplayCmd(cid, uid, replayReq.content))
    }

    class ReplayReq(
        val content: String,
    )
}
