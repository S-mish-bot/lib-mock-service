FROM openjdk:17-jdk-slim
COPY app/target/app-1.0-SNAPSHOT.jar /app/app.jar
COPY agent/target/agent-1.0-SNAPSHOT-jar-with-dependencies.jar /app/agent.jar
#COPY app/src/main/resources/application.yml /app/application.yml
ENV HT_MODE=RECORD
ENTRYPOINT ["java", "-javaagent:/app/agent.jar", "-jar", "/app/app.jar"]
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]