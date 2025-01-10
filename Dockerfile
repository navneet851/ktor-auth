# Use the official Kotlin image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the build.gradle.kts and settings.gradle.kts files
COPY build.gradle.kts settings.gradle.kts ./

# Copy the gradle folder
COPY gradle ./gradle

# Copy the gradlew files
COPY gradlew ./

# Copy the source code
COPY src ./src

# Copy the resources
COPY src/main/resources ./src/main/resources

# Install dependencies and build the application
RUN ./gradlew build

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/test.jar"]