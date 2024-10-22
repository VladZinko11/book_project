FROM gradle:8.0.0-jdk17 AS build
LABEL authors="Vlad"

RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
COPY gradlew ./

RUN ./gradlew

COPY src ./src

RUN ./gradlew bootJar --no-daemon --info

FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java",  "-jar", "app.jar"]
