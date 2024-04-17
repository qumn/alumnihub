package io.github.qumn.framework.security.config

import io.github.qumn.framework.web.common.Rsp
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class JsonAuthenticationEntryPoint : AuthenticationEntryPoint {
    @OptIn(ExperimentalSerializationApi::class)
    override fun commence(
        req: HttpServletRequest,
        rsp: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val rspBody = Rsp(403, "请先登陆", authException.message)
        rsp.contentType = "application/json"
        Json.encodeToStream(rspBody, rsp.outputStream)
    }

}
