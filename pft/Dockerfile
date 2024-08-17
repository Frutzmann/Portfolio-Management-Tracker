# Utilisez une version stable d'OpenJDK
FROM eclipse-temurin:22 AS build

#RUN apt-get update && \
#   apt-get install -y maven && \
#   rm -rf /var/lib/apt/lists/*
# Définissez le répertoire de travail
#WORKDIR /app

#COPY pom.xml .
#COPY src ./src

#Build maven .jar project
#RUN mvn clean install

FROM eclipse-temurin:22-alpine
WORKDIR /app
COPY ./target/*.jar /app/pft.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/pft.jar"]
