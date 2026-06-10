package com.example.mykmpapplication.di

import com.example.mykmpapplication.data.AuthRepository
import com.example.mykmpapplication.data.AuthRepositoryImpl
import com.example.mykmpapplication.ui.SignupViewModel
import com.example.mykmpapplication.ui.LoginViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    factory { SignupViewModel(get()) }
    factory { LoginViewModel(get()) }
}

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}

class KoinHelper : KoinComponent {
    fun getSignupViewModel(): SignupViewModel = get()
    fun getLoginViewModel(): LoginViewModel = get()
}
