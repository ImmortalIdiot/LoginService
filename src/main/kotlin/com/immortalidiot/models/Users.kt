package com.immortalidiot.models

import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable("users", "user_id") {
    val username = varchar("name", 255)
    val country = varchar("country", 255)
    val subscriptionType = varchar("subscription_type", 50)
}
