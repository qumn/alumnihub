package io.github.qumn.domain.system.user.command

import io.github.qumn.domain.system.api.user.model.Users
import io.github.qumn.domain.system.user.UserFactory
import io.github.qumn.framework.exception.BizNotAllowedError
import io.github.qumn.framework.exception.bizRequire
import org.axonframework.commandhandling.CommandHandler
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserCommandHandler(
    val users: Users,
    val passwordEncoder: PasswordEncoder,
) {
    @CommandHandler
    fun handle(command: RegisteredUserCommand) {
        val user = users.findByName(command.username)
        bizRequire(user == null) {
            BizNotAllowedError("用户名已存在")
        }

        var registeredUser = UserFactory.from(command)
        registeredUser = registeredUser.copy(
            password = passwordEncoder.encode(registeredUser.password)
        )
        users.save(registeredUser)
    }

    @CommandHandler
    fun handle(command: LoginCommand) {
        val user = users.findByName(command.username)
        bizRequire(user != null) {
            BizNotAllowedError("用户名不存在")
        }
        bizRequire(passwordEncoder.matches(command.password, user!!.password)) {
            BizNotAllowedError("密码错误")
        }
    }
}
