package io.github.qumn.doamin.trade.intrasturcture

import com.ninjasquad.springmockk.MockkBean
import io.github.qumn.domain.trade.infrastructure.TradeDatabaseRepository
import io.github.qumn.domain.trade.infrastructure.TradeDomainModelMapper
import io.github.qumn.domain.trade.model.Goods
import io.github.qumn.domain.trade.model.Img
import io.github.qumn.domain.trade.model.PendingTrade
import io.github.qumn.domain.trade.model.TradeFactory
import io.github.qumn.framework.domain.user.model.*
import io.github.qumn.framework.test.DbTestAutoConfiguration
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.property.*
import io.kotest.property.arbitrary.*
import io.mockk.every
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random


@SpringBootTest(classes = [TradeDatabaseRepository::class, TradeDomainModelMapper::class, DbTestAutoConfiguration::class])
// 自己的 DB 配置类
class TradeDatabaseRepositoryTest(
    private val repository: TradeDatabaseRepository,
    val tradeDomainModelMapper: TradeDomainModelMapper,
    @MockkBean val users: Users,
) : StringSpec({
    "save pending trade should work" {
        checkAll(100, config = PropTestConfig(seed = 9173113484041523325) , Arb.pendingTrade()) { pendingTrade ->
            // given
            val seller = pendingTrade.seller
            val desiredBuyers = pendingTrade.desiredBuyers
            every { users.findById(seller.uid) } returns seller
            every {
                users.findByIds(
                    desiredBuyers.map(User::uid).toList()
                )
            } returns desiredBuyers // be used in desired buyers

            repository.save(pendingTrade)
            repository.findPendingTrade(pendingTrade.id) shouldBe pendingTrade
        }
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
    "arb should work" {
        var cnt = 0;
        forAll<String, String>(100) { a, b ->
            println("$a $b")
            cnt += 1
            true
        }
        println(cnt)
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
    Arb.email()
    return User(
        uid = Arb.long(min = 1).next(),
        name = Arb.string(2, 10).next(),
        email = Email("email@123.com"),
        phone = Phone("12345678901"),
        birthDay = null,
        gender = Gender.UNKNOWN
    )
}

fun Arb.Companion.pendingTrade() = Arb.bind(
    Arb.long(min = 1),
    Arb.user(),
    Arb.array(Arb.user()),
    Arb.goods()
) { tradeId, seller, tradeDesiredBuyers, tradeGoods ->
    PendingTrade(id = tradeId, seller = seller, desiredBuyers = tradeDesiredBuyers.toMutableList(), goods = tradeGoods)
}

fun Arb.Companion.goods() = Arb.bind(
    Arb.string(10, 100),
    Arb.list(Arb.string(10, 100), 1..10).map { list -> list.map(::Img) },
    Arb.int(min = 1).map { it.toBigDecimal() }
) { desc, imgs, price ->
    Goods(desc = desc, imgs = imgs, price = price)
}

fun Arb.Companion.user() = Arb.bind(
    Arb.long(min = 1),
    Arb.string(2, 10, codepoints = Codepoint.az()),
    Arb.email(),
    Arb.phone(),
    Arb.instant(),
    Arb.enum<Gender>()
) { uid, name, email, phone, birthDay, gender ->
    User(uid = uid, name = name, email = Email(email), phone = Phone(phone), birthDay = birthDay, gender = gender)
}

fun anyGoods(): Goods {
    return Goods(
        desc = Arb.string(10, 100).next(),
        imgs = listOf(),
        price = Arb.int(min = 1).next().toBigDecimal()
    )
}

fun Arb.Companion.phone() = Arb.string(
    size = 11,
    codepoints = Arb.of(('0'.code..'9'.code).map(::Codepoint))
)