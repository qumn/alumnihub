package io.github.qumn.domain.system.user.infrastructure

import io.github.qumn.domain.system.api.user.model.Gender
import io.github.qumn.ktorm.base.BaseEntity
import io.github.qumn.ktorm.base.BaseTable
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.enum
import org.ktorm.schema.long
import org.ktorm.schema.timestamp
import org.ktorm.schema.varchar
import java.time.Instant

interface UserEntity : BaseEntity<UserEntity> {

    companion object : Entity.Factory<UserEntity>()

    var uid: Long
    var name: String
    var password: String
    var gender: Gender
    var birthDay: Instant?
    var phone: String
    var email: String
}

object UserTable : BaseTable<UserEntity>("sys_user") {
    val uid = long("uid").bindTo { it.uid }
    val name = varchar("name").bindTo { it.name }
    val password = varchar("password").bindTo { it.password }
    val gender = enum<Gender>("gender").bindTo { it.gender }
    val birthDay = timestamp("birth_day").bindTo { it.birthDay }
    val phone = varchar("phone").bindTo { it.phone }
    val email = varchar("email").bindTo { it.email }
}

val Database.users
    get() = this.sequenceOf(UserTable)
