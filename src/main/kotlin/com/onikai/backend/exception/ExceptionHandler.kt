package com.onikai.backend.exception

import InvalidGameStateException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {
  @ExceptionHandler(InvalidGameStateException::class)
  @ResponseStatus(HttpStatus.CONFLICT)  // Or choose an appropriate HTTP status
  fun handleInvalidGameStateException(ex: InvalidGameStateException): ResponseEntity<String> {
    return ResponseEntity(ex.message, HttpStatus.CONFLICT)
  }
}