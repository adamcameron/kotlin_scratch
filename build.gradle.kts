val kotest_version: String by project
val system_lambda_version: String by project
val commons_lang_3_version: String by project
val junit_jupiter_version: String by project
val ktor_client_core_version: String by project

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
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
    testImplementation("io.kotest:kotest-framework-datatest:$kotest_version")
    testImplementation("com.github.stefanbirkner:system-lambda:$system_lambda_version")
    testImplementation("org.apache.commons:commons-lang3:$commons_lang_3_version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_jupiter_version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_jupiter_version")
    implementation("io.ktor:ktor-client-core:$ktor_client_core_version")
    implementation("io.ktor:ktor-client-cio:$ktor_client_core_version")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}