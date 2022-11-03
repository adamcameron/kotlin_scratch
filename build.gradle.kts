val kotestVersion: String by project
val systemLambdaVersion: String by project
val commonsLang3Version: String by project
val junitJupiterVersion: String by project
val ktorVersion: String by project
val exposedVersion: String by project
val mysqlConnectorVersion: String by project

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization").version("1.7.10")
    application
}

group = "me.adamcameron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.atteo:evo-inflector:1.2.2")
    implementation("io.ktor:ktor-client-cio-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    testImplementation("com.github.stefanbirkner:system-lambda:$systemLambdaVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.apache.commons:commons-lang3:$commonsLang3Version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
    testImplementation(kotlin("test"))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}