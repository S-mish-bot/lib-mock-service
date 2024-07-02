
FROM maven:3.8.4-openjdk-11-slim AS build-agent
WORKDIR /build-agent
COPY agent/pom.xml /build-agent/pom.xml
COPY agent/src /build-agent/src
RUN mvn clean package

# Build the application JAR
FROM maven:3.8.4-openjdk-11-slim AS build-app
WORKDIR /app
COPY app/pom.xml /app/pom.xml
COPY app/src /app/src
COPY app /app

# Prepare the runtime environment
FROM openjdk:11-jdk-slim
VOLUME /opt/agent
VOLUME /tmp


COPY --from=build-agent /build-agent/target/mocking-library-agent-0.0.1-SNAPSHOT.jar /opt/agent/mocking-library-agent.jar


COPY --from=build-app /app/target/mocking-library-0.0.1-SNAPSHOT.jar /app.jar


ENTRYPOINT ["java","-javaagent:/opt/agent/mocking-library-agent.jar","-jar","/app.jar"]
