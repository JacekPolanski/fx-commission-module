plugins {
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("java")
}

group = "com.bank"
version = "0.0.1-SNAPSHOT"
description = "FX Commission"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    repositories {
        mavenCentral()
    }
    extensions.findByType<JavaPluginExtension>()?.toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.5")
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":account"))
    implementation(project(":customer"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("com.h2database:h2")
    testRuntimeOnly("com.h2database:h2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
