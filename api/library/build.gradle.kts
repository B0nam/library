plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.swagger.core.v3.swagger-gradle-plugin") version "2.2.36"
}

group = "com.bonam"
version = "0.0.1-SNAPSHOT"
description = "Gestão de biblioteca com recomendação de livros"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val postgresVersion = "42.7.7"
val testcontainersVersion = "1.21.3" // TODO: VERIFICAR VERSAO DO TESTCONTAINERS
val springdocVersion = "2.8.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")

    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

springBoot {
    mainClass.set("com.bonam.library.LibraryApplication")
}

tasks {
    test {
        useJUnitPlatform {
            excludeTags("integration")
        }
    }

    register<Test>("integrationTest") {
        description = "Runs integration tests."
        group = "verification"
        useJUnitPlatform {
            includeTags("integration")
        }
        shouldRunAfter(test)

        onlyIf {
            project.hasProperty("runIntegrationTests") ||
                    System.getenv("RUN_INTEGRATION_TESTS") == "true"
        }
    }

    build {
        if (project.hasProperty("runIntegrationTests") ||
            System.getenv("RUN_INTEGRATION_TESTS") == "true"
        ) {
            dependsOn("integrationTest")
        }
    }
}
