package io.github.qumn.domain.system.api.user.model

import io.github.qumn.framework.exception.BizNotAllowedException

interface Users {
    fun findById(uid: UID): User {
        return tryFindById(uid) ?: throw BizNotAllowedException("用户不存在")
    }

    fun contains(uid: UID) :Boolean

    fun tryFindById(uid: UID) : User?

    fun findByIds(uids: Collection<UID>): List<User>

    fun findByName(name: String): User?

    fun save(user: User)
}