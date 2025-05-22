package com.immortalidiot.service

import com.immortalidiot.models.EnrichedUserLogin
import com.immortalidiot.models.UserLoginEvent
import com.immortalidiot.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.time.Instant

class UserLoginEnrichmentService(
    private val producer: KafkaProducer<String, EnrichedUserLogin>,
    private val outputTopic: String,
    private val userRepository: UserRepository
) {
    suspend fun enrichAndPublish(event: UserLoginEvent) {
        try {
            val user = withContext(Dispatchers.IO) {
                userRepository.getUserById(event.userId)
                    ?: throw RuntimeException("User not found")
            }

            val enrichedEvent = EnrichedUserLogin(
                userId = event.userId,
                ipAddress = event.ipAddress,
                timestamp = Instant.now().toString(),
                name = user.username,
                country = user.country,
                subscriptionType = user.subscriptionType
            )

            producer.send(ProducerRecord(outputTopic, enrichedEvent.userId.toString(), enrichedEvent)).get()
        } catch (e: Exception) {
            println("Error enriching user login event: ${e.message}")
        }
    }
}
