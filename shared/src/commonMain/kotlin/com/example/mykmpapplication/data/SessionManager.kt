package com.example.mykmpapplication.data

import com.russhwolf.settings.Settings

class SessionManager(private val settings: Settings = Settings()) {
    companion object {
        private const val KEY_TOKEN = "key_auth_token"
    }

    fun saveToken(token: String) {
        settings.putString(KEY_TOKEN, token)
    }

    fun getToken(): String? {
        return settings.getStringOrNull(KEY_TOKEN)
    }

    fun clear() {
        settings.remove(KEY_TOKEN)
    }

    fun isLoggedIn(): Boolean {
        val token = getToken()
        return !token.isNullOrBlank()
    }
}
