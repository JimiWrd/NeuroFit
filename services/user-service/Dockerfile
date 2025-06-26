# Build stage
FROM amazoncorretto:21 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon

# Runtime stage
FROM amazoncorretto:21
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]