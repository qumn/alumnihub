package io.github.qumn.doamin.trade.intrasturcture

import com.ninjasquad.springmockk.MockkBean
import io.github.qumn.doamin.trade.model.pendingTrade
import io.github.qumn.domain.trade.infrastructure.TradeDatabaseRepository
import io.github.qumn.domain.trade.infrastructure.TradeDomainModelMapper
import io.github.qumn.framework.domain.user.model.User
import io.github.qumn.framework.domain.user.model.Users
import io.github.qumn.framework.test.user
import io.github.qumn.test.config.DbTestAutoConfiguration
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.array
import io.kotest.property.arbitrary.map
import io.kotest.property.checkAll
import io.mockk.every
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(classes = [TradeDatabaseRepository::class, TradeDomainModelMapper::class, DbTestAutoConfiguration::class])
// 自己的 DB 配置类
class TradeDatabaseRepositoryTest(
    private val repository: TradeDatabaseRepository,
    @MockkBean val users: Users,
) : StringSpec({
    "save pending trade should work" {
        checkAll(100, Arb.pendingTrade()) { pendingTrade ->
            // given
            val seller = pendingTrade.seller
            val desiredBuyers = pendingTrade.desiredBuyers
            every { users.findById(seller.uid) } returns seller
            every {
                users.findByIds(
                    desiredBuyers.map(User::uid).toSet()
                )
            } returns desiredBuyers // be used in desired buyers

            repository.save(pendingTrade)
            repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
        }
    }

    "desired buyers should be save" {
        checkAll(
            100,
            Arb.pendingTrade().map { it.desiredBuyers.clear(); it },
            Arb.array(Arb.user())
        ) { pendingTrade, desiredBuyers ->
            // given
            val seller = pendingTrade.seller

            // clear the desiredBuyers be filled arb
            for (buyer in desiredBuyers) {
                pendingTrade.desiredBy(buyer)
            }

            every { users.findById(seller.uid) } returns seller
            every {
                users.findByIds(match { it.toSet() == desiredBuyers.map(User::uid).toSet() }) // only care about the uid
            } returns desiredBuyers.toList()

            repository.save(pendingTrade)
            repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
        }
    }
}) {
    override fun extensions() = listOf(SpringExtension)
}
