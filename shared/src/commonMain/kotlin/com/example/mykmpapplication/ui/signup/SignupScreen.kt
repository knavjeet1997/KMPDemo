package com.example.mykmpapplication.ui.signup

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mykmpapplication.AppColors
import com.example.mykmpapplication.common.CommonTextField
import com.example.mykmpapplication.common.CustomLoader
import com.example.mykmpapplication.common.CustomToast
import com.example.mykmpapplication.di.KoinHelper
import kotlinx.coroutines.delay

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = remember { KoinHelper().getSignupViewModel() },
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is SignupUiEvent.NavigateToHome -> {
                    delay(1200)
                    onNavigateToHome()
                }
                is SignupUiEvent.ShowToast -> {
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

    SignupScreenContent(
        uiState = uiState,
        toastVisible = toastVisible,
        toastMessage = toastMessage,
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onConfirmPasswordChange = { viewModel.onConfirmPasswordChange(it) },
        onSignupClick = { viewModel.register() },
        onNavigateToLogin = onNavigateToLogin
    )
}

@Composable
fun SignupScreenContent(
    uiState: SignupUiState,
    toastVisible: Boolean,
    toastMessage: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignupClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val bgStart = AppColors.gradientStart()
    val bgEnd = AppColors.gradientEnd()

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
                        text = "Sign Up",
                        textAlign = TextAlign.Center
                    )
                    CommonTextField(
                        value = uiState.email,
                        onValueChange = onEmailChange,
                        placeholder = "Email"
                    )
                    CommonTextField(
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        placeholder = "Password"
                    )
                    CommonTextField(
                        value = uiState.confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        placeholder = "Confirm Password"
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !uiState.isLoading,
                        onClick = onSignupClick
                    ) {
                        Text("Sign Up")
                    }

                    Text(
                        text = "Already have an account? Log In",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToLogin() }
                            .padding(10.dp),
                        textAlign = TextAlign.Center,
                        color = AppColors.calcOperatorBg(),
                        textDecoration = TextDecoration.Underline,
                        fontSize = 16.sp
                    )
                }

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

                if (uiState.isLoading) {
                    CustomLoader()
                }
            }
        }
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    SignupScreenContent(
        uiState = SignupUiState(),
        toastVisible = false,
        toastMessage = null,
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onSignupClick = {},
        onNavigateToLogin = {}
    )
}
