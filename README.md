# Task Manager

Spring Boot application to create the backend for the basic Task Manager application.

## Tech stack
- Java (recommended: Java 17+)
- Spring Boot
- Maven
- Runs on Linux / developed with IntelliJ IDEA

## Prerequisites
- JDK 17+ installed
- Maven 3.6+
- Git
- Docker (using PostGreSQL Image for dB)

# Architecture Overview

## High-level diagram
com.example.task Spring Boot app (single service)
Client/API Consumer -> HTTP -> Controller -> Service -> Repository -> Database
(Optional) External services -> HTTP / Messaging -> Service

## Key components
1. Application
    1. Spring Boot entrypoint: `com.example.task.TaskApplication`
    2. Runs as a standalone JAR or via `mvn spring-boot:run`
2. Presentation
    1. REST Controllers handle HTTP requests and return DTOs
3. Business Logic
    1. Service layer implements use-cases and orchestrates repositories and external calls
4. Persistence
    1. Repository layer (Spring Data JPA or manual DAOs) persists `Entity` objects to a relational DB (e.g., PostgreSQL)
    2. Relevant code in `src/main/java/com/example/task/...`
5. Configuration
    1. `src/main/resources/application.properties` or `application.yml` manages profiles, ports, DB URLs and credentials
6. Integration
    1. External HTTP APIs, message brokers, or caches are invoked from the service layer via client components

## Data flow (request lifecycle)
1. Client sends HTTP request to Controller.
2. Controller validates input and maps to domain DTOs.
3. Controller calls Service to perform business logic.
4. Service invokes Repository for persistence or external clients for integrations.
5. Repository returns entities; Service maps results to DTOs.
6. Controller returns HTTP response.

## Package / file conventions
1. Main package: `com.example.task`
2. Source layout: `src/main/java/com/example/task/...`
3. Config & resources: `src/main/resources/application.properties` (or `application.yml`)
4. Tests: `src/test/java/...`

## Deployment & build
1. Build: `mvn clean package`
2. Run (dev): `mvn spring-boot:run` or `java -jar target/*.jar`
3. Optional container: Docker image built from the generated JAR
