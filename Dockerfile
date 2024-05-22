FROM gradle:7.5-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY . .
RUN gradle build

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]