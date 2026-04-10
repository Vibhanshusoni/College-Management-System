# QUICK REFERENCE GUIDE - College Management System Microservices

**Print this page or bookmark it for quick reference!**

---

## 🚀 QUICK START (35 minutes)

```
┌─────────────────────────────────────────────────┐
│ STEP 1: Create Databases (5 minutes)            │
├─────────────────────────────────────────────────┤
│ CREATE DATABASE auth_service_db;                │
│ CREATE DATABASE user_service_db;                │
│ CREATE DATABASE courseservice;                  │
│ CREATE DATABASE studentservice;                 │
│ CREATE DATABASE libraryservice;                 │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│ STEP 2: Set Environment Variables (5 minutes)   │
├─────────────────────────────────────────────────┤
│ DB_PASSWORD=Vivan@123                           │
│ MAIL_USERNAME=your-email@gmail.com              │
│ MAIL_PASSWORD=your-app-password                 │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│ STEP 3: Start Services (3 minutes)              │
├─────────────────────────────────────────────────┤
│ Windows: start-all-services.bat                 │
│ PowerShell: .\start-all-services.ps1            │
│ Manual: See QUICK_START.md                      │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│ STEP 4: Verify (5 minutes)                      │
├─────────────────────────────────────────────────┤
│ Eureka: http://localhost:8761                   │
│ Gateway: http://localhost:8080                  │
│ Swagger: http://localhost:8080/swagger-ui.html  │
└─────────────────────────────────────────────────┘
```

---

## 📌 MUST REMEMBER

### Port Mapping ⚠️
```
8761 │ Discovery-Server (Eureka)
8080 │ API-Gateway
8083 │ Auth-Service
8081 │ User-Service
8082 │ Course-Service
8086 │ Student-Service ⚠️ CHANGED FROM 8083
8084 │ Library-Service
8085 │ Dashboard-Service
```

### Database Names ⚠️
```
auth_service_db    (NOT user_service_db!)
user_service_db
courseservice
studentservice
libraryservice
```

### Access Points 🌐
```
http://localhost:8761           → Eureka Dashboard
http://localhost:8080/swagger-ui.html → API Documentation
http://localhost:8083/swagger-ui.html → Auth Service Doc
http://localhost:8081/swagger-ui.html → User Service Doc
http://localhost:8082/swagger-ui.html → Course Service Doc
http://localhost:8086/swagger-ui.html → Student Service Doc
http://localhost:8084/swagger-ui.html → Library Service Doc
http://localhost:8085/swagger-ui.html → Dashboard Service Doc
```

---

## 🐛 TROUBLESHOOTING CHEAT SHEET

### ❌ "Address already in use: bind"
```bash
netstat -ano | findstr :8083
taskkill /PID <PID> /F
```

### ❌ "Communications link failure"
```bash
mysql -u root -p
SHOW DATABASES;  # Verify databases exist
```

### ❌ "DiscoveryClient_SERVICE: An error occurred"
1. Verify Discovery-Server is running (8761)
2. Check eureka.client.service-url in application.yaml
3. Verify network connectivity

### ❌ "404 Not Found on Swagger"
1. Verify service is running
2. Check: http://localhost:<port>/swagger-ui.html
3. Run: mvn clean install

### ❌ "Service not in Eureka"
1. Wait 30 seconds for registration
2. Check application.yaml Eureka config
3. Restart service

---

## 📂 DOCUMENTATION MAP

```
Need to...                          Read...
─────────────────────────────────────────────────────
Get started fast                    QUICK_START.md
Understand the project              README.md
Learn all config details            CONFIGURATION.md
See what was changed                CHANGES_SUMMARY.md
Verify everything works             VERIFICATION_REPORT.md
Navigate documentation              DOCUMENTATION_INDEX.md
```

---

## ✅ VERIFICATION CHECKLIST

Before reporting issues:

- [ ] All databases created? `SHOW DATABASES;` in MySQL
- [ ] Environment variables set? `echo %DB_PASSWORD%` (Windows)
- [ ] Java 17+ installed? `java -version`
- [ ] Maven working? `mvn -version`
- [ ] MySQL running? `mysql -u root -p`
- [ ] No port conflicts? Check with `netstat -ano`
- [ ] All 8 services started?
- [ ] Eureka shows 7 services? Visit localhost:8761
- [ ] Swagger loads? Visit localhost:8080/swagger-ui.html
- [ ] No errors in terminal windows?

---

## 🔐 SECURITY CREDENTIALS

### Database
```
Username: root
Password: Vivan@123  (set via DB_PASSWORD env var)
         or: root   (for other services)
```

### Email (Gmail)
```
Username: your-email@gmail.com (set via MAIL_USERNAME)
Password: your-app-password    (set via MAIL_PASSWORD)
Note: Use Gmail App Password, not regular password
```

### JWT Tokens
```
Automatically handled by Spring Security
Configure in each service's security config
```

---

## 📊 SERVICE HEALTH ENDPOINTS

Test each service is running:

