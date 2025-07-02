# 👨🏻‍💻 User Service – NeuroFit Platform

This is the **User Service** for the NeuroFit platform. It handles user profile management.

---

## 🚀 Features

- REST API for user management (Register, Login, Roles)
- Spring Security authentication
- PostgreSQL persistence of Users
- 3-Tier architecture (Controller, Service, Repository)
- Dockerized for local and cloud environments
- Testcontainers for integration tests

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.x
- Gradle
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Lombok
- Docker
- Testcontainers


---

## 📦 Getting Started

### Prerequisites

- Java 21
- Docker
- PostgreSQL / Docker-compose

### Clone and Run

```bash
git clone https://github.com/jimiWrd/user-service.git
cd user-service
./gradlew bootRun
````

Or using Docker:

```bash
docker build -t user-service .
docker run -p 8080:8080 user-service
```

---

## 🔐 Endpoints

| Method | Endpoint | Description            |
| ------ |----------| ---------------------- |
| POST   | `/users` | Register a new user    |

---

## 🧪 Running Tests

```bash
./gradlew test
```

## 🗺️ Roadmap

```markdown
Key:
🟡 - In progress
🔴 - To be started
🟢 - Complete
```

* User creation - 🟡
* Account management - 🔴
* Rate limiting & brute-force protection - 🔴
* Audit logging - 🔴

--- 
## 🧑‍💻 Author

**Jimi Ward** – [@jimiWrd](https://github.com/jimiWrd)

---

## 📝 License

MIT License. See `LICENSE` file.

