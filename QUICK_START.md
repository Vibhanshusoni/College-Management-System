# College Management System - Quick Start Checklist

## ✅ Pre-Requisites

- [ ] Java 17 installed (run `java -version`)
- [ ] Maven installed (run `mvn --version`)
- [ ] MySQL Server running on localhost:3306
- [ ] MySQL credentials: username=`root`, password=`Vivan@123` (unified for all services)

---

## 🗄️ Database Setup (One-Time)

### 1. Create Databases
Run in MySQL:
```sql
CREATE DATABASE auth_service_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE user_service_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE courseservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE studentservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE libraryservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

- [ ] All 5 databases created

### 2. Set Environment Variables

**Windows (Command Prompt):**
```cmd
set DB_PASSWORD=Vivan@123
```
set MAIL_USERNAME=your-email@gmail.com
set MAIL_PASSWORD=your-app-password
```

**Windows (PowerShell):**
```powershell
$env:DB_PASSWORD = "Vivan@123"
$env:MAIL_USERNAME = "your-email@gmail.com"
$env:MAIL_PASSWORD = "your-app-password"
```

- [ ] Environment variables set

---

## 🚀 Starting Services

### Option 1: Automated Startup (Windows)

```cmd
REM In Command Prompt, navigate to project root
start-all-services.bat
```

- [ ] Batch script executed
- [ ] All 8 terminal windows opened

### Option 2: Automated Startup (PowerShell)

```powershell
Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope Process
.\start-all-services.ps1
```

- [ ] PowerShell script executed
- [ ] All 8 terminal windows opened

### Option 3: Manual Startup (Recommended for debugging)

**Terminal 1 - Discovery Server:**
```bash
cd Discovery-Server
mvn spring-boot:run
```
Wait for: `Tomcat started on port(s): 8761`

**Terminal 2 - Auth Service:**
```bash
cd Auth-Service
mvn spring-boot:run
```
Wait for: `Tomcat started on port(s): 8083`

**Terminal 3 - User Service:**
```bash
cd User-Service
mvn spring-boot:run
```
Wait for: `Tomcat started on port(s): 8081`

**Terminal 4 - Course Service:**
```bash
cd courseservice
mvn spring-boot:run
```
Wait for: `Tomcat started on port(s): 8082`

**Terminal 5 - Student Service:**
```bash
cd studentservice
mvn spring-boot:run
```
Wait for: `Tomcat started on port(s): 8086`

**Terminal 6 - Library Service:**
```bash
cd libraryservice
mvn spring-boot:run
```
Wait for: `Tomcat started on port(s): 8084`

**Terminal 7 - Dashboard Service:**
```bash
cd dashboardservice
mvn spring-boot:run
```
Wait for: `Tomcat started on port(s): 8085`

**Terminal 8 - API Gateway:**
```bash
cd API-Gateway
mvn spring-boot:run
```
Wait for: `Tomcat started on port(s): 8080`

---

## ✅ Verification Steps

### 1. Check Eureka Discovery
- [ ] Open browser: http://localhost:8761
- [ ] Verify all 7 services registered (excluding Discovery Server itself)
  - [ ] AUTH-SERVICE (8083)
  - [ ] USER-SERVICE (8081)
  - [ ] COURSE-SERVICE (8082)
  - [ ] STUDENT-SERVICE (8086)
  - [ ] LIBRARY-SERVICE (8084)
  - [ ] DASHBOARD-SERVICE (8085)
  - [ ] API-GATEWAY (8080)

### 2. Check API Gateway
- [ ] Open browser: http://localhost:8080
- [ ] Expected response: Gateway is running

### 3. Check Swagger Documentation
- [ ] API Gateway: http://localhost:8080/swagger-ui.html
- [ ] Auth Service: http://localhost:8083/swagger-ui.html
- [ ] User Service: http://localhost:8081/swagger-ui.html
- [ ] Course Service: http://localhost:8082/swagger-ui.html
- [ ] Student Service: http://localhost:8086/swagger-ui.html
- [ ] Library Service: http://localhost:8084/swagger-ui.html
- [ ] Dashboard Service: http://localhost:8085/swagger-ui.html

### 4. Test API Routes
Open Postman or use curl:

```bash
# Test Auth Service via Gateway
curl http://localhost:8080/api/auth/health

# Test User Service via Gateway
curl http://localhost:8080/api/users/health

# Test Course Service via Gateway
curl http://localhost:8080/api/courses/health

# Test Student Service via Gateway
curl http://localhost:8080/api/students/health

# Test Library Service via Gateway
curl http://localhost:8080/api/library/health

# Test Dashboard Service via Gateway
curl http://localhost:8080/dashboard/health
```

---

## 🔍 Common Issues & Solutions

### Issue: Port Already in Use (8083, 8080, etc.)

**Error Message:**
```
Address already in use: bind
```

**Solution:**
```cmd
REM Find process using port (Windows)
netstat -ano | findstr :8083

REM Kill the process (replace PID)
taskkill /PID <PID> /F

REM Or change port in application.yaml and restart
```

### Issue: Database Connection Failed

**Error Message:**
```
Communications link failure: java.net.ConnectException: Connection refused
```