```bash
curl http://localhost:8761  # Discovery Server
curl http://localhost:8080  # API Gateway
curl http://localhost:8083  # Auth Service
curl http://localhost:8081  # User Service
curl http://localhost:8082  # Course Service
curl http://localhost:8086  # Student Service
curl http://localhost:8084  # Library Service
curl http://localhost:8085  # Dashboard Service
```

Expected response: Service running indication

---

## 🔄 API GATEWAY ROUTES

Routes via API-Gateway (localhost:8080):

```
/api/auth/**      → Auth-Service (8083)
/api/users/**     → User-Service (8081)
/api/courses/**   → Course-Service (8082)
/api/students/**  → Student-Service (8086)
/api/library/**   → Library-Service (8084)
/v3/api-docs      → Swagger documentation
```

---

## 🚀 COMMON COMMANDS

### Start Services
```bash
REM Windows
start-all-services.bat

REM PowerShell
.\start-all-services.ps1

REM Manual - Terminal 1
cd Discovery-Server && mvn spring-boot:run

REM Manual - Terminal 2
cd Auth-Service && mvn spring-boot:run
# ... etc for other services
```

### Stop Services
```bash
REM Windows - Close all terminal windows
REM Or: taskkill /F /IM java.exe

REM PowerShell
Get-Process java | Stop-Process -Force

REM Linux/Mac
pkill -9 java
```

### Clean Build
```bash
mvn clean package -DskipTests
```

### View Logs
```bash
REM Check terminal window where service is running
REM Or redirect to file:
cd Auth-Service && mvn spring-boot:run > auth.log 2>&1
```

---

## 🎯 CRITICAL CHANGES SUMMARY

| What | Before | After | Impact |
|-----|--------|-------|--------|
| Student Port | 8083 | 8086 | Update load balancer |
| Auth DB | user_service_db | auth_service_db | Create new DB |
| Credentials | Hardcoded | Env Variables | Must set .env |
| Eureka | 4 services disabled | All enabled | All discoverable |
| Security | Minimal | Full | Protected APIs |
| Swagger | 2 services | All services | Full documentation |

---

## 💾 FILE LOCATIONS

```
Root/
├── QUICK_START.md              ← Read first!
├── README.md                   ← Full docs
├── CONFIGURATION.md            ← Tech details
├── CHANGES_SUMMARY.md          ← What changed
├── VERIFICATION_REPORT.md      ← QA results
├── DOCUMENTATION_INDEX.md      ← Map
├── QUICK_REFERENCE.md          ← This file
├── .env.example                ← Config template
├── start-all-services.bat      ← Windows start
├── start-all-services.ps1      ← PowerShell start
│
└── 8 Microservice directories/
    └── src/main/resources/
        └── application.yaml
```

---

## 📞 GETTING HELP

**Quick Issue Resolution:**

1. **Can't start services?**
   → Check ports aren't in use, MySQL running
   → See QUICK_START.md "Common Issues"

2. **Services not registering?**
   → Check Eureka at localhost:8761
   → Verify eureka config in application.yaml

3. **Database connection fails?**
   → Verify MySQL running
   → Check databases created
   → Verify environment variables set

4. **Swagger not loading?**
   → Verify service running on correct port
   → Check URL: localhost:<port>/swagger-ui.html

5. **Don't understand something?**
   → Check DOCUMENTATION_INDEX.md for navigation
   → Read relevant section in appropriate doc

---

## ⏱️ TIME ESTIMATES

| Task | Time |
|------|------|
| Read QUICK_START.md | 15 min |
| Create databases | 5 min |
| Set environment vars | 5 min |
| Start services | 3 min |
| Verify working | 5 min |
| **Total to running** | **33 min** |
| Read README.md fully | 30 min |
| Read CONFIGURATION.md | 45 min |
| First API call | 5 min |

---

## 🎓 NEXT STEPS AFTER STARTUP

1. **Verify Everything**
   - Check Eureka: localhost:8761
   - Check Swagger: localhost:8080/swagger-ui.html
   - Test one API endpoint

2. **Read Full Documentation**
   - README.md - understand project
   - CONFIGURATION.md - technical details

3. **Review Security**
   - Check Spring Security setup
   - Understand JWT implementation
   - Review .env template

4. **Start Development**
   - Begin building features
   - Keep this guide handy
   - Refer to documentation as needed

---

## ✨ QUICK WIN CHECKLIST

Get your first success in 5 minutes:

- [ ] Databases created
- [ ] Services started (all 8)
- [ ] Visit: http://localhost:8761
- [ ] See 7 services registered
- [ ] Success! 🎉

---

## 🔗 IMPORTANT LINKS

**Bookmark These:**
- Eureka: http://localhost:8761
- API Docs: http://localhost:8080/swagger-ui.html
- QUICK_START.md (in project root)
- DOCUMENTATION_INDEX.md (in project root)

---

## 📝 VERSION INFO

```
Created: April 9, 2026
Status: Complete & Verified ✅
Quality: Production Ready ✅
Version: 1.0
```

---

**Print this page or save to phone for easy reference during development!**

**Happy Coding! 🚀**

