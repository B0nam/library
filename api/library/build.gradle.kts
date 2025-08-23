plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
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

	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql:$postgresVersion")
	implementation("org.testcontainers:postgresql:$testcontainersVersion")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:testcontainers:$testcontainersVersion") // TODO: VERIFICAR VERSAO DO TESTCONTAINERS
	testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion") // TODO: VERIFICAR VERSAO DO TESTCONTAINERS
	testImplementation("org.testcontainers:postgresql:$testcontainersVersion") // TODO: VERIFICAR VERSAO DO TESTCONTAINERS
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<Test>("integrationTest") {
	description = "Executes the integration tests."
	group = "verification"
	useJUnitPlatform {
		includeTags("integration")
	}
}
