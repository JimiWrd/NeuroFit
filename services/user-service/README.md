# 👨🏻‍💻 User Service – NeuroFit Platform

This is the **User Service** for the NeuroFit platform. It handles user registration, authentication, and profile management.

---

## 🚀 Features

- REST API for user management (Register, Login, Roles)
- Spring Security authentication
- JWT token generation & validation
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
- Spring Security (to be added)
- Spring Data JPA (to be added)
- PostgreSQL (to be added)
- Lombok
- Docker (to be added)
- Testcontainers (to be added)


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

| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| POST   | `/auth/register` | Register a new user    |
| POST   | `/auth/login`    | Authenticate & get JWT |
| GET    | `/me`            | Get current user info  |

(*These are preliminary endpoints, they may change as the service grows and evolves to fit requirements.*)

---

## 🧪 Running Tests

```bash
./gradlew test
```

(*Testcontainers integration coming soon*)

---

## 📄 Environment Variables

| Variable      | Description             |
| ------------- | ----------------------- |
| `DB_URL`      | PostgreSQL URL          |
| `JWT_SECRET`  | Secret for signing JWTs |
| `SERVER_PORT` | Port (default: 8080)    |

---

## 🗺️ Roadmap

```markdown
Key:
🟡 - In progress
🔴 - To be started
🟢 - Complete
```

* User Creation - 🟡
* Login - 🔴
* Email verification - 🔴
* Password reset - 🔴
* Rate limiting & brute-force protection - 🔴
* Audit logging - 🔴
* OAuth2 support - 🔴

--- 
## 🧑‍💻 Author

**Jimi Ward** – [@jimiWrd](https://github.com/jimiWrd)

---

## 📝 License

MIT License. See `LICENSE` file.

