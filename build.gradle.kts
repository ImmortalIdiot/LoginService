plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.serialization") version "2.0.10"
    id("io.ktor.plugin") version "3.1.1"
}

group = "com.immortalidiot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val ktorVersion = "3.1.1"
val kafkaVersion = "3.9.1"
val exposedVersion = "0.61.0"
val postgresVersion = "42.7.3"
val logbackVersion = "1.5.16"
val kotlinxSerializationVersion = "1.6.3"
val coroutinesVersion = "1.10.2"

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
}

kotlin {
    jvmToolchain(21)
}
