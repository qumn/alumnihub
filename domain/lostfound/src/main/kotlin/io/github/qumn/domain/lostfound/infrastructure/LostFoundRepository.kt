package io.github.qumn.domain.lostfound.infrastructure

import io.github.qumn.domain.lostfound.model.LostFound
import io.github.qumn.domain.lostfound.model.LostFoundID
import io.github.qumn.domain.lostfound.model.LostFounds
import io.github.qumn.ktorm.ext.exist
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.update
import org.springframework.stereotype.Repository

@Repository
class LostFoundRepository(
    val db: Database,
) : LostFounds {
    override fun tryFindBy(lostFoundID: LostFoundID): LostFound? {
        return tryFindEntity(lostFoundID)?.toDomain()
    }

    override fun save(lostFound: LostFound) {
        if (contains(lostFound.id)) {
            update(lostFound.toEntity())
        } else {
            insertNew(lostFound.toEntity())
        }
    }

    override fun contains(lostFoundID: LostFoundID): Boolean {
        return db.lostFounds.exist {
            it.id eq lostFoundID
        }
    }

    private fun insertNew(lostFoundEntity: LostFoundEntity) {
        db.lostFounds.add(lostFoundEntity)
    }

    private fun update(lostFoundEntity: LostFoundEntity) {
        db.lostFounds.update(lostFoundEntity)
    }

    private fun tryFindEntity(lostFoundID: LostFoundID): LostFoundEntity? {
        return db.lostFounds.find { it.id eq lostFoundID }
    }
}