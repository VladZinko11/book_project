plugins {
    id("java")
}

group = "com.zinko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val junitVersion = "5.9.1"
val jacksonVersion = "2.17.2"
val lombokVersion = "1.18.34"
val springVersion = "6.1.13"
val mapstructVersion = "1.5.5.Final"
val slf4jVersion = "2.0.16"
val log4j2Version = "2.24.0"
val aspectjVersion = "1.9.22.1"
val liquibaseVersion = "4.29.2"
val postgresqlVersion = "42.7.4"
val hikariVersion = "5.1.0"

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-core:$springVersion")
    implementation("org.springframework:spring-aop:$springVersion")
    implementation("org.aspectj:aspectjweaver:$aspectjVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4j2Version")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("org.springframework:spring-jdbc:$springVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
}

tasks.test {
    useJUnitPlatform()
}
