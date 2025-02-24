plugins {
    `java-library`
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.liquibase.gradle") version "2.2.0"
}

group = "me.maxhub.hercules"
version = "0.0.1"

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
val springBootVersion = "3.4.2"
val springDocVersion = "2.8.5"

// Jackson Databind
val jacksonVersion = "2.18.2"

// Mapping
val mapstructVersion = "1.6.3"

// Database
val postgresVersion = "42.7.5"

// Liquibase
val liquibaseVersion = "4.31.0"
val picocliVersion = "4.7.6"

// Lombok
val lombokVersion = "1.18.36"
val lombokMapstructBindingVersion = "0.2.0"

// Testing
val h2Version = "2.3.232"

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
    developmentOnly("org.springframework.boot:spring-boot-devtools:$springBootVersion")

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
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    liquibaseRuntime("org.liquibase:liquibase-core:$liquibaseVersion")
    liquibaseRuntime("org.postgresql:postgresql:$postgresVersion")
    liquibaseRuntime("info.picocli:picocli:$picocliVersion")

    // Lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("com.h2database:h2:$h2Version")
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
