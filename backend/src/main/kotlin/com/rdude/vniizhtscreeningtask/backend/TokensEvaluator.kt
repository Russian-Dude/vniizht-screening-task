package com.rdude.vniizhtscreeningtask.backend

import org.springframework.stereotype.Component

@Component
class TokensEvaluator {

    fun evaluate(tokens: List<Token>): Double =
        tokens.toPostfixNotation().evaluateTokens()


    private fun List<Token>.toPostfixNotation(): List<Token> {
        val result = ArrayList<Token>(size)
        val stack = ArrayDeque<Token>()

        forEach { token ->
            when (token) {
                is NumberToken -> result += token
                is OpenParenthesis -> stack.addFirst(token)
                is ClosingParenthesis -> {
                    while (stack.isNotEmpty() && stack.first() !is OpenParenthesis) {
                        result += stack.removeFirst()
                    }
                    if (stack.isEmpty() || stack.first() !is OpenParenthesis) {
                        throw InvalidExpressionException("Mismatched parentheses")
                    }
                    stack.removeFirst()
                }
                is Operator -> {
                    var top = stack.firstOrNull()
                    while (top is Function || (top is Operator && top.priority >= token.priority)) {
                        result += stack.removeFirst()
                        top = stack.firstOrNull()
                    }
                    stack.addFirst(token)
                }
                is Function -> stack.addFirst(token)
            }
        }

        stack.forEach {
            if (it is OpenParenthesis) throw InvalidExpressionException("Mismatched parentheses")
            result += it
        }

        return result
    }


    private fun List<Token>.evaluateTokens(): Double {
        val stack = ArrayDeque<Double>()

        forEach { token ->
            when (token) {
                is NumberToken -> stack.addFirst(token.number)
                is Operator -> {
                    if (stack.size < 2) throw InvalidExpressionException("Invalid expression")
                    val second = stack.removeFirst()
                    val first = stack.removeFirst()
                    stack.addFirst(token.performOperation(first, second))
                }
                is Function -> {
                    if (stack.isEmpty()) throw InvalidExpressionException("Invalid expression")
                    stack.addFirst(token.evaluate(stack.removeFirst()))
                }
                else -> throw InvalidExpressionException("Invalid expression")
            }
        }

        if (stack.size != 1) throw InvalidExpressionException("Invalid expression")

        return stack.first()
    }

}