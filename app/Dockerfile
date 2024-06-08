FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY ../agent/target/agent.jar /app/agent.jar
ENTRYPOINT ["java", "-javaagent:/app/agent.jar", "-jar", "/app/app.jar"]