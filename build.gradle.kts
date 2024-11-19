plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"

}

group = "com.zinko"
version = "1.0-SNAPSHOT"


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
}

repositories {
    mavenCentral()
}
val mapstructVersion = "1.6.2"
val liquibaseVersion = "4.29.2"
val hibernateVersion = "6.6.1.Final"
val ehcacheVersion = "3.10.8"
val testcontainersVersion = "1.20.3"

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation ("org.testcontainers:junit-jupiter:$testcontainersVersion")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.hibernate.orm:hibernate-jcache:$hibernateVersion")
    implementation("org.ehcache:ehcache:$ehcacheVersion")

    runtimeOnly("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    compileOnly("org.mapstruct:mapstruct:$mapstructVersion")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
