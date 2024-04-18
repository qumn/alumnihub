package io.github.qumn.domain.system.user.infrastructure

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.domain.system.api.user.model.User
import io.github.qumn.domain.system.api.user.model.Users
import io.github.qumn.domain.system.api.user.query.UserDetails
import io.github.qumn.domain.system.api.user.query.UserQuery
import io.github.qumn.framework.security.Authentication
import io.github.qumn.framework.security.LoginUser
import io.github.qumn.ktorm.ext.exist
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.*
import org.springframework.stereotype.Component

@Component
class UserRepository(
    val userMapper: UserMapper,
    val database: Database,
) : Users, UserQuery, Authentication {

    override fun findByIds(uids: Collection<UID>): List<User> {
        return database.users.filter { it.uid inList uids }.map {
            userMapper.toUser(it)
        }
    }

    override fun contains(uid: UID): Boolean {
        return database.users.exist { it.uid eq uid }
    }

    override fun tryFindById(uid: UID): User? {
        return database.users.find { it.uid eq uid }?.let {
            userMapper.toUser(it)
        }
    }

    override fun findByName(name: String): User? {
        return database.users.find { it.name eq name }?.let {
            userMapper.toUser(it)
        }
    }

    override fun save(user: User) {
        val entity = userMapper.toEntity(user)
        if (database.users.any { it.uid eq user.uid }) {
            database.users.update(entity)
        } else {
            database.users.add(entity)
        }
    }

    override fun login(username: String, match: (encrypted: String) -> Boolean): LoginUser {
        val user =
            database.users.find { UserTable.name eq username } ?: throw IllegalArgumentException("user not found")
        if (!match(user.password)) {
            throw IllegalArgumentException("password is not correct")
        }
        return LoginUser(user.uid, user.name)
    }

    // query method
    override fun tryQueryById(id: UID): UserDetails? {
        val entity = database.users.find { it.uid eq id } ?: return null
        return userMapper.toDetails(entity)
    }

}