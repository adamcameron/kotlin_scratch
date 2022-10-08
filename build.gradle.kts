plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "me.adamcameron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.5.0")
    testImplementation("io.kotest:kotest-assertions-core:5.5.0")
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")
    testImplementation("org.apache.commons:commons-lang3:3.12.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("io.kotest:kotest-framework-datatest:5.5.0")
    implementation("io.ktor:ktor-client-core:2.1.2")
    implementation("io.ktor:ktor-client-cio:2.1.2")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}