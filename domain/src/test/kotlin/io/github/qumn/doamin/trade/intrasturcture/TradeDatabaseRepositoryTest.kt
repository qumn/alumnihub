package io.github.qumn.doamin.trade.intrasturcture

import com.ninjasquad.springmockk.MockkBean
import io.github.qumn.domain.trade.infrastructure.TradeDatabaseRepository
import io.github.qumn.domain.trade.infrastructure.TradeDomainModelMapper
import io.github.qumn.domain.trade.model.Goods
import io.github.qumn.domain.trade.model.PendingTrade
import io.github.qumn.domain.trade.model.TradeFactory
import io.github.qumn.framework.domain.user.model.*
import io.github.qumn.framework.test.DbTestAutoConfiguration
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string
import io.mockk.every
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(classes = [TradeDatabaseRepository::class, TradeDomainModelMapper::class, DbTestAutoConfiguration::class])
// 自己的 DB 配置类
class TradeDatabaseRepositoryTest(
    private val repository: TradeDatabaseRepository,
    @MockkBean val users: Users,
) : StringSpec({
    "save pending trade should work" {
        // given
        val pendingTrade = anyPendingTrade()
        val seller = pendingTrade.seller
        every { users.findById(seller.uid) } returns seller
        every { users.findByIds(emptyList()) } returns listOf() // be used in desired buyers

        repository.save(pendingTrade)
        repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
    }

    "desired buyers should be save" {
        // given
        val pendingTrade = anyPendingTrade()
        val seller = pendingTrade.seller
        val desiredBuyers = listOf(anyUser(), anyUser())

        for (buyer in desiredBuyers) {
            pendingTrade.desiredBy(buyer)
        }

        every { users.findById(seller.uid) } returns seller
        every { users.findByIds(desiredBuyers.map { it.uid }) } returns desiredBuyers

        repository.save(pendingTrade)
        repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
    }

}) {
    override fun extensions() = listOf(SpringExtension)
}


fun anyPendingTrade(): PendingTrade {
    val goods = anyGoods()
    return TradeFactory.create(
        anyUser(),
        goodsDesc = goods.desc,
        goodsPrice = goods.price.toInt(),
        listOf()
    )
}

fun anyUser(): User {
    return User(
        uid = Arb.long(min = 1).next(),
        name = Arb.string(2, 10).next(),
        email = Email("email@123.com"),
        phone = Phone("12345678901"),
        birthDay = null,
        gender = Gender.UNKNOWN
    )
}

fun anyGoods(): Goods {
    return Goods(
        id = Arb.long(min = 1).next(),
        desc = Arb.string(10, 100).next(),
        imgs = listOf(),
        price = Arb.int(min = 1).next().toBigDecimal()
    )
}