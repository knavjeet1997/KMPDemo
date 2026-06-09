package com.example.mykmpapplication.network

object CommonHeaders {
    fun get(): Map<String, String> {
        return mapOf(
            HeaderKeys.ACCEPT to "application/json",
            HeaderKeys.CONNECTION to "Keep-Alive",
            HeaderKeys.DEVICE_ID to HeaderConfig.deviceId,
            HeaderKeys.DEVICE_TYPE to HeaderConfig.deviceType,
            HeaderKeys.LATITUDE to HeaderConfig.latitude,
            HeaderKeys.LONGITUDE to HeaderConfig.longitude,
            HeaderKeys.USER_AGENT to HeaderConfig.userAgent
        )
    }
}
