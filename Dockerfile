FROM maven:3.9.9-eclipse-temurin-21 AS builder
COPY . /app
WORKDIR /app
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21 AS prod
COPY --from=builder /app/target/*.jar /app/wallet-api.jar
ENV SERVER_PORT=8081
WORKDIR /app
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/wallet-api.jar"]