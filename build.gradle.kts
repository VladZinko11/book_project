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

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
