# First stage: Build with Maven
FROM maven:3.8.5-openjdk-17 AS MAVEN_BUILD

# Set the working directory
COPY ./ ./

# Package the application
RUN mvn clean package

FROM --platform=linux/arm64/v8  openjdk:17 AS build

# Copy the built jar from the first stage
COPY --from=MAVEN_BUILD ./target/libmocker-0.0.1-SNAPSHOT.jar /app.jar

# Copy the agent.jar from the first stage
#COPY --from=MAVEN_BUILD ./target/agent.jar agent.jar
COPY /agent.jar /agent.jar

# Set the startup command to execute the jar with the java agent
ENTRYPOINT ["java", "-javaagent:/agent.jar", "-jar", "/app.jar"]