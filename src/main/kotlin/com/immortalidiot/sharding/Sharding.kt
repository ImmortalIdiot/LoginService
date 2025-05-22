package com.immortalidiot.sharding

import com.immortalidiot.config.JDBCUrl

object ShardManager {
    private const val SHARD_COUNT = 2

    fun getShardIndex(id: Int): Int {
        return (id % SHARD_COUNT)
    }

    fun getConnectionString(shardIndex: Int): String {
        return when (shardIndex) {
            1 -> JDBCUrl.FIRST_SHARD_URL
            2 -> JDBCUrl.SECOND_SHARD_URL
            else -> throw IllegalArgumentException("Invalid shard index")
        }
    }
}
