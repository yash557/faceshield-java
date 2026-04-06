# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copies the compiled .jar file
COPY --from=build /app/target/*.jar app.jar

# THE FIX: Copy the model folder to the server's physical file system
# so Paths.get("src/main/resources/model") can find it!
COPY --from=build /app/src/main/resources/model ./src/main/resources/model

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]