package io.github.qumn.doamin.trade.infrasturcture

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.MockkClear
import io.github.qumn.doamin.trade.arb.pendingTrade
import io.github.qumn.domain.system.api.user.model.Users
import io.github.qumn.domain.system.api.user.test.user
import io.github.qumn.domain.trade.infrastructure.TradeDatabaseRepository
import io.github.qumn.test.DBIntegrationSpec
import io.kotest.common.ExperimentalKotest
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.array
import io.kotest.property.arbitrary.map
import io.kotest.property.checkAll
import org.springframework.context.annotation.Import

@Import(value = [TradeDatabaseRepository::class])
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

            repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
        }
    }

    "save reserved trade should work" {
        checkAll(100, Arb.pendingTrade(), Arb.user()) { pendingTrade, buyer ->
            // save the pending trade
            repository.save(pendingTrade)

            val reservedTrade = pendingTrade.reserve(buyer)
            repository.save(reservedTrade)

            repository.findReservedTrade(reservedTrade.id) shouldBe reservedTrade
        }
    }

    "save completed trade should work" {
        checkAll(100, Arb.pendingTrade(), Arb.user()) { pendingTrade, buyer ->
            repository.save(pendingTrade)
            val reservedTrade = pendingTrade.reserve(buyer)
            repository.save(reservedTrade)
            val completedTrade = reservedTrade.complete()
            repository.save(completedTrade)

            repository.findCompletedTrade(completedTrade.id) shouldBe completedTrade
        }
    }

    "desired buyers should be save" {
        checkAll(
            100,
            Arb.pendingTrade().map { it.desiredBuyerIds.clear(); it },
            Arb.array(Arb.user())
        ) { pendingTrade, desiredBuyers ->
            // clear the desiredBuyers be filled arb
            for (buyer in desiredBuyers) {
                pendingTrade.desiredBy(buyer)
            }
            repository.save(pendingTrade)

            repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
        }
    }
}) {
    @ExperimentalKotest
    override fun dispatcherAffinity() = false
}