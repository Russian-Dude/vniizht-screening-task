package com.rdude.vniizhtscreeningtask.backend

import org.springframework.stereotype.Service

@Service
class CalculatorService(val tokenizer: Tokenizer, val tokensEvaluator: TokensEvaluator) {

    fun calculate(equation: String): String {
        val tokens = tokenizer.convertToTokens(equation)
        return tokensEvaluator.evaluate(tokens).toString()
    }

}