**Solution:**
1. Ensure MySQL is running: `mysql -u root -p`
2. Verify databases are created: `SHOW DATABASES;`
3. Check credentials in `application.yaml` or environment variables
4. Ensure firewall allows port 3306

### Issue: Service Not Registering with Eureka

**Error Message:**
```
DiscoveryClient_<SERVICE-NAME>: An error occurred
```

**Solution:**
1. Verify Discovery Server is running on 8761
2. Check `eureka.client.service-url.defaultZone` in `application.yaml`
3. Verify network connectivity
4. Check service name in Eureka matches configured name

### Issue: Cannot Access Swagger UI

**Error Message:**
```
404 Not Found
```

**Solution:**
1. Verify service is running
2. Check URL: http://localhost:<port>/swagger-ui.html
3. Verify `springdoc-openapi-starter-webmvc-ui` is in pom.xml
4. Rebuild: `mvn clean install`

### Issue: Environment Variables Not Recognized

**Solution:**
1. Verify variables are set before starting Maven
2. Use: `mvn spring-boot:run -Dspring-boot.run.arguments="--DB_PASSWORD=xxx"`
3. Or set in IDE run configuration
4. Or add to system environment permanently (Windows: Settings > Environment Variables)

---

## 📊 Service Port Reference

| Service | Port | Swagger |
|---------|------|---------|
| Discovery Server | 8761 | http://localhost:8761 |
| API Gateway | 8080 | http://localhost:8080/swagger-ui.html |
| Auth Service | 8083 | http://localhost:8083/swagger-ui.html |
| User Service | 8081 | http://localhost:8081/swagger-ui.html |
| Course Service | 8082 | http://localhost:8082/swagger-ui.html |
| Student Service | **8086** ⚠️ CHANGED | http://localhost:8086/swagger-ui.html |
| Library Service | 8084 | http://localhost:8084/swagger-ui.html |
| Dashboard Service | 8085 | http://localhost:8085/swagger-ui.html |

⚠️ **Note**: Student Service port changed from 8083 to 8086 to avoid conflict with Auth Service

---

## 🧹 Cleanup & Stop Services

### Stop All Services

**Option 1: Close all terminal windows** (simplest)

**Option 2: Kill processes**

Windows Command Prompt:
```cmd
taskkill /F /IM java.exe
```

Windows PowerShell:
```powershell
Get-Process java | Stop-Process -Force
```

Linux/Mac:
```bash
pkill -9 java
```

### Clean Build (if issues arise)

```bash
REM Navigate to project root
mvn clean

REM Then rebuild
mvn install -DskipTests

REM Or for specific service
cd Auth-Service
mvn clean install -DskipTests
```

---

## 📈 Monitoring & Logs

### View Eureka Dashboard
- URL: http://localhost:8761
- Shows all registered instances
- Real-time status monitoring

### View Individual Service Logs
- Check the terminal window where service is running
- Look for errors starting with `ERROR` or `WARN`

### Enable Debug Logging
Add to `application.yaml`:
```yaml
logging:
  level:
    root: INFO
    com.apiGateway: DEBUG
    org.springframework.cloud: DEBUG
```

---

## 🔐 Security Checklist

Before deploying to production:

- [ ] Change default database passwords
- [ ] Update email credentials in environment variables
- [ ] Enable HTTPS/SSL on API Gateway
- [ ] Configure CORS properly
- [ ] Implement rate limiting
- [ ] Add authentication to Eureka dashboard
- [ ] Use secrets management service
- [ ] Enable request/response logging
- [ ] Set up monitoring and alerts
- [ ] Review security dependencies for vulnerabilities

---

## 📚 Documentation Files

- **README.md** - Project overview and setup
- **CONFIGURATION.md** - Detailed configuration guide
- **.env.example** - Environment variables template
- **start-all-services.bat** - Windows batch startup script
- **start-all-services.ps1** - PowerShell startup script

---

## 🆘 Getting Help

### Check Service Status
```bash
# Via Eureka
http://localhost:8761

# Via individual service health endpoint
http://localhost:8080/actuator/health
http://localhost:8083/actuator/health
```

### Review Logs
1. Look in terminal windows for error messages
2. Check MySQL error log: `C:\ProgramData\MySQL\MySQL Server 8.0\Data\[hostname].err`
3. Check Java heap size if OutOfMemory occurs

### Rebuild Everything
```bash
mvn clean package -DskipTests
```

---

## 💾 Backup & Recovery

### Backup Database
```bash
mysqldump -u root -p auth_service_db > auth_service_db.sql
mysqldump -u root -p user_service_db > user_service_db.sql
```

### Restore Database
```bash
mysql -u root -p auth_service_db < auth_service_db.sql
mysql -u root -p user_service_db < user_service_db.sql
```

---

**Last Updated**: April 9, 2026  
**Status**: Ready for Testing ✅

## Final Checklist

- [ ] All prerequisites installed
- [ ] Databases created
- [ ] Environment variables set
- [ ] Services started successfully
- [ ] All services registered in Eureka
- [ ] Swagger UI accessible on all services
- [ ] API routes working through Gateway
- [ ] No errors in logs
- [ ] Ready for development!

**You're all set! 🎉**





