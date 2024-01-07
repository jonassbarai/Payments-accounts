FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install gradle -y
RUN gradle clean build --rerun-tasks --no-build-cache

From openjdk:17-slim

expose 8080

COPY --from=build /build/libs/PaymentAccounts-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar ","app.jar"]
