package com.immortalidiot.config

object Config {
    val kafkaBootstrapServers: String = System.getenv("KAFKA_BOOTSTRAP_SERVERS") ?: "localhost:9092"
    val kafkaInputTopic: String = System.getenv("KAFKA_INPUT_TOPIC") ?: "user-logins"
    val kafkaOutputTopic: String = System.getenv("KAFKA_OUTPUT_TOPIC") ?: "enriched-user-logins"
}
