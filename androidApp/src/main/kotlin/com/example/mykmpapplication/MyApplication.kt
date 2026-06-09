package com.example.mykmpapplication

import android.app.Application
import com.example.mykmpapplication.di.initKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
