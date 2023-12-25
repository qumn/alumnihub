package io.github.qumn.framework.web.util;

import com.fasterxml.jackson.databind.json.JsonMapper
import io.github.qumn.framework.web.REQUEST_BODY_ATTRIBUTE_NAME
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestMethod


private val jsonMapper = JsonMapper()
fun requestToCurl(
    request: HttpServletRequest,
): String {
    val result = StringBuilder()
    result.append("curl --request ")

    // output method
    result.append(request.method).append(" ")

    // output url
    result.append("\"")
        .append(request.requestURL)
        .append("\"")

    // output headers
    val headerNames = request.headerNames
    while (headerNames.hasMoreElements()) {
        val headerName = headerNames.nextElement()
        result
            .append(" \\")
            .append("\n")
            .append(" -H \"")
            .append(headerName).append(": ")
            .append(request.getHeader(headerName))
            .append("\"")
    }

    // output parameters
    val parameterNames = request.parameterNames
    while (parameterNames.hasMoreElements()) {
        val parameterName= parameterNames.nextElement()
        result
            .append(" \\")
            .append("\n")
            .append(" -d \"")
            .append(parameterName)
            .append("=")
            .append(request.getParameter(parameterName))
            .append("\"")
    }

    // output body
    if (RequestMethod.POST.name.equals(request.method, ignoreCase = true)) {
        val requestBody = request.getAttribute(REQUEST_BODY_ATTRIBUTE_NAME)
        if (requestBody != null) {
            result
                .append(" -d \'")
                .append(jsonMapper.writeValueAsString(requestBody))
                .append("\'")
        }
    }
    return result.toString()
}