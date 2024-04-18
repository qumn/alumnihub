package io.github.qumn.domain.trade.web

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.comment.api.model.Comments
import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.domain.trade.command.*
import io.github.qumn.domain.trade.ext.findByTradeId
import io.github.qumn.domain.trade.query.TradeDetails
import io.github.qumn.domain.trade.query.TradeQueryHandler
import io.github.qumn.framework.security.LoginUser
import io.github.qumn.framework.storage.model.URN
import io.github.qumn.framework.web.common.Rsp
import io.github.qumn.framework.web.common.toRsp
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/trades")
class TradeController(
    val commandGateway: CommandGateway,
    val queryHandler: TradeQueryHandler,
    val comments: Comments,
) {
    // command
    @PostMapping
    fun publishIdleGoods(@RequestBody idleGoods: IdleGoods): Rsp<Long> {
        val sellerId = LoginUser.current.uid
        return commandGateway.sendAndWait<Long>(idleGoods.toCommand(sellerId)).toRsp()
    }

    @PutMapping("/{tid}/desired")
    fun desire(@PathVariable("tid") tradeId: Long) {
        val desiredBy = LoginUser.current.uid
        return commandGateway.sendAndWait(DesireTradeCmd(tradeId, desiredBy))
    }

    @PutMapping("{tid}/reserved")
    fun reserve(@PathVariable("tid") tradeId: Long) {
        val buyerId = LoginUser.current.uid
        return commandGateway.sendAndWait(ReserveTradeCmd(tradeId, buyerId))
    }

    @PutMapping("/{tid}/completed")
    fun complete(@PathVariable("tid") tradeId: Long) {
        val operationUserId = LoginUser.current.uid
        return commandGateway.sendAndWait(CompleteTradeCmd(tradeId, operationUserId));
    }

    @PostMapping("/{tid}/comments")
    fun commentTrade(@PathVariable("tid") tradeId: Long, @RequestBody newComment: NewComment): Rsp<Long> {
        val commenterId = LoginUser.current.uid
        return commandGateway.sendAndWait<Long>(newComment.toCommand(commenterId, tradeId)).toRsp()
    }

    // query
    @GetMapping("{tid}")
    fun detail(@PathVariable("tid") tradeId: Long): Rsp<TradeDetails> {
        return queryHandler.queryTradeDetails(tradeId).toRsp()
    }

    @GetMapping("/{tid}/comments")
    fun comment(@PathVariable("tid") tradeId: Long): Rsp<List<Comment>> {
        return comments.findByTradeId(tradeId).toRsp()
    }

    data class NewComment(
        val content: String,
    ) {
        fun toCommand(commenterId: UID, tradeId: Long): CreateCommentCmd {
            return CreateCommentCmd(commenterId, tradeId, content, Instant.now())
        }
    }

    data class IdleGoods(
        val desc: String,
        val price: Int,
        val imgs: List<URN> = listOf(),
    ) {
        fun toCommand(sellerId: UID): PublishIdleGoodsCmd {
            return PublishIdleGoodsCmd(sellerId, desc, imgs, price)
        }
    }

}