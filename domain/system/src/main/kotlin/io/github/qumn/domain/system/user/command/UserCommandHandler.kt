package io.github.qumn.domain.system.user.command

import io.github.qumn.domain.system.api.user.model.Users
import io.github.qumn.domain.system.user.UserFactory
import org.axonframework.commandhandling.CommandHandler
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class UserCommandHandler(
    val users: Users,
    val passwordEncoder: PasswordEncoder,
) {
    @CommandHandler
    fun handle(command: RegisteredUserCommand) {
        val user = users.findByName(command.username)
        if (user != null) {
            throw IllegalArgumentException("${user.name} has been existed")
        }

        var registeredUser = UserFactory.from(command)
        registeredUser = registeredUser.copy(
            password = passwordEncoder.encode(registeredUser.password)
        )
        users.save(registeredUser)
    }
}