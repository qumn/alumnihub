package io.github.qumn.domain.system.user.command

import org.axonframework.commandhandling.CommandHandler
import org.springframework.stereotype.Component

@Component
class UserCommandHandler {
    @CommandHandler
    fun handle(command: RegisteredUserCommand) {
        println("handle command: $command")
    }
}