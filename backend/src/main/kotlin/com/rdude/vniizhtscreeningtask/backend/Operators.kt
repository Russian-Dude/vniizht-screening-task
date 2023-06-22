package com.rdude.vniizhtscreeningtask.backend

import org.springframework.stereotype.Component
import kotlin.math.pow


interface Operator : Token {

    val operator: String

    val priority: Int

    fun performOperation(operand1: Double, operand2: Double): Double

}


@Component
class Plus : Operator {

    override val operator: String get() = "+"

    override val priority: Int get() = 1

    override fun performOperation(operand1: Double, operand2: Double): Double =
        operand1 + operand2
}


@Component
class Minus : Operator {

    override val operator: String get() = "-"

    override val priority: Int get() = 1

    override fun performOperation(operand1: Double, operand2: Double): Double =
        operand1 - operand2
}


@Component
class Multiply : Operator {

    override val operator: String get() = "*"

    override val priority: Int get() = 2

    override fun performOperation(operand1: Double, operand2: Double): Double =
        operand1 * operand2
}


@Component
class Divide : Operator {

    override val operator: String get() = "/"

    override val priority: Int get() = 2

    override fun performOperation(operand1: Double, operand2: Double): Double {
        if (operand2 == 0.0) throw DivisionByZeroException()
        return operand1 / operand2
    }
}


@Component
class PowerOf : Operator {

    override val operator: String get() = "^"

    override val priority: Int get() = 3

    override fun performOperation(operand1: Double, operand2: Double): Double =
        operand1.pow(operand2)
}

