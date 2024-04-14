package io.github.qumn.domain.system.api.user.query

import io.github.qumn.framework.exception.BizNotAllowedException

interface UserQuery {
    fun queryById(id: Long) : UserDetails {
        return tryQueryById(id) ?: throw BizNotAllowedException("找不到此用户")
    }
    fun tryQueryById(id: Long) : UserDetails?

}