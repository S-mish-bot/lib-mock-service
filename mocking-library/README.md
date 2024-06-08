# Mocking Library

## Description

This project demonstrates how to mock Java libraries in a Spring Boot application using a Java agent with Byte Buddy.

## Structure

- `app/`: The Spring Boot application
- `agent/`: The Java agent that intercepts and mocks library functions
- `docker-compose.yml`: Docker Compose configuration to run the application and agent

## How to Build and Run

1. Build the Docker images:

    ```bash
    docker-compose build
    ```

2. Start the containers:

    ```bash
    docker-compose up
    ```

3. Test the application by sending a POST request:

    ```bash
    curl -X POST http://localhost:8080/api/createNewPost -H "Content-Type: application/json" -d '{"post_name":"test-post","post_contents":"This is a test post"}'
    ```

## Environment Variables

- `DB_HOST`: Database host
- `DB_PORT`: Database port
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `HT_MODE`: Mode for the Java agent (`RECORD` or `REPLAY`)
