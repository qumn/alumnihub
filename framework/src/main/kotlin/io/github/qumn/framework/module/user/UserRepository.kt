package io.github.qumn.framework.module.user

import io.github.qumn.framework.domain.user.model.Gender
import io.github.qumn.ktorm.base.BaseEntity
import io.github.qumn.ktorm.base.BaseTable
import io.github.qumn.ktorm.base.database
import org.ktorm.database.Database
import org.ktorm.dsl.like
import org.ktorm.entity.*
import org.ktorm.schema.enum
import org.ktorm.schema.timestamp
import org.ktorm.schema.varchar
import java.time.Instant


//interface User : BaseEntity<User> {
//    companion object: Entity.Factory<User>()
//
//    var name: String
//    var gender: Gender
//    var birthDay: Instant?
//    var phone: String
//    var email: String
//
//    val friends
//        get() =
//            database.users.filter { it.name like "zs" }.toList()
//}
//
//object Users : BaseTable<User>("sys_user") {
//    val name = varchar("name").bindTo { it.name }
//    val gender = enum<Gender>("gender").bindTo { it.gender }
//    val birthDay = timestamp("birth_day").bindTo { it.birthDay }
//    val phone = varchar("phone").bindTo { it.phone }
//    val email = varchar("email").bindTo { it.email }
//}
//
//// TODO permission filter
//val Database.users
//    get() = this.sequenceOf(Users)
