package com.rdude.vniizhtscreeningtask.backend

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/calculator")
class CalculatorController(val calculatorService: CalculatorService) {

    @PostMapping
    @CrossOrigin
    fun calculate(@RequestBody equation: String): String =
        calculatorService.calculate(equation)

}