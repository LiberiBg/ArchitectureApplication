FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# Copie TOUT d'abord pour Maven complet
COPY pom.xml mvnw mvnw.cmd ./
COPY .mvn .mvn
COPY src ./src

# UN SEUL package après tous les fichiers
RUN ./mvnw clean package -DskipTests -Pprod

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
