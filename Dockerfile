FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install gradle -y
FROM gradle
RUN gradle clean build

From openjdk:17-slim
COPY --from=build /build/libs/PaymentAccounts-0.0.1-SNAPSHOT.jar app.jar
expose 8080
ENTRYPOINT ["java", "-jar ","app.jar"]
