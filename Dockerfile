FROM maven:3.6.3-openjdk-11-slim AS build-stage
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn verify -Ptest

FROM amazoncorretto:11.0.21-alpine AS run-stage
RUN addgroup -S item-tracker && adduser -S admin -G item-tracker 
USER admin:item-tracker
WORKDIR home/admin
COPY --from=build-stage target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
