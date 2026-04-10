@echo off
REM College Management System - Startup Script
REM This script starts all microservices in order

echo.
echo ====================================
echo College Management System Startup
echo ====================================
echo.
echo This script will start all services.
echo Each service will open in a new terminal window.
echo.
echo Service Startup Order:
echo 1. Discovery-Server (8761)
echo 2. Auth-Service (8083)
echo 3. User-Service (8081)
echo 4. Course-Service (8082)
echo 5. Student-Service (8086)
echo 6. Library-Service (8084)
echo 7. Dashboard-Service (8085)
echo 8. API-Gateway (8080)
echo.
echo Press any key to start...
pause

REM Set the root directory
cd /d %~dp0

REM 1. Start Discovery-Server
echo Starting Discovery-Server...
start "Discovery-Server" cmd /k "cd Discovery-Server && mvn spring-boot:run"
timeout /t 5 /nobreak

REM 2. Start Auth-Service
echo Starting Auth-Service...
start "Auth-Service" cmd /k "cd Auth-Service && mvn spring-boot:run"
timeout /t 3 /nobreak

REM 3. Start User-Service
echo Starting User-Service...
start "User-Service" cmd /k "cd User-Service && mvn spring-boot:run"
timeout /t 3 /nobreak

REM 4. Start Course-Service
echo Starting Course-Service...
start "Course-Service" cmd /k "cd courseservice && mvn spring-boot:run"
timeout /t 3 /nobreak

REM 5. Start Student-Service
echo Starting Student-Service...
start "Student-Service" cmd /k "cd studentservice && mvn spring-boot:run"
timeout /t 3 /nobreak

REM 6. Start Library-Service
echo Starting Library-Service...
start "Library-Service" cmd /k "cd libraryservice && mvn spring-boot:run"
timeout /t 3 /nobreak

REM 7. Start Dashboard-Service
echo Starting Dashboard-Service...
start "Dashboard-Service" cmd /k "cd dashboardservice && mvn spring-boot:run"
timeout /t 3 /nobreak

REM 8. Start API-Gateway
echo Starting API-Gateway...
start "API-Gateway" cmd /k "cd API-Gateway && mvn spring-boot:run"

echo.
echo ====================================
echo All services started!
echo ====================================
echo.
echo Access Points:
echo - Eureka Discovery: http://localhost:8761
echo - API Gateway: http://localhost:8080
echo - Swagger UI: http://localhost:8080/swagger-ui.html
echo.
echo Check each terminal window for startup logs.
echo Close this window when done.
pause

