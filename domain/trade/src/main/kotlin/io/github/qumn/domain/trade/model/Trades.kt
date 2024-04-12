package io.github.qumn.domain.trade.model

import io.github.qumn.framework.exception.BizNotAllowedException

interface Trades {

    fun findById(id: Long) : Trade {
        return tryFindById(id) ?: throw BizNotAllowedException("Trade not found")
    }

    fun tryFindById(id: Long): Trade?

    fun findPendingTrade(id: Long): PendingTrade?

    fun findReservedTrade(id: Long): ReservedTrade?

    fun findCompletedTrade(id: Long): CompletedTrade?

    fun save(trade: Trade)

}