package io.github.qumn.framework.security.filter

import io.github.qumn.framework.security.LoginUser
import io.github.qumn.framework.security.config.SecurityProperties
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter


class JwtTokenFilter(
    private val securityProperties: SecurityProperties,
    private val requestHeader: String = "Authorization",
    private val tokenPrefix: String = "Bearer ",
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val header = request.getHeader(requestHeader)
        if (header?.startsWith(tokenPrefix) == true) {
            val token = header.substring(tokenPrefix.length)
            val loginUser = LoginUser.fromJwt(token, securityProperties)
            loginUser?.also { user ->
                user.save()
                SecurityContextHolder.getContext().authentication = authentication(user, request)
            }
        }
        filterChain.doFilter(request, response)
    }
}

private fun authentication(loginUser: LoginUser, request: HttpServletRequest): Authentication {
    // 创建 UsernamePasswordAuthenticationToken 对象
    val authenticationToken = UsernamePasswordAuthenticationToken(
        loginUser, null, emptyList()
    )
    authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
    return authenticationToken
}
