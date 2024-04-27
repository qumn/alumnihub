package io.github.qumn.domain.lostfound.cmd

import io.github.qumn.domain.lostfound.model.LostFoundFactory
import io.github.qumn.domain.lostfound.model.LostFoundID
import io.github.qumn.domain.lostfound.model.LostFounds
import io.github.qumn.framework.exception.BizNotAllowedException
import org.axonframework.commandhandling.CommandHandler
import org.springframework.stereotype.Component

@Component
class CommandHandler(
    val lostFounds: LostFounds,
) {
    @CommandHandler
    fun handle(cmd: PublishMissingItemCmd): LostFoundID {
        val lostFound = LostFoundFactory.createNew(
            cmd.publisherId,
            cmd.location,
            cmd.questions,
            cmd.missingItem
        )
        lostFounds.save(lostFound)
        return lostFound.id
    }

    @CommandHandler
    fun handle(cmd: ClaimCmd) {
        val lostFound = lostFounds.findBy(cmd.lostFoundID)
        val claimLostFound = lostFound.claim(cmd.answers, cmd.ownerId) ?: throw BizNotAllowedException("回答错误")
        lostFounds.save(claimLostFound)
    }

}