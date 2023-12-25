package io.github.qumn.framework.web

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import java.lang.reflect.Type


const val REQUEST_BODY_ATTRIBUTE_NAME = "__BODY"

/**
 * save request body to request attribute
 * so that we can get it in [io.github.qumn.framework.web.util.CurlUtil]
 */
@ControllerAdvice
class RequestBodySaveAdvice : RequestBodyAdviceAdapter() {

    val logger = KotlinLogging.logger {}

    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>,
    ): Boolean {
        return true
    }

    override fun afterBodyRead(
        body: Any,
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>,
    ): Any {
        // if in debug mode
        if (logger.isDebugEnabled()) {
            val requestAttributes = RequestContextHolder.getRequestAttributes()
            requestAttributes?.setAttribute(REQUEST_BODY_ATTRIBUTE_NAME, body, RequestAttributes.SCOPE_REQUEST)
        }
        return body
    }

}