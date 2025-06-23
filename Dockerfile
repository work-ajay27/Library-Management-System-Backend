# Use official OpenJDK image as base
FROM openjdk:17-jdk-slim

# Copy the JAR file from the target folder to the container
COPY target/*.jar app.jar

# Expose the default Spring Boot port (Railway maps PORT env variable)
EXPOSE 8080

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app.jar"]
