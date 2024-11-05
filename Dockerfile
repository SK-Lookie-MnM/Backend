# Base image with JDK 17
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /application

# Copy the built JAR file into the container
COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
# Set timezone and active profile, then run the JAR
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
