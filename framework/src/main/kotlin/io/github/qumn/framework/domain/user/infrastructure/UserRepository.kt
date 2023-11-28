package io.github.qumn.framework.domain.user.infrastructure

import io.github.qumn.framework.domain.user.model.User
import io.github.qumn.framework.domain.user.model.Users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.springframework.stereotype.Component

@Component
class UserRepository(
    val userDomainModelMapper: UserDomainModelMapper,
    val database: Database,
) : Users {

    override fun findByIds(uids: List<Long>): List<User> {
        return database.users.filter { it.uid inList uids }.map {
            userDomainModelMapper.toUser(it)
        }
    }

    override fun findById(uid: Long): User? {
        return database.users.find { it.uid eq uid }?.let {
            userDomainModelMapper.toUser(it)
        }
    }

    override fun findByName(name: String): User? {
        return database.users.find { it.name eq name }?.let {
            userDomainModelMapper.toUser(it)
        }
    }


}