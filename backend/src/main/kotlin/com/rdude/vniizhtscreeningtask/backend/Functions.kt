package com.rdude.vniizhtscreeningtask.backend

import org.springframework.stereotype.Component
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan


interface Function : Token {

    val declaration: String

    fun evaluate(arg: Double): Double

}


@Component
class Sin : Function {

    override val declaration: String get() = "sin"

    override fun evaluate(arg: Double): Double =
        sin(arg)
}


@Component
class Cos : Function {

    override val declaration: String get() = "cos"

    override fun evaluate(arg: Double): Double =
        cos(arg)
}


@Component
class Tan : Function {

    override val declaration: String get() = "tan"

    override fun evaluate(arg: Double): Double =
        tan(arg)
}


@Component
class Sqrt : Function {

    override val declaration: String get() = "sqrt"

    override fun evaluate(arg: Double): Double =
        sqrt(arg)
}


object UnaryMinus : Function {

    override val declaration: String get() = "-"

    override fun evaluate(arg: Double): Double =
        -arg
}