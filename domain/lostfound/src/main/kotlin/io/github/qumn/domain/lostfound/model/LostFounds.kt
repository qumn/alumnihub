package io.github.qumn.domain.lostfound.model

import io.github.qumn.framework.exception.BizNotAllowedException

interface LostFounds {
    fun findBy(lostFoundID: LostFoundID): LostFound {
        return tryFindBy(lostFoundID) ?: throw BizNotAllowedException("物品找不到了")
    }

    fun tryFindBy(lostFoundID: LostFoundID): LostFound?

    fun save(lostFound: LostFound)

    fun contains(lostFoundID: LostFoundID): Boolean

}