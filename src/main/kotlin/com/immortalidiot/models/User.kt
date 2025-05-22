package com.immortalidiot.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<User>(Users)
    var username by Users.username
    var country by Users.country
    var subscriptionType by Users.subscriptionType
}

