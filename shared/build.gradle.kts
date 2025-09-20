plugins {
    `java-library`
}

group = "com.bank"
version = "0.0.1-SNAPSHOT"
description = "shared module"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {}

tasks.withType<Test> {
    useJUnitPlatform()
}