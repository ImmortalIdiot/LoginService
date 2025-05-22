package com.immortalidiot.repository

import com.immortalidiot.sharding.ShardManager
import com.immortalidiot.models.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

class UserRepository {
    private val logger = LoggerFactory.getLogger(UserRepository::class.java)

    private fun connect(shardIndex: Int): Database {
        val url = ShardManager.getConnectionString(shardIndex)
        val user = "postgres"
        val password = "postgres"
        return Database.connect(url, driver = "org.postgresql.Driver", user = user, password = password)
    }

    fun getUserById(userId: Long): User? {
        val shardIndex = ShardManager.getShardIndex(userId.toInt())
        logger.info("Read user $userId from shard $shardIndex")

        val db = connect(shardIndex)

        return transaction(db) {
            User.findById(userId)
        }
    }
}
