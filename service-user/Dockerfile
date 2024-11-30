FROM gradle:8.0.0-jdk17 AS build
LABEL authors="Vlad"

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
COPY gradlew ./

RUN ./gradlew

COPY src ./src

RUN ./gradlew bootJar --no-daemon --info

FROM openjdk:17-jre-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java",  "-jar", "app.jar"]
