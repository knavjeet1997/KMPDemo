package com.example.mykmpapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorScreen() {

    val calculator = remember { Calculator() }

    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Number 1") }
        )

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Number 2") }
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    result = calculator.add(
                        num1.toDoubleOrNull() ?: 0.0,
                        num2.toDoubleOrNull() ?: 0.0
                    ).toString()
                }
            ) {
                Text("+")
            }

            Button(
                onClick = {
                    result = calculator.subtract(
                        num1.toDoubleOrNull() ?: 0.0,
                        num2.toDoubleOrNull() ?: 0.0
                    ).toString()
                }
            ) {
                Text("-")
            }

            Button(
                onClick = {
                    result = calculator.multiply(
                        num1.toDoubleOrNull() ?: 0.0,
                        num2.toDoubleOrNull() ?: 0.0
                    ).toString()
                }
            ) {
                Text("×")
            }

            Button(
                onClick = {
                    try {
                        result = calculator.divide(
                            num1.toDoubleOrNull() ?: 0.0,
                            num2.toDoubleOrNull() ?: 0.0
                        ).toString()
                    } catch (e: Exception) {
                        result = e.message ?: "Error"
                    }
                }
            ) {
                Text("÷")
            }
        }

        Text(
            text = "Result: $result",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}