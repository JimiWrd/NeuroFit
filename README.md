# 🧠 NeuroFit – ADHD-Focused Fitness Platform

**NeuroFit** is a platform aimed at neurodivergent fitness enthusiasts, designed to support personalised workout planning; utilising gamification features, habit tracking, and notifications. The platform is designed to be cloud-native, implementing a distributed system architecture using Java 21, Spring Boot, and a suite of AWS services, orchestrated through a modern DevOps pipeline.

---

## 🧰 Tech Stack:

- Java 21 + Spring Boot 3.x
- Docker + Docker Compose
- Keycloak
- Terraform
- GitHub Actions
- AWS
- LocalStack
- Testcontainers (for integration/mock testing)
- Kafka
- Redis
- MongoDB & PostgreSQL

---

## 🧱 Platform Architecture (In progress)

```
Key:
🟡 - In progress
🔴 - To be started
🟢 - Complete
```

| Service                | Description |
|------------------------|-------------|
|🟡 **App Gateway**      | Entry point, routing, JWT validation against Keycloak|
|🟡 **Eureka Server**    | Eureka server for service registration and discovery|
|🟡 **Keycloak**         | Authentication, user management, JWT provision|
|🟡 **User Service**     | Handles user specific operations i.e. user data|
|🔴 **Workout Service**  | Stores workouts, suggests routines via ML|
|🔴 **Habit Service**    | Tracks daily habits and handles upload of user content|
|🔴 **Gamification**     | Leaderboards and achievement tracking|
|🔴 **Notification**     | Real-time push & email notifications|
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
                       |      API Gateway     |  <-- Spring Cloud Gateway
                       |   (Login/Create User |    
                       |     via keycloak,    |
                       |  rate limit, routing |
                       +----+-----------+-----+
                            |           |
        +-------------------+           +--------------------+
        |                                                    |
+-------v--------+                                   +-------v---------+
| User Service   |                                   | Workout Service |
| - Spring Sec   |                                   | - MongoDB       |
| - PostgreSQL   |                                   | - ML suggestions|
+-------+--------+                                   +-------+---------+
        |                                                    |
        |                                                    |
+-------v--------+                                   +-------v--------+
| Habit Service  |                                   | Gamification   |
| - Kafka        |                                   | - Redis (ZSET) |
| - S3           |                                   +-------+--------+
+-------+--------+                                           |
        |                                                    |
        |                                                    |
+-------v-------+                                    +-------v--------+
| Notification  |                                    | Analytics      |
| Service       |                                    | - AWS Lambda   |
| - WebSocket   |                                    | - Prometheus   |
| - FCM         |                                    | - DynamoDB/S3  |
+---------------+                                    +----------------+

                +------------------------------------------+
                |               Infrastructure             |
                |                                          |
                | - Terraform (AWS infra)                  |
                | - LocalStack (local AWS mocks)           |
                | - Docker + Docker Compose (local dev)    |
                | - GitHub Actions (CI/CD pipeline)        |
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

### Provision AWS Infra (Dev/Prod) - IN PROGRESS

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
├── gateway/                
│   └── api-gateway/
│
├── services/
│   ├── user-service/
│   ├── workout-planner-service/
│   ├── habit-tracker-service/
│   ├── gamification-service/
│   └── notification-service/
│
├── shared-libs/
│   ├── common-security/
│   └── common-utils/
│
├── infra/
│   └── terraform/
│       ├── aws/
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
├── README.md     <-- You are here
└── arch/
    └── diagrams/
```

---

## 🧪 Testing

* Unit tests for service logic
* Mock tests for integration - using TestContainers, WireMock, Docker, etc
* CI tests via GitHub Actions

---

## 🧭 Roadmap

These are some additional features/goals for this project long term, loosely defined currently.

* [ ] OpenAPI docs
* [ ] Rate limiting & throttling
* [ ] Mobile application
* [ ] ArgoCD for deployment visualization and observability
* [ ] An analytics dashboard

---

## 🧑‍💻 Author

**Jimi Ward** – [@JimiWrd](https://github.com/JimiWrd)

---

## 📝 License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
