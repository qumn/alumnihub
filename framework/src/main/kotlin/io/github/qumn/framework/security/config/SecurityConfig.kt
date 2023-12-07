package io.github.qumn.framework.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		http {
			authorizeHttpRequests {
				authorize(anyRequest, authenticated)
			}
			formLogin { }
			httpBasic { }
		}

		return http.build()
	}

	@Bean
	fun userDetailsService(): UserDetailsService {
		val user = User.withDefaultPasswordEncoder()
			.username("qumn")
			.password("qumn")
			.roles("USER", "ADMIN")
			.build()

		return InMemoryUserDetailsManager(user)
	}

}