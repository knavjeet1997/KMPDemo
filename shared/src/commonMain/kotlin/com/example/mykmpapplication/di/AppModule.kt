package com.example.mykmpapplication.di

import com.example.mykmpapplication.data.AuthRepository
import com.example.mykmpapplication.data.AuthRepositoryImpl
import com.example.mykmpapplication.data.SessionManager
import com.example.mykmpapplication.ui.signup.SignupViewModel
import com.example.mykmpapplication.ui.login.LoginViewModel
import com.example.mykmpapplication.ui.splash.SplashViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

val appModule = module {
    single { SessionManager() }
    single<AuthRepository> { AuthRepositoryImpl(sessionManager = get()) }
    factory { SignupViewModel(get()) }
    factory { LoginViewModel(get()) }
    factory { SplashViewModel(get()) }
}

fun initKoin() {
    try {
        startKoin {
            modules(appModule)
        }
    } catch (e: Exception) {
        // Koin already started, ignore
    }
}

class KoinHelper : KoinComponent {
    fun getSignupViewModel(): SignupViewModel = get()
    fun getLoginViewModel(): LoginViewModel = get()
    fun getSplashViewModel(): SplashViewModel = get()
    fun getSessionManager(): SessionManager = get()
}
