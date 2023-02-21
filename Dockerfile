FROM openjdk:17-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar /app/takeHome.jar
ENTRYPOINT ["java", "-jar", "/app/takeHome.jar"]