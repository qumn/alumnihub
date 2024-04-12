package io.github.qumn.domain.trade.ext

import io.github.qumn.domain.comment.api.model.Comment
import io.github.qumn.domain.comment.api.model.Comments
import io.github.qumn.domain.comment.api.model.SubjectType
import io.github.qumn.domain.trade.model.Trade
import io.github.qumn.domain.trade.model.Trades

fun Comment.trade(trades: Trades): Trade {
    return trades.findById(this.subjectId)
}

fun Comments.findByTradeId(id: Long) : List<Comment> {
    return this.findBySubjectId(SubjectType.Trade, id)
}