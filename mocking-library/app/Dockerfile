## app/Dockerfile
#
## Stage 1: Build the application JAR
#FROM maven:3.8.4-openjdk-11-slim AS build-app
#WORKDIR /app
#COPY ./app/pom.xml /app/pom.xml
#COPY ./app/src /app/src
#RUN mvn dependency:go-offline
#COPY ./app /app
#RUN mvn clean package -DskipTests
#
## Stage 2: Prepare the runtime environment
#FROM openjdk:11-jdk-slim AS runtime
#VOLUME /tmp
#
#COPY --from=build-app /app/target/mocking-library-0.0.1-SNAPSHOT.jar /app.jar
#COPY --from=mocking-library-agent:latest /opt/agent/mocking-library-agent.jar /opt/agent/mocking-library-agent.jar
#
#ENTRYPOINT ["java","-javaagent:/opt/agent/mocking-library-agent.jar","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
