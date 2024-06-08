FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/mocking-library-0.0.1-SNAPSHOT app.jar
ENTRYPOINT ["java", "-javaagent:/app/agent.jar", "-jar", "/app.jar"]
