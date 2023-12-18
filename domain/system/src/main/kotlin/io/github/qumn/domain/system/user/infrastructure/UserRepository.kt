package io.github.qumn.domain.system.user.infrastructure

import io.github.qumn.domain.system.api.user.model.User
import io.github.qumn.domain.system.api.user.model.Users
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

    override fun findByIds(uids: Collection<Long>): List<User> {
        return database.users.filter { UserTable.uid inList uids }.map {
            userDomainModelMapper.toUser(it)
        }
    }

    override fun findById(uid: Long): User? {
        return database.users.find { UserTable.uid eq uid }?.let {
            userDomainModelMapper.toUser(it)
        }
    }

    override fun findByName(name: String): User? {
        return database.users.find { UserTable.name eq name }?.let {
            userDomainModelMapper.toUser(it)
        }
    }


}