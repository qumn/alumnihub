package io.github.qumn.framework.security

/**
 * authorization is an interface that represents the authorization of the system.
 * implementation by system domain.
 */
interface Authentication {

    fun login(username: String, encryptedPassword: String): LoginUser

}