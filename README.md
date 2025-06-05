# 🧠 NeuroFit – ADHD-Focused Fitness Platform

**NeuroFit** is a microservices-based platform designed to help individuals with ADHD build and plan workouts, implementing gamification, notifications, and habit building. 
Built using Java 21, Spring Boot, AWS services, and a modern DevOps toolchain, this project also showcases distributed system architecture.

---

## 🧰 Tech Stack:

- Java 21 + Spring Boot 3.x
- Docker + Docker Compose
- Terraform
- GitHub Actions
- AWS
- LocalStack
- Testcontainers (for integration testing)
- Kafka
- Redis
- MongoDB & PostgreSQL

---

## 🧱 Microservices Architecture (In progress)

```
Key:
🟡 - In progress
🔴 - To be started
🟢 - Complete
```

| Service              | Description |
|----------------------|-------------|
|🔴 **API Gateway**      | Entry point, routing, JWT validation|
|🟡 **User Service**     | Handles registration, login, roles, and profiles|
|🔴 **Workout Service**  | Stores workouts, suggests routines via ML|
|🔴 **Habit Service**    | Tracks daily habits and handles upload of user content|
|🔴 **Gamification**     | Leaderboards and achievement tracking|
|🔴 **Notification**     | Real-time push & mobile notifications|
|🔴 **Analytics**        | Collects and processes usage data |

## High Level Diagram

```
                         +-----------------+
                         |     Client      |
                         | (Web/Mobile App)|
                         +--------+--------+
                                  |
                                  | HTTPS + JWT
                                  v
                       +----------+-----------+
                       |    API Gateway       |  <-- Spring Cloud Gateway
                       | (JWT validation,     |
                       |  routing, rate-limit)|
                       +----+-----------+-----+
                            |           |
        +-------------------+           +--------------------+
        |                                                |
+-------v--------+                               +-------v--------+
| User Service   |                               | Workout Service|
| - Spring Sec   |                               | - MongoDB      |
| - PostgreSQL   |                               | - ML suggestions|
+-------+--------+                               +-------+--------+
        |                                                |
        |                                                |
+-------v--------+                               +-------v--------+
| Habit Service  |                               | Gamification   |
| - Kafka       |                               | - Redis (ZSET) |
| - S3          |                               +-------+--------+
+-------+--------+                                       |
        |                                                |
        |                                                |
+-------v--------+                               +-------v--------+
| Notification   |                               | Analytics      |
| Service       |                                | - AWS Lambda   |
| - WebSocket   |                                | - Prometheus   |
| - FCM         |                                | - DynamoDB/S3  |
+---------------+                                +----------------+

                +------------------------------------------+
                |               Infrastructure              |
                |                                          |
                | - Terraform (AWS infra)                   |
                | - LocalStack (local AWS mocks)            |
                | - Docker + Docker Compose (local dev)     |
                | - GitHub Actions (CI/CD pipeline)         |
                +------------------------------------------+

```

---

## 🚀 Getting Started

### Prerequisites
- Java 21
- Docker + Docker Compose
- Gradle (if using locally)
- Terraform CLI

### Running Locally
```bash
# Spin up local services
docker-compose up --build
````

### Provision AWS Infra (Dev/Prod)

```bash
cd infrastructure/
terraform init
terraform apply
```

---

## 📂 Project Structure

```plaintext
neurofit-platform/
│
├── services/
│   ├── user-service/
│   ├── workout-planner-service/
│   ├── habit-tracker-service/
│   ├── gamification-service/
│   └── notification-service/
│
├── shared-libs/
│   └── common-security/
│   └── common-utils/
│
├── infra/
│   └── terraform/
│       └── aws/
│       └── local-dev/
│
├── docker/
│   ├── docker-compose.yml
│   ├── localstack/
│   └── grafana-prometheus/
│
├── .github/
│   └── workflows/
│       └── ci-cd.yml
│
├── README.md       # You are here
└── arch/
    └── diagrams/

```

---

## 🧪 Testing

* Unit and integration tests per service
* Testcontainers for DB- and Kafka-backed tests
* CI tests via GitHub Actions

---

## 🧭 Roadmap

* [ ] OAuth2 login via Google/Apple
* [ ] Advanced habit analytics dashboard
* [ ] Native mobile app integration
* [ ] ArgoCD for deployment visualization
* [ ] Rate limiting & throttling
* [ ] OpenAPI + Swagger UI docs

---

## 🧑‍💻 Author

**Jimi Ward** – [@JimiWrd](https://github.com/JimiWrd)

---

## 📝 License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
