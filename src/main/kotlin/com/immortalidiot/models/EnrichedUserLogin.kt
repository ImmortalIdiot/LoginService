package com.immortalidiot.models

import kotlinx.serialization.Serializable

@Serializable
data class EnrichedUserLogin(
    val userId: Long,
    val ipAddress: String,
    val timestamp: String,
    val name: String,
    val country: String,
    val subscriptionType: String
)
