plugins {
	java
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "backendnovice"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// Mockito Core
	testImplementation("org.mockito:mockito-core:5.4.0")
	// Springdoc OpenAPI UI
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	// Spring Boot Starter Security
	implementation("org.springframework.boot:spring-boot-starter-security:3.1.1")
	// Thymeleaf Extras Spring Security 6
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE")
	// Spring Boot Starter Mail
	implementation("org.springframework.boot:spring-boot-starter-mail:3.1.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
