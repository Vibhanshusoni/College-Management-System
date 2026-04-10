# College Management System - Microservices

## 🔧 Project Overview

A Spring Boot microservices-based College Management System with the following services:
- **API-Gateway** (Port 8080) - Routes requests to microservices
- **Discovery-Server** (Port 8761) - Eureka server for service discovery
- **Auth-Service** (Port 8083) - Authentication and user registration
- **User-Service** (Port 8081) - User management
- **Course-Service** (Port 8082) - Course management
- **Student-Service** (Port 8086) - Student management
- **Library-Service** (Port 8084) - Library management
- **Dashboard-Service** (Port 8085) - Dashboard/Analytics

## ✅ Fixed Issues

### 1. **Port Conflicts** ✓
- ✅ Student-Service moved from 8083 to 8086
- ✅ Auth-Service remains on 8083

### 2. **Eureka Service Discovery** ✓
- ✅ Course-Service: Eureka enabled
- ✅ Student-Service: Eureka enabled
- ✅ Library-Service: Eureka enabled
- ✅ Dashboard-Service: Eureka enabled
- ✅ API-Gateway: @EnableDiscoveryClient added

### 3. **Database Configuration** ✓
- ✅ Auth-Service: Corrected database name to `auth_service_db`
- ✅ All services: Password moved to environment variables `${DB_PASSWORD:default}`

### 4. **Sensitive Credentials** ✓
- ✅ Mail credentials moved to environment variables:
  - `${MAIL_USERNAME:default}`
  - `${MAIL_PASSWORD:default}`

### 5. **Missing Dependencies Added** ✓
- ✅ Course-Service: Added Spring Security, Swagger, JWT
- ✅ Student-Service: Added Spring Security, Swagger, JWT
- ✅ Library-Service: Added Spring Security, Swagger, JWT
- ✅ Dashboard-Service: Added Eureka Client, Swagger

### 6. **Naming Conventions Standardized** ✓
- ✅ Course-Service: "COURSE-SERVICE" → "Course-Service"
- ✅ Student-Service: "student-service" → "Student-Service"
- ✅ Library-Service: "LIBRARY-SERVICE" → "Library-Service"
- ✅ Dashboard-Service: "dashboardservice" → "Dashboard-Service"

### 7. **Code Quality** ✓
- ✅ API-Gateway: Removed duplicate plugin configurations in pom.xml

### 8. **User/Employee Fields** ✓
- ✅ Added `department` field to UserEntity
- ✅ Department field for role-based organization
- ✅ Follows SRS section 3.2 requirements
- ✅ API-Gateway: Routes consolidated in GatewayConfig.java (removed from YAML)
- ✅ Student-Service & Library-Service: @EnableFeignClients verified

### 8. **Security Features** ✓
- ✅ Spring Security added to all microservices
- ✅ JWT support added to all services
- ✅ Swagger/OpenAPI documentation enabled on all services

## 📋 Service Configuration

### Port Mapping
```
8761  → Discovery-Server (Eureka)
8080  → API-Gateway
8083  → Auth-Service
8081  → User-Service
8082  → Course-Service
8086  → Student-Service ⚠️ Changed from 8083
8084  → Library-Service
8085  → Dashboard-Service
```

## 🚀 Running the Services

### 1. Set Environment Variables

Create a `.env` file in the root directory (or set in your system):

```bash
# Database Passwords
DB_PASSWORD=Vivan@123
DB_PASSWORD_COURSE=root
DB_PASSWORD_STUDENT=root
DB_PASSWORD_LIBRARY=root

# Mail Configuration
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# Eureka URL
EUREKA_CLIENT_SERVICE_URL=http://localhost:8761/eureka
```

### 2. Start Services in Order

```bash
# 1. Start Discovery Server
cd Discovery-Server
mvn spring-boot:run

# 2. Start Auth-Service (in a new terminal)
cd Auth-Service
mvn spring-boot:run

# 3. Start User-Service (in a new terminal)
cd User-Service
mvn spring-boot:run

# 4. Start Course-Service (in a new terminal)
cd courseservice
mvn spring-boot:run

# 5. Start Student-Service (in a new terminal)
cd studentservice
mvn spring-boot:run

# 6. Start Library-Service (in a new terminal)
cd libraryservice
mvn spring-boot:run

# 7. Start Dashboard-Service (in a new terminal)
cd dashboardservice
mvn spring-boot:run

# 8. Start API-Gateway (in a new terminal)
cd API-Gateway
mvn spring-boot:run
```

## 📊 Access Points

After all services are running:

