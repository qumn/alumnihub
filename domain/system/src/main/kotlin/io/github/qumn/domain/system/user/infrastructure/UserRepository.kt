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
    val database: Database,
) : Users, UserQuery, Authentication {

    override fun findByIds(uids: Collection<UID>): List<User> {
        return database.users.filter { it.uid inList uids }.map(UserEntity::toDomain)
    }

    override fun contains(uid: UID): Boolean {
        return database.users.exist { it.uid eq uid }
    }

    override fun tryFindById(uid: UID): User? {
        return database.users.find { it.uid eq uid }?.toDomain()
    }

    override fun findByName(name: String): User? {
        return database.users.find { it.name eq name }?.toDomain()
    }

    override fun save(user: User) {
        val entity = user.toEntity()
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

    override fun queryBy(ids: Collection<UID>): List<UserDetails> {
        return database.users.filter { it.uid inList ids }.map { it.toDetails() }
    }

    // query method
    override fun tryQueryById(id: UID): UserDetails? {
        return database.users.find { it.uid eq id }?.toDetails()
    }
}