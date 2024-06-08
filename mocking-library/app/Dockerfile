# Stage 1: Build the application JAR
FROM maven:3.8.4-openjdk-17-slim AS build-app
WORKDIR /app
COPY ./app /app
RUN mvn clean package

# Stage 2: Prepare the runtime environment
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY --from=build-app /app/target/mocking-library-0.0.1-SNAPSHOT.jar app.jar

# Use the shared volume to access the agent JAR
#COPY /opt/agent/mocking-library-agent-0.0.1-SNAPSHOT.jar /opt/agent/mocking-library-agent.jar

ENTRYPOINT ["java","-javaagent:/opt/agent/mocking-library-agent.jar","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]