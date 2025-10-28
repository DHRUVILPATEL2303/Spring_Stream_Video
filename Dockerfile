FROM openjdk:17-jdk-slim

WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "build/libs/app.jar"]