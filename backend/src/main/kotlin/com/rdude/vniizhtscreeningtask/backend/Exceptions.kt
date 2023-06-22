package com.rdude.vniizhtscreeningtask.backend

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UnknownOperatorException(operator: String) : RuntimeException("Unknown operator \"$operator\"")

@ResponseStatus(HttpStatus.BAD_REQUEST)
class DivisionByZeroException : RuntimeException("Division by zero")

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidExpressionException(message: String) : RuntimeException(message)