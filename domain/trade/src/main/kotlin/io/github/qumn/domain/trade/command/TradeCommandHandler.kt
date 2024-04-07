package io.github.qumn.domain.trade.command

import io.github.qumn.domain.trade.model.Trades
import org.axonframework.commandhandling.CommandHandler
import org.springframework.stereotype.Component

@Component
class TradeCommandHandler(
    val trades: Trades,
) {
    @CommandHandler
    fun handle(publishIdleGoodsCommand: PublishIdleGoodsCommand) {

    }

    @CommandHandler
    fun handle(createCommentCommand: CreateCommentCommand) {

    }
}