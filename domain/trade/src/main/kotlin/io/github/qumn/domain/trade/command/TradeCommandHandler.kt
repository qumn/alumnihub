package io.github.qumn.domain.trade.command

import io.github.qumn.domain.comment.api.model.CommentFactory
import io.github.qumn.domain.comment.api.model.CommentId
import io.github.qumn.domain.comment.api.model.Comments
import io.github.qumn.domain.comment.api.model.SubjectType
import io.github.qumn.domain.system.api.user.model.Users
import io.github.qumn.domain.trade.model.*
import io.github.qumn.framework.exception.BizNotAllowedException
import org.axonframework.commandhandling.CommandHandler
import org.springframework.stereotype.Component

@Component
class TradeCommandHandler(
    val trades: Trades,
    val users: Users,
    val comments: Comments,
) {
    /**
     * @return the id of new trade
     */
    @CommandHandler
    fun handle(pubIdleCmd: PublishIdleGoodsCmd): Long {
        val pendingTrade = TradeFactory.create(pubIdleCmd.sellerId, pubIdleCmd.desc, pubIdleCmd.price, pubIdleCmd.imgs)
        trades.save(pendingTrade)
        return pendingTrade.id
    }

    @CommandHandler
    fun handle(desireTradeCmd: DesireTradeCmd) {
        when (val trade = trades.findById(desireTradeCmd.tradeId)) {
            is PendingTrade -> {
                val user = users.findById(desireTradeCmd.desiredBy)
                trade.desiredBy(user.uid)
                trades.save(trade)
            }

            else -> {
                throw BizNotAllowedException("当前商品不再待定状态")
            }
        }
    }

    @CommandHandler
    fun handle(reserveTradeCmd: ReserveTradeCmd) {
        when (val trade = trades.findById(reserveTradeCmd.tradeId)) {
            is PendingTrade -> {
                val user = users.findById(reserveTradeCmd.buyerId)
                trade.reserve(user)
                trades.save(trade)
            }

            else -> {
                throw BizNotAllowedException("当前商品不再待定状态")
            }
        }
    }

    @CommandHandler
    fun handle(completeTradeCmd: CompleteTradeCmd) {
        when (val trade = trades.findById(completeTradeCmd.tradeId)) {
            is ReservedTrade -> {
                if (trade.buyerId != completeTradeCmd.operationUID)
                    throw BizNotAllowedException("您不是此商品的卖家无法完成此交易")
                trade.complete()
                trades.save(trade)
            }

            is PendingTrade -> {
                throw BizNotAllowedException("请先预定商品")
            }

            is CompletedTrade -> {
                throw BizNotAllowedException("交易已完成")
            }

            else -> {
                throw BizNotAllowedException("未知错误")
            }
        }

    }

    /**
     * @return the id of new comment
     */
    @CommandHandler
    fun handle(createCommentCmd: CreateCommentCmd): CommentId {
        val newComment = CommentFactory.create(
            createCommentCmd.commenterId,
            SubjectType.Trade,
            createCommentCmd.tradeId,
            createCommentCmd.content
        )
        comments.save(newComment)
        return newComment.id
    }
}