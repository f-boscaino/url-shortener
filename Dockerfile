FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-oracle
WORKDIR /app
COPY --from=builder /app/target/shortener.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shortener.jar"]