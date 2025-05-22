package com.immortalidiot.routes

import com.immortalidiot.models.UserLoginEvent
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

fun Route.userRoutes(
    producer: KafkaProducer<String, UserLoginEvent>,
    inputTopic: String,
) {
    route("/api/user") {
        post("/login") {
            val event = call.receive<UserLoginEvent>()
            producer.send(ProducerRecord(inputTopic, event.userId.toString(), event))
            call.respond(HttpStatusCode.OK, "Login event sent to Kafka")
        }
    }
}
