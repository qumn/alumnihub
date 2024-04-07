package io.github.qumn.domain.trade.web

import io.github.qumn.domain.trade.command.CreateCommentCommand
import io.github.qumn.domain.trade.command.PublishIdleGoodsCommand
import io.github.qumn.domain.trade.model.Img
import io.github.qumn.framework.security.LoginUser
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.Instant

@RestController
@RequestMapping("/trades")
class TradeController(
    val commandGateway: CommandGateway,
) {
    @PostMapping
    fun publishIdleGoods(idleGoods: IdleGoods) {
        val sellerId = LoginUser.current.uid
        commandGateway.send<Void>(idleGoods.toCommand(sellerId))
    }

    @PostMapping("/{tid}/comments")
    fun commentTrade(@PathVariable("tid") tradeId: Long, @RequestBody newComment: NewComment) {
        val commenterId = LoginUser.current.uid
        commandGateway.send<Void>(newComment.toCommand(commenterId, tradeId))
    }

    data class NewComment(
        val content: String,
    ) {
        fun toCommand(commenterId: Long, tradeId: Long): CreateCommentCommand {
            return CreateCommentCommand(commenterId, tradeId, content, Instant.now())
        }
    }

    data class IdleGoods(
        val desc: String,
        val imgs: List<Img>,
        val price: BigDecimal,
    ) {
        fun toCommand(sellerId: Long): PublishIdleGoodsCommand {
            return PublishIdleGoodsCommand(sellerId, desc, imgs, price)
        }
    }

}