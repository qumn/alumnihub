package io.github.qumn.domain.trade.web

import io.github.qumn.domain.trade.command.CreateCommentCmd
import io.github.qumn.domain.trade.command.DesireTradeCmd
import io.github.qumn.domain.trade.command.PublishIdleGoodsCmd
import io.github.qumn.domain.trade.command.ReserveTradeCmd
import io.github.qumn.framework.security.LoginUser
import io.github.qumn.framework.web.common.Rsp
import io.github.qumn.framework.web.common.toRsp
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/trades")
class TradeController(
    val commandGateway: CommandGateway,
) {
    @PostMapping
    fun publishIdleGoods(idleGoods: IdleGoods): Rsp<Long> {
        val sellerId = LoginUser.current.uid
        return commandGateway.sendAndWait<Long>(idleGoods.toCommand(sellerId)).toRsp()
    }

    @PutMapping("/desire/{tid}")
    fun desire(@PathVariable("tid") tradeId: Long) {
        val desiredBy = LoginUser.current.uid
        return commandGateway.sendAndWait(DesireTradeCmd(tradeId, desiredBy))
    }

    @PutMapping("/reserve/{tid}")
    fun reserve(@PathVariable("tid") tradeId: Long) {
        val buyerId = LoginUser.current.uid
        return commandGateway.sendAndWait(ReserveTradeCmd(tradeId, buyerId))
    }

    @PutMapping("/complete/{tid}")
    fun complete(@PathVariable("tid") tradeId: Long) {
        return commandGateway.sendAndWait(tradeId);
    }

    @PostMapping("/{tid}/comments")
    fun commentTrade(@PathVariable("tid") tradeId: Long, @RequestBody newComment: NewComment): Rsp<Long> {
        val commenterId = LoginUser.current.uid
        return commandGateway.sendAndWait<Long>(newComment.toCommand(commenterId, tradeId)).toRsp()
    }

    data class NewComment(
        val content: String,
    ) {
        fun toCommand(commenterId: Long, tradeId: Long): CreateCommentCmd {
            return CreateCommentCmd(commenterId, tradeId, content, Instant.now())
        }
    }

    data class IdleGoods(
        val desc: String,
        val imgs: List<String>,
        val price: Int,
    ) {
        fun toCommand(sellerId: Long): PublishIdleGoodsCmd {
            return PublishIdleGoodsCmd(sellerId, desc, imgs, price)
        }
    }

}