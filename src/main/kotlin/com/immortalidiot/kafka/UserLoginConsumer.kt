package com.immortalidiot.kafka

import com.immortalidiot.models.UserLoginEvent
import com.immortalidiot.service.UserLoginEnrichmentService
import kotlinx.coroutines.*
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import java.time.Duration
import kotlin.coroutines.CoroutineContext

class UserLoginConsumer(
    private val consumer: KafkaConsumer<String, UserLoginEvent>,
    private val enrichmentService: UserLoginEnrichmentService,
    private val inputTopic: String
) : CoroutineScope {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun start() {
        launch {
            try {
                consumer.subscribe(listOf(inputTopic))
                while (isActive) {
                    val records = consumer.poll(Duration.ofMillis(100))
                    records.forEach { record ->
                        try {
                            logger.info("Received user login event: {}", record.value())
                            enrichmentService.enrichAndPublish(record.value())
                        } catch (e: Exception) {
                            logger.error("Error processing user login event: {}", record.value(), e)
                        }
                    }
                }
            } catch (e: Exception) {
                logger.error("Error in consumer loop", e)
            } finally {
                consumer.close()
            }
        }
    }

    fun stop() {
        job.cancel()
    }
}
