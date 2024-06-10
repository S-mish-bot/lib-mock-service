# Lib Mock Service

Lib Mock Service is a Spring Boot application designed to facilitate mocking of Java libraries. This service enables you to override the functionality of Java libraries at runtime without changing the business logic code. It operates in two modes: RECORD and REPLAY, which allow you to record real outbound HTTP calls and database interactions and then replay them with hardcoded values.

## Features

- Record Mode: Logs real outbound HTTP calls and database interactions.
- Replay Mode: Replays recorded HTTP calls and database interactions with hardcoded values.
- Uses Byte Buddy for runtime patching and manipulation of library functions.
- Dockerized setup for easy deployment and testing.

## Project Structure

.
└── mocking-library
    ├── agent
    │   └── src
    │       └── main
    │           ├── java
    │           │   └── com
    │           │       └── example
    │           │           └── mockinglibrary
    │           │               └── agent
    │           └── resources
    │               └── META-INF
    └── app
        └── src
            └── main
                ├── java
                │   └── com
                │       └── example
                │           └── mockinglibrary
                │               ├── controller
                │               ├── entity
                │               └── repository
                └── resources
