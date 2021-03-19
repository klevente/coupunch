package dev.klevente.coupunch.library.exception

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@ConditionalOnClass(ResponseEntityExceptionHandler::class)
class MvcExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    protected fun handleEntityNotFound(ex: EntityNotFoundException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, ex.toApiError(), HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(BadRequestException::class)
    protected fun handleBadRequest(ex: BadRequestException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, ex.toApiError(), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request)
    }
}