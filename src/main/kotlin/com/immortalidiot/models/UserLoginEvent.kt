package com.immortalidiot.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginEvent(
    val userId: Long,
    val ipAddress: String,
    val timestamp: String
)
