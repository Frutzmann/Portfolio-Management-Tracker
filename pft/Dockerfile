# Utilisez une version stable d'OpenJDK
FROM eclipse-temurin:22 AS build

FROM eclipse-temurin:22-alpine

WORKDIR /app

COPY ./target/*.jar /app/pft.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/pft.jar"]