package io.github.qumn.framework.security.config;

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.qumn.framework.web.common.err
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class JsonAccessDeniedHandler : AccessDeniedHandler {
    var logger = KotlinLogging.logger {}

    @OptIn(ExperimentalSerializationApi::class)
    override fun handle(
        req: HttpServletRequest,
        rsp: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        logger.info { "handle the access denied request" }
        val rspBody = accessDeniedException.message.err()
        rsp.status = 403
        Json.encodeToStream(rspBody, rsp.outputStream)
        rsp.outputStream.flush()
    }
}
