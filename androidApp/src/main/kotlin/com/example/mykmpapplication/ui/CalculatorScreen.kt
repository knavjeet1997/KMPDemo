package com.example.mykmpapplication.ui

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue
import com.example.mykmpapplication.R
import com.example.mykmpapplication.AppTheme
import com.example.mykmpapplication.Calculator

@Composable
fun CalculatorScreen() {
    val calculator = remember { Calculator() }
    
    // Trigger recomposition on state changes
    var displayValue by remember { mutableStateOf("0") }
    var expressionValue by remember { mutableStateOf("") }

    // Helper to sync local state from engine
    val syncState = {
        displayValue = calculator.displayValue
        expressionValue = calculator.expressionValue
    }

    val isDark = isSystemInDarkTheme()
    
    // Theme Colors
    val bgStart = colorResource(id = R.color.calc_bg_start)
    val bgEnd = colorResource(id = R.color.calc_bg_end)
    
    val displayTextColor = colorResource(id = R.color.calc_display_text)
    val expressionTextColor = colorResource(id = R.color.calc_expression_text)
    
    val numberBg = colorResource(id = R.color.calc_number_bg)
    val numberFg = colorResource(id = R.color.calc_number_fg)
    
    val functionBg = colorResource(id = R.color.calc_function_bg)
    val functionFg = colorResource(id = R.color.calc_function_fg)
    
    val operatorBg = colorResource(id = R.color.calc_operator_bg)
    val operatorFg = colorResource(id = R.color.calc_operator_fg)

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(colors = listOf(bgStart, bgEnd)))
                    .systemBarsPadding()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Display Area (Top)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures { change, dragAmount ->
                                    if (dragAmount.absoluteValue > 50) {
                                        change.consume()
                                        calculator.backspace()
                                        syncState()
                                    }
                                }
                            }
                            .padding(bottom = 24.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End
                    ) {
                        // Formula/Expression History
                        if (expressionValue.isNotEmpty()) {
                            Text(
                                text = expressionValue,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Normal
                                ),
                                color = expressionTextColor,
                                textAlign = TextAlign.End,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        
                        // Current display value
                        val fontSize = when {
                            displayValue.length > 12 -> 36.sp
                            displayValue.length > 8 -> 48.sp
                            else -> 64.sp
                        }
                        Text(
                            text = displayValue,
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontSize = fontSize,
                                fontWeight = FontWeight.Bold
                            ),
                            color = displayTextColor,
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Keypad Area (Bottom)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Row 1
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val isClearAll = displayValue == "0" && expressionValue.isEmpty()
                            CalculatorButton(
                                text = if (isClearAll) "AC" else "C",
                                onClick = { calculator.clear(); syncState() },
                                containerColor = functionBg,
                                contentColor = functionFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "±",
                                onClick = { calculator.toggleSign(); syncState() },
                                containerColor = functionBg,
                                contentColor = functionFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "%",
                                onClick = { calculator.percentage(); syncState() },
                                containerColor = functionBg,
                                contentColor = functionFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "÷",
                                onClick = { calculator.inputOperator("÷"); syncState() },
                                containerColor = operatorBg,
                                contentColor = operatorFg,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Row 2
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CalculatorButton(
                                text = "7",
                                onClick = { calculator.inputDigit("7"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "8",
                                onClick = { calculator.inputDigit("8"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "9",
                                onClick = { calculator.inputDigit("9"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "×",
                                onClick = { calculator.inputOperator("×"); syncState() },
                                containerColor = operatorBg,
                                contentColor = operatorFg,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Row 3
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CalculatorButton(
                                text = "4",
                                onClick = { calculator.inputDigit("4"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "5",
                                onClick = { calculator.inputDigit("5"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "6",
                                onClick = { calculator.inputDigit("6"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "−",
                                onClick = { calculator.inputOperator("−"); syncState() },
                                containerColor = operatorBg,
                                contentColor = operatorFg,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Row 4
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CalculatorButton(
                                text = "1",
                                onClick = { calculator.inputDigit("1"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "2",
                                onClick = { calculator.inputDigit("2"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "3",
                                onClick = { calculator.inputDigit("3"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "+",
                                onClick = { calculator.inputOperator("+"); syncState() },
                                containerColor = operatorBg,
                                contentColor = operatorFg,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Row 5
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CalculatorButton(
                                text = "0",
                                onClick = { calculator.inputDigit("0"); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                isCapsule = true,
                                modifier = Modifier.weight(2f)
                            )
                            CalculatorButton(
                                text = ".",
                                onClick = { calculator.inputDecimal(); syncState() },
                                containerColor = numberBg,
                                contentColor = numberFg,
                                modifier = Modifier.weight(1f)
                            )
                            CalculatorButton(
                                text = "=",
                                onClick = { calculator.calculate(); syncState() },
                                containerColor = operatorBg,
                                contentColor = operatorFg,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    isCapsule: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.92f else 1.0f, label = "ButtonScale")

    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = if (isCapsule) RoundedCornerShape(40.dp) else CircleShape,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .then(
                if (isCapsule) {
                    Modifier.fillMaxWidth().height(80.dp)
                } else {
                    Modifier.aspectRatio(1f)
                }
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = if (text.length > 2) 22.sp else 28.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
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
