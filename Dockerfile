# 1) Build con Maven + OpenJDK 17 slim
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app

# Copiar el POM y descargar dependencias\ CYCLE
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar el código fuente y empaquetar como uber-jar para incluir Main-Class
COPY src ./src
RUN mvn clean package -DskipTests -Dquarkus.package.type=uber-jar -B

# 2) Runtime con OpenJDK 17 slim
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app

# Copiar el uber-jar generado (tendrá manifest con Main-Class)
COPY --from=builder /app/target/*-runner.jar app.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Punto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]