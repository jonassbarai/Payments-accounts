FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN ./gradlew bootJar --no-daemon

From openjdk:17-slim
expose 8080
COPY --from=build /build/libs/PaymentAccounts-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar ","app.jar"]
