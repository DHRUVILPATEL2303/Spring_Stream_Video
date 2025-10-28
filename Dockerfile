FROM openjdk:21-jdk-slim

WORKDIR /app
COPY . .


EXPOSE 8082

ENTRYPOINT ["java", "-jar", "build/libs/app.jar"]