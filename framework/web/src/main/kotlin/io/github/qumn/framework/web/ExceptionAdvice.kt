package io.github.qumn.framework.web

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.qumn.framework.exception.BizException
import io.github.qumn.framework.web.common.Rsp
import io.github.qumn.framework.web.util.requestToCurl
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionAdvice {

    val logger = KotlinLogging.logger {}

    @ExceptionHandler(BizException::class)
    fun handleBizException(e: BizException, req: HttpServletRequest): ResponseEntity<Rsp<Unit>> {
        e.printStackTrace()
        logger.debug {
            "encounter a business exception can be reproduce by\n ${requestToCurl(req)}"
        }
        return ResponseEntity
            .status(e.code)
            .body(Rsp.err(msg = e.msg))
    }

    @ExceptionHandler(Exception::class)
    fun defaultExceptionHandler(e: Exception, req: HttpServletRequest): ResponseEntity<Rsp<Unit>> {
        e.printStackTrace()
        logger.debug {
            "encounter a exception can be reproduce by\n ${requestToCurl(req)}"
        }
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Rsp.err(msg = e.message ?: "unknown error"))
    }

}