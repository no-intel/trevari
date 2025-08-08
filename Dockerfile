# 1. Gradle 빌드 단계
FROM gradle:8.8-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# 2. 실행 단계
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar trevari.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "trevari.jar"]