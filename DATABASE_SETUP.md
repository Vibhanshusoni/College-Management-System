# 📊 DATABASE SCHEMA CREATION GUIDE

**Date**: April 9, 2026  
**Password for ALL databases**: `Vivan@1234` ✅ UNIFIED  
**MySQL User**: `root`

---

## ✅ DATABASES YOU NEED TO CREATE

All services now use the **same password**: `Vivan@1234`

### 📋 Complete List of Databases to Create

| # | Database Name | Service | Purpose |
|---|---|---|---|
| 1 | `auth_service_db` | Auth-Service (8083) | User authentication, JWT |
| 2 | `user_service_db` | User-Service (8081) | Employee/User management |
| 3 | `courseservice` | Course-Service (8082) | Course management |
| 4 | `studentservice` | Student-Service (8086) | Student management |
| 5 | `libraryservice` | Library-Service (8084) | Books & transactions |

---

## 🔧 SQL SCRIPT - CREATE ALL DATABASES AT ONCE

Copy and paste this in MySQL Command Line or Workbench:

```sql
-- Create all databases for College Management System
-- Password: Vivan@1234

-- 1. Auth Service Database
CREATE DATABASE IF NOT EXISTS auth_service_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 2. User Service Database
CREATE DATABASE IF NOT EXISTS user_service_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 3. Course Service Database
CREATE DATABASE IF NOT EXISTS courseservice 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 4. Student Service Database
CREATE DATABASE IF NOT EXISTS studentservice 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 5. Library Service Database
CREATE DATABASE IF NOT EXISTS libraryservice 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Verify creation
SHOW DATABASES;
```

---

## 📱 STEP-BY-STEP CREATION

### Using MySQL Command Line:

```bash
# Open MySQL Command Line
mysql -u root -p

# Enter password when prompted (if you have MySQL root password set)
# If no password, just press Enter

# Then run the SQL script above
```

### Using MySQL Workbench:

1. Open MySQL Workbench
2. Connect to your MySQL server
3. Go to: File → Open SQL Script
4. Copy the SQL script above
5. Paste it in a new SQL tab
6. Execute it (Ctrl+Shift+Enter or click Execute)

### Using phpMyAdmin (if available):

1. Open phpMyAdmin
2. Click on "SQL" tab at top
3. Paste the SQL script
4. Click "Go" button

---

## ✅ VERIFY DATABASES CREATED

After running the script, verify:

```sql
-- Check all databases
SHOW DATABASES;
```

You should see:
```
information_schema
mysql
performance_schema
sys
auth_service_db          ✅ NEW
user_service_db          ✅ NEW
courseservice            ✅ NEW
studentservice           ✅ NEW
libraryservice           ✅ NEW
```

---

## 🔐 PASSWORD CONFIRMATION

All services now use **same password**:

| Service | Password | Status |
|---------|----------|--------|
| Auth-Service | `Vivan@1234` | ✅ Updated |
| User-Service | `Vivan@1234` | ✅ Updated |
| Course-Service | `Vivan@1234` | ✅ Updated |
| Student-Service | `Vivan@1234` | ✅ Updated |
| Library-Service | `Vivan@1234` | ✅ Updated |

---

## 🔍 VERIFY CONNECTIONS

After creating databases, verify each service can connect:

### Test Auth-Service Connection:
```bash
mysql -h localhost -u root -pVivan@1234 -e "USE auth_service_db; SHOW TABLES;"
```

### Test User-Service Connection:
```bash
mysql -h localhost -u root -pVivan@1234 -e "USE user_service_db; SHOW TABLES;"
```

### Test Course-Service Connection:
```bash
mysql -h localhost -u root -pVivan@1234 -e "USE courseservice; SHOW TABLES;"
```

### Test Student-Service Connection:
```bash
mysql -h localhost -u root -pVivan@1234 -e "USE studentservice; SHOW TABLES;"
```

### Test Library-Service Connection:
```bash
mysql -h localhost -u root -pVivan@1234 -e "USE libraryservice; SHOW TABLES;"
```

All should return successfully without errors ✅

---

## 📝 CONFIGURATION UPDATES

All `application.yaml` files have been updated to use:

```yaml
datasource:
  username: root
  password: Vivan@1234  ✅ UNIFIED PASSWORD
```

No more need for:
- Environment variables
- Different passwords
- Confusion about which password for which service

**Everything uses: `Vivan@1234`**

---

## 🎯 QUICK CHECKLIST

Before starting services:

- [ ] MySQL is running
- [ ] All 5 databases created
  - [ ] auth_service_db
  - [ ] user_service_db
  - [ ] courseservice
  - [ ] studentservice
  - [ ] libraryservice
- [ ] Password is: `Vivan@1234`
- [ ] All application.yaml files updated ✅ (DONE)
- [ ] Can connect to each database

---

## 🚀 NEXT STEPS

1. **Create Databases**: Run the SQL script above
2. **Verify**: Run SHOW DATABASES command
3. **Start Services**: Run `start-all-services.bat`
4. **Check Logs**: Look for connection success messages

---

## 📊 DATABASE STRUCTURE

Hibernate will **automatically create tables** when services start!

You **only need to create the empty databases**. The services will:
- ✅ Auto-detect database schema
- ✅ Auto-create necessary tables
- ✅ Auto-create relationships
- ✅ Auto-add indexes

No manual table creation needed! 🎉

---

## ⚠️ TROUBLESHOOTING

### Error: "Access denied for user 'root'@'localhost'"
**Solution**: Check password is correct: `Vivan@1234`

### Error: "Unknown database 'auth_service_db'"
**Solution**: Run the SQL script to create databases first

### Error: "Can't connect to MySQL server"
**Solution**: 
1. Verify MySQL is running
2. Check MySQL port (default: 3306)
3. Verify root user credentials

---

## 💾 BACKUP & RESTORE (Future Reference)

### Backup a database:
```bash
mysqldump -u root -pVivan@1234 auth_service_db > auth_service_db_backup.sql
```

### Restore a database:
```bash
mysql -u root -pVivan@1234 auth_service_db < auth_service_db_backup.sql
```

---

## 📚 ADDITIONAL INFO

### Character Set UTF-8
All databases created with UTF-8 support for:
- ✅ International characters
- ✅ Emoji support
- ✅ Unicode text

### Collation utf8mb4_unicode_ci
Enables:
- ✅ Case-insensitive searches
- ✅ Full Unicode support
- ✅ Accent-insensitive comparisons

---

**Everything is ready! Create the databases and start the services! 🚀**


