# 👨🏻‍💻 Workout Service – NeuroFit Platform

This is the **Workout Service** for the NeuroFit platform. It handles exercise and workout management.

---

## 🚀 Features

- REST API for exercise and workout management
- MongoDB persistence of Exercises and Workouts
- 3-Tier architecture (Controller, Service, Repository)
- Dockerized for local and cloud environments
- Testcontainers for integration tests

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.x
- Gradle
- Spring Web
- Spring Data
- MongoDB
- Lombok
- Docker
- Testcontainers

---

## 📦 Getting Started

### Prerequisites

- Java 21
- Docker
- MongoDB / Docker-compose

### Clone and Run

```bash
git clone https://github.com/jimiWrd/workout-service.git
cd workout-service
./gradlew bootRun
```
Or using Docker:

```bash
docker build -t workout-service .
docker run -p 8080:8080 workout-service
```

### 🧪 Running Tests
```bash
./gradlew test
```

🗺️ Roadmap

```
Key:
🟡 - In progress
🔴 - To be started
🟢 - Complete
```
Exercise management - 🟡
Workout creation - 🔴
Exercise search & filtering (pagination, queries) - 🔴
Workout templates - 🔴

<hr>
🧑‍💻 Author
Jimi Ward – @jimiWrd

<hr>
📝 License

MIT License. See LICENSE file.