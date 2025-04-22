FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]
