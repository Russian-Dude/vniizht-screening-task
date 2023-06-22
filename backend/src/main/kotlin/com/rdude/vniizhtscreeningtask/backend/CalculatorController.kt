package com.rdude.vniizhtscreeningtask.backend

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/calculator")
class CalculatorController(val calculatorService: CalculatorService) {

    @PostMapping
    fun calculate(@RequestBody equation: String): String =
        calculatorService.calculate(equation)

}