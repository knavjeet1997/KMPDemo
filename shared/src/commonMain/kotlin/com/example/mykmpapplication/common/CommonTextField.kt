package com.example.mykmpapplication.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mykmpapplication.AppColors

@Composable
fun CommonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = placeholder) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = AppColors.calcFunctionBg(),
            unfocusedContainerColor = AppColors.calcFunctionBg(),
            focusedBorderColor = AppColors.calcOperatorBg(),
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}
