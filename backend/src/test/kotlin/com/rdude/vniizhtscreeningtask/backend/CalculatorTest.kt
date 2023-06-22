package com.rdude.vniizhtscreeningtask.backend

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@SpringBootTest
class CalculatorTest(@Autowired val calculatorService: CalculatorService) {


    @Test
    fun operators() {
        val expected = -2.0 + 2.0 * 6.0 - 7.0
        val actual = calculatorService.calculate("-2 + 2 * 6 - 7")
        assert(expected.toString() == actual)
    }

    @Test
    fun operatorsAndParenthesis() {
        val expected = 2.0 + 2.0 + 3.0 - 5.0 * 2.0 + (-8.0 + 2.0) / 4.0
        val actual = calculatorService.calculate("2 + 2 + 3 - 5 * 2 + (-8 + 2) / 4")
        assert(expected.toString() == actual)
    }

    @Test
    fun functions() {
        val expected = cos(121.0).pow(sqrt(9.0))
        val actual = calculatorService.calculate("cos(121.0)^sqrt(9)")
        assert(expected.toString() == actual)
    }

    @Test
    fun complexEquation() {
        val expected = -900.0 * -1.0 + (3.0.pow(5.0) - 7.0 * -sin(45.0)) / 2.0
        val actual = calculatorService.calculate("-900 * -1 + (3 ^ 5 - 7 * -sin(45.0)) / 2")
        assert(expected.toString() == actual)
    }

    @Test
    fun wrongParenthesis() {
        assertThrows<InvalidExpressionException> {
            calculatorService.calculate("(5 + 2)) - ((7)")
        }
        assertThrows<InvalidExpressionException> {
            calculatorService.calculate("2+2()(((")
        }
        assertThrows<InvalidExpressionException> {
            calculatorService.calculate("(((717))")
        }
    }

    @Test
    fun divisionByZero() {
        assertThrows<DivisionByZeroException> {
            calculatorService.calculate("717 / 0")
        }
    }

    @Test
    fun invalidExpression() {
        assertThrows<InvalidExpressionException> {
            calculatorService.calculate("25 + - * 12")
        }
        assertThrows<InvalidExpressionException> {
            calculatorService.calculate("cos(^5)sqrt 9")
        }
    }

    @Test
    fun unknownOperator() {
        assertThrows<UnknownOperatorException> {
            calculatorService.calculate("some meaningless input")
        }
    }

}