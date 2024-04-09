package io.github.qumn.domain.system.api.user.model

import io.github.qumn.framework.exception.BizNotAllowedException

interface Users {
    fun findById(uid: Long): User {
        return tryFindById(uid) ?: throw BizNotAllowedException("用户不存在")
    }

    fun tryFindById(uid: Long) : User?

    fun findByIds(uids: Collection<Long>): List<User>

    fun findByName(name: String): User?

    fun save(user: User)
}