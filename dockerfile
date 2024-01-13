FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install -y openjdk-17-jdk

COPY . .

RUN chmod +x ./gradlew && \
    ./gradlew clean bootJar --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
