# Stage 1: Build the application
FROM gradle:8.3-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/test.jar

# Set environment variables
ENV MONGO_PASS=navbarneet
ENV JWT_SECRET=navsecret

ENTRYPOINT ["java", "-jar", "/app/test.jar"]