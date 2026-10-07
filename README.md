# Internship Backend - Spring Boot

This repository contains my daily backend tasks (Day 1-8) and my final internship project (Day 9-10) built with Spring Boot. It covers everything from basic REST APIs to advanced concepts like Kafka, OAuth2, Docker, and CI/CD.

## 🚀 Tech Stack
- **Language:** Java 17+
- **Frameworks:** Spring Boot, Spring Security, Spring Data JPA, Spring Batch, Spring Kafka
- **Database:** MySQL
- **Security:** JWT, OAuth2 (Google)
- **Real-time:** WebSockets, Server-Sent Events (SSE)
- **Messaging:** Apache Kafka
- **Caching:** Caffeine, Redis
- **Testing:** JUnit, Mockito, Spring Security Test
- **DevOps:** Docker, GitHub Actions
- **Docs:** Swagger / OpenAPI

---

## 📅 Day 1: Spring Boot Setup & REST Fundamentals

**Tasks Completed:**
- Installed and set up Spring Boot environment.
- Learned REST API concepts (HTTP methods, status codes, resource design).
- Created Postman Collection for API testing.
- Wrote API Specification document for a Book API.
- Set up MySQL and executed basic SQL queries.

**Files:** `INTERNSHIP DAY 1 BOOKS.sql`, `task2-rest-concepts.docx`, `task3-postman-collection.json`, `task4-api-specification.odt`

---

## 📅 Day 2: Spring Core, Dependency Injection & Basic CRUD

**Tasks Completed:**
- Studied Spring Core Concepts (IoC Container, Beans, Application Context).
- Implemented three types of Dependency Injection:
  - Constructor Injection
  - Setter Injection
  - Field Injection
- Configured MySQL datasource in `application.properties`.
- Created `Book` entity with JPA annotations.
- Implemented full CRUD REST endpoints (`GET`, `POST`, `PUT`, `DELETE`).
- Added DTOs (`BookRequestDTO`, `BookResponseDTO`) and Mapper classes.

**Files:** `Spring Core Concepts.docx`, `Dependency Injection Approaches.docx`, `books-api-source/`

---

## 📅 Day 3: Transactions, Repositories & Security

**Tasks Completed:**
- Studied Transactions and Repository patterns.
- Implemented JPA Repositories with custom query methods.
- Learned about Spring Security Filter Chain.
- Implemented JWT-based authentication:
  - `JwtUtil` for token generation/validation
  - `JwtFilter` for request interception
  - `SecurityConfig` for route protection
  - `CustomUserDetailsService` for user lookup
- Created `AuthController` for login/register.

**Files:** `Task 3 - Transactions and Repositories.docx`, `Task 11 - Spring Security Filter Chain.docx`, `books-api-source/`

---

## 📅 Day 4: OAuth2, Profiles, Batch & Schedulers

**Tasks Completed:**
- Studied OAuth2 concepts and flow.
- Implemented **Google OAuth2 Login**.
- Configured Spring Profiles (`application-dev.properties`, `application-prod.properties`).
- Learned Spring Batch for batch processing.
- Implemented task scheduling using `@Scheduled`.
- Created Swagger/OpenAPI documentation.

**Files:** `OAuth2 Concepts.docx`, `Google OAuth2 Implementation.docx`, `Spring Batch.docx`, `Spring Profiles.docx`

---

## 📅 Day 5: Caching, Audit Logging & Testing

**Tasks Completed:**
- Studied caching strategies and concepts.
- Compared **Caffeine** (in-memory) vs **Redis** (distributed) caching.
- Implemented caching with `@Cacheable`, `@CachePut`, `@CacheEvict`.
- Added Audit Logging with a scheduled Poller.
- Wrote Unit Tests using **JUnit** and **Mockito**.
- Wrote Security Tests for controllers.

**Files:** `Task 1 - Cache Concepts.docx`, `Task 2 - Caffeine vs Redis.docx`, `Task 3-4 - Caching Implementation.docx`

---

## 📅 Day 6: Real-time Communication & External Services

**Tasks Completed:**
- Studied and implemented **Firebase Cloud Messaging (FCM)** for push notifications.
- Implemented **Server-Sent Events (SSE)** for one-way real-time updates.
- Used **RestClient** for calling external APIs.
- Implemented **WebSockets** for two-way real-time communication.
- Built event-driven flow with `BookCreatedEvent` and `BookEventListener`.

**Files:** `Task 43 - FCM.docx`, `books-api-source/`

---

## 📅 Day 7: Monitoring, Docker & CI/CD

**Tasks Completed:**
- Advanced WebSocket implementation with `WebSocketConfig` and `WebSocketController`.
- Integrated **Spring Boot Actuator** for health checks and monitoring.
- Created **Dockerfile** for containerizing the application.
- Added `.dockerignore` for optimized builds.
- Set up **CI/CD pipeline** using GitHub Actions (`.github/workflows/ci.yml`).
- Learned about deployment workflows.

**Files:** `Task 44 - WebSockets.docx`, `Task 46 - CI-CD.docx`, `Task 47 - Spring Boot Actuator.docx`, `Dockerfile`, `.github/workflows/ci.yml`

---

## 📅 Day 8: Apache Kafka & Microservices

**Tasks Completed:**
- Studied **Apache Kafka Fundamentals** (topics, partitions, brokers, producers, consumers).
- Implemented **Spring Kafka Integration**:
  - `KafkaConfig` for producer/consumer setup
  - `KafkaProducerService` for publishing events
  - `KafkaConsumerService` for consuming events
  - `KafkaController` for exposing endpoints
- Created `BookEventDTO` for message payloads.
- Studied **Spring Microservices Architecture** (service discovery, API gateway, distributed tracing).

**Files:** `Task 48 - Apache Kafka Fundamentals.docx`, `Task 49 - Spring Kafka Integration.docx`, `Task 50 - Spring Microservices Architecture.docx`, `books-api-source/`

---

# 🏗️ Final Project

## 🎓 Student Management System (Day 9 & 10)

My final internship project — a complete backend system for managing students with modern architecture, real-time features, and secure authentication.

**Features:**
- **Full CRUD REST API** for managing student records.
- **Security:** JWT-based authentication with custom `JwtFilter`, `JwtUtil`, and `SecurityConfig`.
  - `AuthController` for login/register.
  - `CustomUserDetailsService` for user lookup.
  - `User` entity + `UserRepository`.
- **Event-driven architecture:**
  - `StudentCreatedEvent` published on student creation.
  - `StudentEventListener` handles the event asynchronously.
- **Real-time updates:**
  - WebSocket integration (`WebSocketConfig`, `WebSocketController`).
  - Live dashboard at `dashboard.html` reflecting student changes in real time.
- **Task Scheduling:**
  - `StudentScheduler` for periodic background operations.
- **Exception Handling:**
  - `GlobalExceptionHandler` for consistent error responses.
- **DTO Pattern:**
  - `StudentRequestDTO`, `StudentResponseDTO`, `AuthRequestDTO`, `AuthResponseDTO`.
- **API Documentation:** Swagger / OpenAPI integration.
- **Database:** MySQL with Spring Data JPA.

**Folder:** `project student management/student-management/`

**Project Structure:**
