# College Management System - Startup Script (PowerShell)
# This script starts all microservices

Write-Host "====================================" -ForegroundColor Cyan
Write-Host "College Management System Startup" -ForegroundColor Cyan
Write-Host "====================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "This script will start all services in separate PowerShell windows." -ForegroundColor Yellow
Write-Host ""
Write-Host "Service Startup Order:" -ForegroundColor Green
Write-Host "1. Discovery-Server (8761)" -ForegroundColor Green
Write-Host "2. Auth-Service (8083)" -ForegroundColor Green
Write-Host "3. User-Service (8081)" -ForegroundColor Green
Write-Host "4. Course-Service (8082)" -ForegroundColor Green
Write-Host "5. Student-Service (8086)" -ForegroundColor Green
Write-Host "6. Library-Service (8084)" -ForegroundColor Green
Write-Host "7. Dashboard-Service (8085)" -ForegroundColor Green
Write-Host "8. API-Gateway (8080)" -ForegroundColor Green
Write-Host ""
Write-Host "Press Enter to start all services..." -ForegroundColor Yellow
Read-Host

# Get the root directory
$rootDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $rootDir

# Function to start service in new window
function Start-Service {
    param(
        [string]$ServiceName,
        [string]$ServicePath,
        [int]$Port
    )

    Write-Host "Starting $ServiceName (Port $Port)..." -ForegroundColor Cyan
    $scriptBlock = {
        param($path, $name)
        Set-Location $path
        Write-Host "Launching $name..." -ForegroundColor Green
        & mvn spring-boot:run
    }

    Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$ServicePath'; mvn spring-boot:run" -WindowStyle Normal
    Start-Sleep -Seconds 3
}

# Start all services
Start-Service -ServiceName "Discovery-Server" -ServicePath "$rootDir\Discovery-Server" -Port 8761
Start-Service -ServiceName "Auth-Service" -ServicePath "$rootDir\Auth-Service" -Port 8083
Start-Service -ServiceName "User-Service" -ServicePath "$rootDir\User-Service" -Port 8081
Start-Service -ServiceName "Course-Service" -ServicePath "$rootDir\courseservice" -Port 8082
Start-Service -ServiceName "Student-Service" -ServicePath "$rootDir\studentservice" -Port 8086
Start-Service -ServiceName "Library-Service" -ServicePath "$rootDir\libraryservice" -Port 8084
Start-Service -ServiceName "Dashboard-Service" -ServicePath "$rootDir\dashboardservice" -Port 8085
Start-Service -ServiceName "API-Gateway" -ServicePath "$rootDir\API-Gateway" -Port 8080

Write-Host ""
Write-Host "====================================" -ForegroundColor Cyan
Write-Host "All services have been started!" -ForegroundColor Cyan
Write-Host "====================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Access Points:" -ForegroundColor Green
Write-Host "- Eureka Discovery: http://localhost:8761" -ForegroundColor Green
Write-Host "- API Gateway: http://localhost:8080" -ForegroundColor Green
Write-Host "- Swagger UI: http://localhost:8080/swagger-ui.html" -ForegroundColor Green
Write-Host ""
Write-Host "Check the terminal windows for startup logs." -ForegroundColor Yellow
Write-Host "Close this window when done." -ForegroundColor Yellow
Write-Host ""
Read-Host "Press Enter to exit"

