package com.example.mykmpapplication

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorScreen() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.gradient_start),
                                colorResource(id = R.color.gradient_end)
                            )
                        )
                    )
            ) {
                val calculator = remember { Calculator() }

                var num1 by remember { mutableStateOf("") }
                var num2 by remember { mutableStateOf("") }
                var result by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Android Calculator",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = num1,
                        onValueChange = { num1 = it },
                        label = { Text("Number 1") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = num2,
                        onValueChange = { num2 = it },
                        label = { Text("Number 2") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                result = calculator.add(
                                    num1.toDoubleOrNull() ?: 0.0,
                                    num2.toDoubleOrNull() ?: 0.0
                               ).toString()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+", style = MaterialTheme.typography.titleLarge)
                        }

                        Button(
                            onClick = {
                                result = calculator.sub(
                                    num1.toDoubleOrNull() ?: 0.0,
                                    num2.toDoubleOrNull() ?: 0.0
                                ).toString()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("-", style = MaterialTheme.typography.titleLarge)
                        }

                        Button(
                            onClick = {
                                result = calculator.multiply(
                                    num1.toDoubleOrNull() ?: 0.0,
                                    num2.toDoubleOrNull() ?: 0.0
                                ).toString()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("×", style = MaterialTheme.typography.titleLarge)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Result",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = result.ifEmpty { "--" },
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun CalculatorScreenPreviewLight() {
    CalculatorScreen()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun CalculatorScreenPreviewDark() {
    CalculatorScreen()
}
