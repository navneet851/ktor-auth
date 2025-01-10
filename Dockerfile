FROM openjdk:11-jre-slim
WORKDIR /app
COPY ./build/libs/test.jar /app/test.jar
CMD ["java", "-jar", "/app/test.jar"]
