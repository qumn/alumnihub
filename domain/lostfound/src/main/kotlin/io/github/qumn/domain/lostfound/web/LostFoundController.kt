package io.github.qumn.domain.lostfound.web

import io.github.qumn.domain.lostfound.cmd.ClaimCmd
import io.github.qumn.domain.lostfound.cmd.PublishMissingItemCmd
import io.github.qumn.domain.lostfound.model.LostFoundID
import io.github.qumn.domain.lostfound.model.MissingItem
import io.github.qumn.domain.lostfound.model.Question
import io.github.qumn.domain.lostfound.query.LostFoundOverview
import io.github.qumn.domain.lostfound.query.LostFoundQueryHandler
import io.github.qumn.domain.lostfound.query.LostFoundSearchParam
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.security.LoginUser
import io.github.qumn.framework.storage.model.URN
import io.github.qumn.framework.web.common.Rsp
import io.github.qumn.framework.web.common.toRsp
import io.github.qumn.ktorm.page.Page
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lostfound")
class LostFoundController(
    val cmdGateway: CommandGateway,
    val lostFoundQuery: LostFoundQueryHandler,
) {
    @PostMapping
    fun publisher(@RequestBody req: PublishMissingItemReq): Rsp<LostFoundID> {
        val publisherId = LoginUser.current.uid
        return cmdGateway.sendAndWait<LostFoundID>(req.toCmd(publisherId)).toRsp()
    }

    @PutMapping("/{lid}/claim")
    fun claim(@PathVariable lid: LostFoundID, @RequestBody answers: List<String>): Rsp<Boolean> {
        val ownerId = LoginUser.current.uid
        cmdGateway.sendAndWait<Unit>(
            ClaimCmd(
                lid,
                ownerId,
                answers
            )
        )
        return Rsp.success(true)
    }

    @GetMapping("/search")
    fun search(param: LostFoundSearchParam): Rsp<Page<LostFoundOverview>> {
        return lostFoundQuery.search(param).toRsp()
    }

    data class PublishMissingItemReq(
        val name: String,
        val imgs: List<URN>,
        val location: String,
//        val pickupTime: Instant,
        val questions: List<Question>,
    ) {

        fun toCmd(publisherId: UID): PublishMissingItemCmd =
            PublishMissingItemCmd(
                publisherId,
                MissingItem(
                    name, imgs
                ),
                location,
//                pickupTime,
                questions
            )
    }
}