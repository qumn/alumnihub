package io.github.qumn.doamin.trade.infrasturcture

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.MockkClear
import io.github.qumn.doamin.trade.arb.pendingTrade
import io.github.qumn.domain.trade.infrastructure.TradeDatabaseRepository
import io.github.qumn.domain.trade.infrastructure.TradeDomainModelMapper
import io.github.qumn.domain.trade.model.CompletedTrade
import io.github.qumn.domain.trade.model.PendingTrade
import io.github.qumn.domain.trade.model.ReservedTrade
import io.github.qumn.domain.trade.model.Trade
import io.github.qumn.framework.domain.user.model.User
import io.github.qumn.framework.domain.user.model.Users
import io.github.qumn.framework.test.user
import io.github.qumn.test.DBIntegrationSpec
import io.kotest.common.ExperimentalKotest
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.array
import io.kotest.property.arbitrary.map
import io.kotest.property.checkAll
import io.mockk.every
import org.springframework.context.annotation.Import

@Import(value = [TradeDatabaseRepository::class, TradeDomainModelMapper::class])
// 自己的 DB 配置类
class TradeDatabaseRepositoryTest(
    private val repository: TradeDatabaseRepository,
    //! the default clear behavior is to clear all mocks after each test,
    // that's lead to test failed when working on parallel mode
    @MockkBean(clear = MockkClear.NONE) val users: Users,
) : DBIntegrationSpec({
    "save pending trade should work" {
        checkAll(100, Arb.pendingTrade()) { pendingTrade ->
            // given
            repository.save(pendingTrade)
            users.mockFor(pendingTrade)
            repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
        }
    }

    "save reserved trade should work" {
        checkAll(100, Arb.pendingTrade(), Arb.user()) { pendingTrade, buyer ->
            // save the pending trade
            repository.save(pendingTrade)

            val reservedTrade = pendingTrade.reserve(buyer)
            repository.save(reservedTrade)
            users.mockFor(reservedTrade)

            repository.findReservedTrade(reservedTrade.id)?.reservedAt shouldBe reservedTrade.reservedAt
        }
    }

    "desired buyers should be save" {
        checkAll(
            100,
            Arb.pendingTrade().map { it.desiredBuyers.clear(); it },
            Arb.array(Arb.user())
        ) { pendingTrade, desiredBuyers ->
            // clear the desiredBuyers be filled arb
            for (buyer in desiredBuyers) {
                pendingTrade.desiredBy(buyer)
            }
            repository.save(pendingTrade)
            users.mockFor(pendingTrade)
            repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
        }
    }
}) {
    @ExperimentalKotest
    override fun dispatcherAffinity() = false
}

fun Users.mockFor(trade: Trade) {
    val users = this
    val seller = trade.seller
    every { users.findById(seller.uid) } returns seller

    when (trade) {
        is PendingTrade -> {
            val desiredBuyers = trade.desiredBuyers
            every {
                users.findByIds(
                    desiredBuyers.map(User::uid).toSet()
                )
            } returns desiredBuyers // be used in desired buyers
        }

        is ReservedTrade -> {
            every { users.findById(trade.buyer.uid) } returns trade.buyer
        }

        is CompletedTrade -> {
            every { users.findById(trade.buyer.uid) } returns trade.buyer
        }
    }
}