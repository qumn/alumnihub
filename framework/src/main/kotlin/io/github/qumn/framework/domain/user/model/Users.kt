package io.github.qumn.framework.domain.user.model

interface Users {
    fun findById(uid: Long): User?

    fun findByIds(uids: Collection<Long>): List<User>

    fun findByName(name: String): User?
}