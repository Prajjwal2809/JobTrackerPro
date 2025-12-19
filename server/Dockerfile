# ---- Build stage ----
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# install maven (because mvnw is not in /server)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

COPY . .
RUN mvn -v
RUN mvn clean package -DskipTests

# ---- Run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
