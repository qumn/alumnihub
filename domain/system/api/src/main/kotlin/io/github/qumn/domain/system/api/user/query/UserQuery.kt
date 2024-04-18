package io.github.qumn.domain.system.api.user.query

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.exception.BizNotAllowedException

interface UserQuery {
    fun queryById(id: UID): UserDetails {
        return tryQueryById(id) ?: throw BizNotAllowedException("找不到此用户")
    }

    fun tryQueryById(id: UID): UserDetails?

}