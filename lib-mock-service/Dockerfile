# First stage: Build with Maven
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the parent pom.xml first
COPY ./pom.xml ./libmocker-parent/

# Copy the child module pom.xml files
COPY ./agent/pom.xml ./libmocker-parent/agent/
COPY ./application/pom.xml ./libmocker-parent/application/

# Copy the source code of the modules
COPY ./agent/src ./libmocker-parent/agent/src/
COPY ./application/src ./libmocker-parent/application/src/

# Build the project
RUN mvn clean install -f libmocker-parent/pom.xml

# Stage 2: Create the runtime image
FROM openjdk:17

# Copy the built jar files from the first stage
COPY --from=build /app/libmocker-parent/agent/target/agent-0.0.1-SNAPSHOT.jar /agent.jar
COPY --from=build /app/libmocker-parent/agent/target/agent.jar /agent.jar
COPY --from=build /app/libmocker-parent/application/target/libmocker-application-0.0.1-SNAPSHOT.jar /app.jar
COPY application/src/main/resources/application.yml /application.yml
COPY application/src/main/resources/application-dev.yml /application-dev.yml

ENV HT_MODE=RECORD

# Set the startup command to execute the jar with the java agent
ENTRYPOINT ["java", "-javaagent:/agent.jar", "-jar", "/app.jar"]
