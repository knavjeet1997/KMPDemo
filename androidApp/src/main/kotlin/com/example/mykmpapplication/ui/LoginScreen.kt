package com.example.mykmpapplication.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mykmpapplication.R
import com.example.mykmpapplication.common.CommonTextField
import com.example.mykmpapplication.common.CustomLoader
import com.example.mykmpapplication.common.CustomToast
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val email = uiState.email
    val password = uiState.password

    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.NavigateToHome -> {
                    delay(1200)
                    onNavigateToHome()
                }
                is LoginUiEvent.ShowToast -> {
                    toastMessage = event.message
                }
            }
        }
    }

    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            toastVisible = true
            delay(3000)
            toastVisible = false
            delay(500)
            toastMessage = null
        }
    }

    val bgStart = colorResource(id = R.color.gradient_start)
    val bgEnd = colorResource(id = R.color.gradient_end)

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(colors = listOf(bgStart, bgEnd)))
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                        fontSize = 40.sp,
                        text = "Log In",
                        textAlign = TextAlign.Center
                    )
                    CommonTextField(
                        value = email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        placeholder = "Email"
                    )
                    CommonTextField(
                        value = password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        placeholder = "Password"
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !uiState.isLoading,
                        onClick = {
                            viewModel.login()
                        }
                    ) {
                        Text("Log In")
                    }

                    Text(
                        text = "Don't have an account? Sign Up",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToSignup() }
                            .padding(10.dp),
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.calc_operator_bg),
                        textDecoration = TextDecoration.Underline,
                        fontSize = 16.sp
                    )
                }

                // Smooth sliding custom toast overlay
                AnimatedVisibility(
                    visible = toastVisible && toastMessage != null,
                    enter = slideInVertically(initialOffsetY = { -it }),
                    exit = slideOutVertically(targetOffsetY = { -it }),
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    toastMessage?.let {
                        CustomToast(message = it)
                    }
                }

                // Simplified loader overlay call using isLoading state
                if (uiState.isLoading) {
                    CustomLoader()
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun LoginScreenPreviewLight() {
    LoginScreen(onNavigateToHome = {}, onNavigateToSignup = {})
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenPreviewDark() {
    LoginScreen(onNavigateToHome = {}, onNavigateToSignup = {})
}
