package com.immortalidiot

import com.immortalidiot.config.Config
import com.immortalidiot.config.KafkaConfig
import com.immortalidiot.kafka.UserLoginConsumer
import com.immortalidiot.models.EnrichedUserLogin
import com.immortalidiot.models.InstantSerializer
import com.immortalidiot.models.UserLoginEvent
import com.immortalidiot.repository.UserRepository
import com.immortalidiot.routes.userRoutes
import com.immortalidiot.service.UserLoginEnrichmentService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer

fun main() {
    embeddedServer(Netty, port = 8282, module = Application::module).start(wait = true)
}

fun Application.module() {
    val kafkaConfig = KafkaConfig()

    val inputTopic = Config.kafkaInputTopic
    val outputTopic = Config.kafkaOutputTopic

    val userLoginProducer = KafkaProducer<String, UserLoginEvent>(kafkaConfig.producerProps())
    val enrichedLoginProducer = KafkaProducer<String, EnrichedUserLogin>(kafkaConfig.producerProps())

    val userLoginConsumer = KafkaConsumer<String, UserLoginEvent>(kafkaConfig.userLoginConsumerProps())

    val userRepository = UserRepository()

    val enrichmentService = UserLoginEnrichmentService(enrichedLoginProducer, outputTopic, userRepository)
    val consumer = UserLoginConsumer(userLoginConsumer, enrichmentService, inputTopic)

    consumer.start()

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                serializersModule = SerializersModule {
                    contextual(InstantSerializer)
                }
            }
        )
    }

    routing {
        userRoutes(userLoginProducer, inputTopic)
    }

    environment.monitor.subscribe(ApplicationStopped) {
        consumer.stop()
        userLoginProducer.close()
        enrichedLoginProducer.close()
        userLoginConsumer.close()
    }
}
