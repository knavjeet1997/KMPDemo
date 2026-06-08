package com.example.mykmpapplication

import kotlin.math.round

class Calculator {
    var displayValue: String = "0"
        private set
    
    var expressionValue: String = ""
        private set
        
    private var previousValue: Double? = null
    private var pendingOperator: String? = null
    private var isEnteringNewNumber: Boolean = false

    fun inputDigit(digit: String) {
        if (isEnteringNewNumber || displayValue == "Error") {
            displayValue = digit
            isEnteringNewNumber = false
        } else {
            if (displayValue == "0") {
                displayValue = digit
            } else {
                displayValue += digit
            }
        }
        updateExpression()
    }

    fun inputDecimal() {
        if (isEnteringNewNumber || displayValue == "Error") {
            displayValue = "0."
            isEnteringNewNumber = false
        } else {
            if (!displayValue.contains(".")) {
                displayValue += "."
            }
        }
        updateExpression()
    }

    fun inputOperator(op: String) {
        if (displayValue == "Error") {
            clear()
            return
        }
        val currentValue = displayValue.toDoubleOrNull() ?: 0.0
        
        if (pendingOperator != null && !isEnteringNewNumber) {
            val res = calculateValue(previousValue ?: 0.0, currentValue, pendingOperator!!)
            displayValue = formatResult(res)
            previousValue = res
        } else {
            previousValue = currentValue
        }
        
        pendingOperator = op
        isEnteringNewNumber = true
        updateExpression()
    }

    fun calculate() {
        if (pendingOperator == null || previousValue == null) return
        
        val currentValue = displayValue.toDoubleOrNull() ?: 0.0
        val res = calculateValue(previousValue!!, currentValue, pendingOperator!!)
        displayValue = formatResult(res)
        
        previousValue = null
        pendingOperator = null
        isEnteringNewNumber = true
        expressionValue = ""
    }

    fun toggleSign() {
        val value = displayValue.toDoubleOrNull() ?: return
        if (value == 0.0) return
        
        displayValue = if (displayValue.startsWith("-")) {
            displayValue.substring(1)
        } else {
            "-$displayValue"
        }
        updateExpression()
    }

    fun percentage() {
        val currentValue = displayValue.toDoubleOrNull() ?: return
        val res = currentValue / 100.0
        displayValue = formatResult(res)
        updateExpression()
    }

    fun backspace() {
        if (isEnteringNewNumber) return
        
        displayValue = if (displayValue.length <= 1 || (displayValue.length == 2 && displayValue.startsWith("-"))) {
            "0"
        } else {
            displayValue.substring(0, displayValue.length - 1)
        }
        updateExpression()
    }

    fun clear() {
        displayValue = "0"
        expressionValue = ""
        previousValue = null
        pendingOperator = null
        isEnteringNewNumber = false
    }

    private fun calculateValue(a: Double, b: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-", "−" -> a - b
            "×", "*" -> a * b
            "÷", "/" -> if (b == 0.0) Double.NaN else a / b
            else -> b
        }
    }

    private fun formatResult(value: Double): String {
        if (value.isNaN()) return "Error"
        if (value.isInfinite()) return "Error"
        
        // If it's a whole number, represent as integer
        if (value == value.toLong().toDouble()) {
            return value.toLong().toString()
        }
        
        // Format decimal to avoid floating point precision tails (e.g. 0.30000000000000004)
        val rounded = round(value * 1e10) / 1e10
        val roundedStr = rounded.toString()
        return if (roundedStr.endsWith(".0")) {
            roundedStr.substring(0, roundedStr.length - 2)
        } else {
            roundedStr
        }
    }

    private fun updateExpression() {
        val prev = previousValue
        val op = pendingOperator
        expressionValue = if (prev != null && op != null) {
            val prevStr = formatResult(prev)
            "$prevStr $op"
        } else {
            ""
        }
    }

    // Keep backwards compatibility with the original app code if any other files use them
    fun add(a: Double, b: Double): Double = a + b
    fun sub(a: Double, b: Double): Double = a - b
    fun multiply(a: Double, b: Double): Double = a * b
}