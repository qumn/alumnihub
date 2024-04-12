package io.github.qumn.domain.comment.command

import io.github.qumn.domain.comment.api.model.Comments
import org.axonframework.commandhandling.CommandHandler
import org.springframework.stereotype.Component

@Component
class CommentCmdHanlder(
    val comments: Comments,
) {
    @CommandHandler
    fun handle(likeCmd: LikeCmd) {
        val comment = comments.findById(likeCmd.cid)
        comments.save(comment.likeBy(likeCmd.uid))
    }

    @CommandHandler
    fun handle(unlikeCmd: UnlikeCmd) {
        val comment = comments.findById(unlikeCmd.cid)
        comments.save(comment.unlikeBy(unlikeCmd.uid))
    }

    @CommandHandler
    fun handle(replayCmd: ReplayCmd) {
        val comment = comments.findById(replayCmd.replayToId)
        val (ncomment, _) = comment.replayBy(replayCmd.replayerId, replayCmd.content)
        comments.save(ncomment)
    }
}