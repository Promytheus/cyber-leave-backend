# Cyber Leave Backend

HR Presence Management Backend built with **Spring Boot 4 (4.1.1)** and **OpenJDK 25 (LTS)**.

---

## 🛠 Tech Stack

- **Java**: OpenJDK 25 (Eclipse Adoptium Temurin 25.0.4.1 LTS)
- **Framework**: Spring Boot 4.1.1
  - `spring-boot-starter-webmvc` (Spring Framework 7)
  - `spring-boot-starter-data-jpa` (Hibernate ORM 7)
  - `spring-boot-starter-validation` (Jakarta Validation)
  - `spring-boot-starter-security`
  - `spring-boot-starter-actuator`
  - `spring-boot-h2console`
  - `tools.jackson` (Jackson 3)
- **Database**: H2 In-Memory (`jdbc:h2:mem:cyberleave`)
- **Build Tool**: Apache Maven with Maven Wrapper (`./mvnw.cmd` / `./mvnw`)

---

## 🚀 Getting Started

### Prerequisites
- **Java 25**: Eclipse Adoptium Temurin 25 (installed at `C:\Program Files\Eclipse Adoptium\jdk-25.0.4.101-hotspot`)
- Ensure `JAVA_HOME` points to your JDK 25 directory.

### Build and Test
```powershell
.\mvnw.cmd clean test
```

### Run Application
```powershell
.\mvnw.cmd spring-boot:run
```
The server will start on port `8080` (`http://localhost:8080`).

---

## 📡 REST API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/leaves` | List all leave requests (optional `?status=PENDING` filter) |
| `GET` | `/api/v1/leaves/{id}` | Get leave request details by ID |
| `GET` | `/api/v1/leaves/employee/{employeeId}` | Get all leave requests for an employee |
| `POST` | `/api/v1/leaves` | Create a new leave request |
| `PATCH` | `/api/v1/leaves/{id}/status` | Update leave status (`APPROVED`, `REJECTED`, etc.) |
| `DELETE` | `/api/v1/leaves/{id}` | Delete a leave request |

### API Documentation & Interactive UI
- **Swagger UI**: `GET http://localhost:8080/swagger-ui/` (also redirected from `/swagger-ui.html` and `/swagger-ui`)
- **OpenAPI Specification**: `GET http://localhost:8080/openapi.yaml` (also located at repo root [`openapi.yaml`](file:///c:/Workspaces/cyber-leave-backend/openapi.yaml))

### Monitoring & Management
- **Health Check**: `GET http://localhost:8080/actuator/health`
- **Actuator Root**: `GET http://localhost:8080/actuator`
- **H2 Web Console**: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:cyberleave`
  - User Name: `sa`
  - Password: *(leave blank)*

---

## 📋 Sample Request

### Create a Leave Request
```http
POST /api/v1/leaves
Content-Type: application/json

{
  "employeeId": "EMP-104",
  "employeeName": "Jane Doe",
  "leaveType": "ANNUAL",
  "startDate": "2026-11-01",
  "endDate": "2026-11-05",
  "reason": "Family vacation"
}
```
