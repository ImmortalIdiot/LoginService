package com.immortalidiot.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.immortalidiot.kafka.UserLoginEventDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class KafkaConfig {
    private val bootstrapServers = Config.kafkaBootstrapServers

    companion object {
        val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    }

    class JsonSerializer<T> : Serializer<T> {
        override fun serialize(topic: String?, data: T): ByteArray {
            return objectMapper.writeValueAsBytes(data)
        }
    }

    fun producerProps(): Properties = Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer::class.java)
    }

    fun userLoginConsumerProps(groupId: String = "user-login-group"): Properties = Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserLoginEventDeserializer::class.java)
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    }
}
