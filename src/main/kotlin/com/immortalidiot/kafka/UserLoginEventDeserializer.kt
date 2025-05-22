package com.immortalidiot.kafka

import com.immortalidiot.config.KafkaConfig
import com.immortalidiot.models.UserLoginEvent
import org.apache.kafka.common.serialization.Deserializer

class UserLoginEventDeserializer : Deserializer<UserLoginEvent> {
    override fun deserialize(topic: String?, data: ByteArray): UserLoginEvent {
        return KafkaConfig.objectMapper.readValue(data, UserLoginEvent::class.java)
    }
}
