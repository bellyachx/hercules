plugins {
    `java-library`
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.liquibase.gradle") version "2.2.0"
}

group = "me.maxhub.hercules"
version = "0.0.3"

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

repositories {
    mavenCentral()
}

// Spring
val springBootVersion = "3.4.4"
val springDocVersion = "2.8.5"

// Jackson Databind
val jacksonVersion = "2.18.3"

// Mapping
val mapstructVersion = "1.6.3"

// Database
val postgresVersion = "42.7.5"

// Liquibase
val liquibaseVersion = "4.31.1"
val picocliVersion = "4.7.6"

// Lombok
val lombokMapstructBindingVersion = "0.2.0"

// Testing
val h2Version = "2.3.232"

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}

dependencies {
    // === Spring starters start ===
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Data
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Actuator, swagger & devtools
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    // === Spring starters end ===

    // Jackson Databind
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")

    // Mapping
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // Database
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    // Liquibase
    implementation("org.liquibase:liquibase-core")
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.postgresql:postgresql:$postgresVersion")
    liquibaseRuntime("info.picocli:picocli:$picocliVersion")

    // Misc
    implementation("org.apache.commons:commons-lang3")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "logLevel" to "info",
            "changelogFile" to "db/changelog/master-changelog.xml",
            "classpath" to "src/main/resources",
            "url" to System.getenv("DB_URL"),
            "username" to System.getenv("DB_SUPERUSER_USERNAME"),
            "password" to System.getenv("DB_SUPERUSER_PASSWORD"),
            "driver" to "org.postgresql.Driver",
        )
    }
    runList = "main"
}
