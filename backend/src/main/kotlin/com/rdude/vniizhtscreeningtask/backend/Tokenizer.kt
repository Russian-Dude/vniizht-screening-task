package com.rdude.vniizhtscreeningtask.backend

import org.springframework.stereotype.Component

@Component
class Tokenizer(val operators: List<Operator>, val functions: List<Function>) {

    fun convertToTokens(string: String): List<Token> =
        convertToTokens(string.split(Regex("((?=[+\\-*/()^ ])|(?<=[+\\-*/()^ ]))")))


    fun convertToTokens(stringTokens: List<String>): List<Token> {
        val result = ArrayList<Token>(stringTokens.size)

        return stringTokens
            .asSequence()
            .filterNot { it.isBlank() }
            .mapIndexedTo(result) { index, string ->
                if (string.toDoubleOrNull() != null) NumberToken(string.toDouble())
                else if (string == "(") OpenParenthesis
                else if (string == ")") ClosingParenthesis
                else if (string == "-" && (index == 0 || result[index - 1] is Operator)) UnaryMinus
                else operators.find { it.operator == string }
                    ?: functions.find { it.declaration == string }
                    ?: throw UnknownOperatorException(string)
            }
    }

}