| Service | URL | Purpose |
|---------|-----|---------|
| **Discovery Server** | http://localhost:8761 | View registered services |
| **Swagger UI - Gateway** | http://localhost:8080/swagger-ui.html | API documentation |
| **Swagger UI - Auth** | http://localhost:8083/swagger-ui.html | Auth service API docs |
| **Swagger UI - User** | http://localhost:8081/swagger-ui.html | User service API docs |
| **Swagger UI - Course** | http://localhost:8082/swagger-ui.html | Course service API docs |
| **Swagger UI - Student** | http://localhost:8086/swagger-ui.html | Student service API docs |
| **Swagger UI - Library** | http://localhost:8084/swagger-ui.html | Library service API docs |
| **Swagger UI - Dashboard** | http://localhost:8085/swagger-ui.html | Dashboard service API docs |

## 🗄️ Database Setup

### Create Databases

```sql
-- Auth-Service Database
CREATE DATABASE auth_service_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- User-Service Database
CREATE DATABASE user_service_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Course-Service Database
CREATE DATABASE courseservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Student-Service Database
CREATE DATABASE studentservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Library-Service Database
CREATE DATABASE libraryservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Default Credentials

```
Username: root
Password: Vivan@123 (for Auth-Service and User-Service)
Password: root (for other services)
```

## 🔒 Security Notes

### JWT Configuration
- All services now support JWT authentication
- Protected endpoints require valid JWT tokens
- Token validation filters should be configured in each service's security config

### Email Configuration
⚠️ **Important**: Update the following in environment variables or `.env`:
```
MAIL_USERNAME=your-actual-email@gmail.com
MAIL_PASSWORD=your-app-specific-password
```

For Gmail, generate an [App Password](https://support.google.com/accounts/answer/185833).

## 📦 Maven Build

Build all services:

```bash
# Build all services
mvn clean package

# Skip tests for faster build
mvn clean package -DskipTests
```

## 🐳 Docker Support (Optional)

To containerize services, add Dockerfile to each service root:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
```

Build and run:
```bash
docker build -t college-service:1.0 .
docker run -p 8083:8083 college-service:1.0
```

## 📝 API Routes (via API-Gateway)

```
/api/auth/**      → Auth-Service (8083)
/api/users/**     → User-Service (8081)
/api/courses/**   → Course-Service (8082)
/api/students/**  → Student-Service (8086)
/api/library/**   → Library-Service (8084)
/v3/api-docs      → Swagger documentation for each service
```

## ⚙️ Configuration Files Modified

1. **API-Gateway**
   - ✅ `pom.xml` - Removed duplicate plugins
   - ✅ `application.yaml` - Routes now in GatewayConfig.java
   - ✅ `ApiGatewayApplication.java` - Added @EnableDiscoveryClient

2. **Auth-Service**
   - ✅ `application.yaml` - Corrected database name, added env var support

3. **User-Service**
   - ✅ `application.yaml` - Added env var support for passwords

4. **Course-Service**
   - ✅ `application.yaml` - Enabled Eureka, standardized naming, added env vars
   - ✅ `pom.xml` - Added Security, Swagger, JWT dependencies

5. **Student-Service**
   - ✅ `application.yaml` - Port changed 8083→8086, Eureka enabled, standardized naming
   - ✅ `pom.xml` - Added Security, Swagger, JWT dependencies

6. **Library-Service**
   - ✅ `application.yaml` - Eureka enabled, standardized naming
   - ✅ `pom.xml` - Added Security, Swagger, JWT dependencies

7. **Dashboard-Service**
   - ✅ `application.yaml` - Eureka enabled, standardized naming
   - ✅ `pom.xml` - Added Eureka Client, Swagger dependencies

## 🐛 Troubleshooting

### Services not registering with Eureka?
- Ensure Discovery-Server is running on port 8761
- Check that Eureka configuration is correct in each service's `application.yaml`
- Verify network connectivity

### Port already in use?
```bash
# Find process using port
netstat -ano | findstr :8083

# Kill process (replace PID)
taskkill /PID <PID> /F
```

### Database connection error?
- Ensure MySQL is running
- Verify databases are created
- Check `DB_PASSWORD` environment variable is set correctly

### Cannot connect to services through API-Gateway?
- Verify all services are registered in Eureka (check http://localhost:8761)
- Ensure API paths match the gateway routes in GatewayConfig.java
- Check logs for any service-specific errors

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
- [Eureka Service Discovery](https://spring.io/projects/spring-cloud-netflix)

---

**Last Updated**: April 9, 2026
**Status**: All critical issues fixed ✅


