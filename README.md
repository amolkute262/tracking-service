
# 📦 Parcel Tracking Number Generator App (`tracking-service`)

A reactive Spring Boot 3.2+ application using WebFlux and Redis, designed to generate unique parcel tracking numbers efficiently in a distributed environment. Built with production-grade observability, high-throughput, test coverage, and validation.

---

## 🚀 Features

- Generate unique parcel tracking numbers
- Built using **Spring WebFlux** (Reactive)
- Redis for distributed caching
- RESTful APIs with reactive types
- Prometheus-compatible metrics via Spring Boot Actuator + Micrometer
- Input validation via `jakarta.validation`
- Code coverage reports using **JaCoCo**

---

## ⚙️ Tech Stack

| Technology             | Version      |
|------------------------|--------------|
| Java                   | 21           |
| Spring Boot            | 3.2.5        |
| Spring WebFlux         | latest       |
| Redis Reactive         | latest       |
| Hibernate Validator    | 8.x          |
| Jakarta EL             | 4.0.2        |
| Micrometer + Prometheus| latest       |
| JaCoCo                 | 0.8.11       |

---

## 📦 Installation

### Prerequisites

- Java 21+
- Maven 3.8+
- Redis Server (Running locally or remotely)

---

## 📁 Project Structure

```
tracking-service/
│
├── src/main/java/com/getrosoft/
│   ├── controller/
│   ├── exception/
│   ├── model/
│   └── service/
│
├── src/test/java/...
├── application.yml
└── pom.xml
```

---

## 🧪 Running the App

### 1. 📥 Clone the Repo

```bash
git clone https://github.com/amolkute262/tracking-service.git
cd tracking-service
```

### 2. 🧱 Run Redis Locally

#### Option A: 🐳 Docker (Recommended)

```bash
docker run --name redis-dev -p 6379:6379 -d redis
```

#### Option B: Manual Install

- Ubuntu: `sudo apt install redis-server`
- macOS: `brew install redis`
- Windows: Use WSL or install from [Redis Releases](https://github.com/microsoftarchive/redis/releases)

Test with:

```bash
redis-cli ping  # should return PONG
```

---

### 3. ▶️ Run Spring Boot App

```bash
mvn spring-boot:run
```

Or run the generated JAR:

```bash
java -jar target/tracking-service.jar
```

App will start on `http://localhost:8080`

#### Sample Request

```bash
GET /api/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics
```

#### Sample Response

```bash
{
    "tracking_number": "U7FAJ824YTF3JKN2",
    "created_at": "2025-06-10T14:10:10.6775374+05:30"
}
```

---

## 🧪 Running Tests + Code Coverage

```bash
mvn test
```

To generate code coverage report:

```bash
mvn verify
```

View the report at:

```
target/jacoco-report/index.html
```

---

## 🔌 REST Endpoints

> Replace these with your actual endpoint definitions.

| Method | Endpoint           | Description                      |
|--------|--------------------|----------------------------------|
| GET    | `/api/next-tracking-number` | Generate a new tracking number   |
| GET    | `/actuator/health` | Health check                     |
| GET    | `/actuator/prometheus` | Prometheus metrics           |

---

## 📊 Monitoring with Prometheus

The app exposes metrics at:

```
http://localhost:8080/actuator/prometheus
```

Use this in your `prometheus.yml` configuration:

```yaml
- job_name: 'tracking-service'
  metrics_path: '/actuator/prometheus'
  static_configs:
    - targets: ['localhost:8080']
```

---

## ✅ Validation

Bean Validation is enabled using:

- `jakarta.validation-api`
- `hibernate-validator`
- `jakarta.el` for EL expression support

**Common annotations used:**

- `@NotNull`
- `@NotBlank` (for `String` only)
- `@DecimalMin`, `@Size`, etc.

> ⚠️ Make sure to use validation annotations compatible with field types!

---

## 👨‍💻 Author

- Amol Kute ([amolkute262](https://github.com/amolkute262))