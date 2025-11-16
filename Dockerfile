# stage 1: build the application
FROM gradle:8.5-jdk21 AS build

WORKDIR /app

# copy gradle files
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# copy source code
COPY src ./src

# build the application
RUN gradle build -x test --no-daemon

# stage 2: create run time image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# copy the build jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

# run the application
ENTRYPOINT ["java", "-jar", "app.jar"]