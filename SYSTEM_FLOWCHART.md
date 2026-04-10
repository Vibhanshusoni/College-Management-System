# 📊 COMPLETE SYSTEM FLOWCHART - ALL ROLES & CRUD OPERATIONS

**Date**: April 9, 2026  
**System**: College Management System Microservices  
**All Roles**: Student, Faculty, Librarian, Admin

---

## 🎯 OVERALL SYSTEM ARCHITECTURE

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER                              │
│  (Web/Mobile Application - Student, Faculty, Librarian, Admin)  │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                   API GATEWAY (Port 8080)                       │
│  • Routes requests to microservices                             │
│  • AuthFilter validates JWT tokens                              │
│  • Checks role-based permissions                               │
└─────────────────────────────────────────────────────────────────┘
           │                │                 │
           ▼                ▼                 ▼
    ┌────────────┐   ┌────────────┐   ┌────────────┐
    │Auth        │   │User        │   │Course      │
    │Service     │   │Service     │   │Service     │
    │(8083)      │   │(8081)      │   │(8082)      │
    └────────────┘   └────────────┘   └────────────┘
           │                │                 │
           ▼                ▼                 ▼
    ┌────────────┐   ┌────────────┐   ┌────────────┐
    │Student     │   │Library     │   │Dashboard   │
    │Service     │   │Service     │   │Service     │
    │(8086)      │   │(8084)      │   │(8085)      │
    └────────────┘   └────────────┘   └────────────┘
           │                │                 │
           └────────┬───────┴────────┬────────┘
                    ▼
        ┌───────────────────────┐
        │  MySQL Databases      │
        │  (5 databases)        │
        └───────────────────────┘
```

---

## 👤 ROLE-BASED ACCESS CONTROL (RBAC)

```
┌────────────────────────────────────────────────────────────────┐
│                        USER ROLES                               │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  ADMIN ────────────────────────────────────────────────────▶ │
│  ✅ Full Access to All Services                              │
│  ✅ Manage Users (Create, Read, Update, Delete)              │
│  ✅ Manage Courses                                           │
│  ✅ View Students                                            │
│  ✅ Library Management                                       │
│  ✅ Admin Dashboard                                          │
│                                                                │
│  FACULTY ──────────────────────────────────────────────────▶ │
│  ✅ Create & Manage Courses (Own only)                       │
│  ✅ Assign Students to Courses                               │
│  ✅ View Course Roster                                       │
│  ✅ Issue Library Books                                      │
│  ✅ Faculty Dashboard                                        │
│  ❌ Cannot Delete Users                                      │
│  ❌ Cannot Manage Librarian Roles                            │
│                                                                │
│  LIBRARIAN ────────────────────────────────────────────────▶ │
│  ✅ Manage Books (Create, Update, Delete)                    │
│  ✅ Issue/Return Books                                       │
│  ✅ Track Overdue Books                                      │
│  ✅ View Transactions                                        │
│  ✅ Library Dashboard                                        │
│  ❌ Cannot Manage Users                                      │
│  ❌ Cannot Create Courses                                    │
│                                                                │
│  STUDENT ─────────────────────────────────────────────────▶ │
│  ✅ View Own Profile                                        │
│  ✅ View Enrolled Courses                                    │
│  ✅ View Course Materials                                    │
│  ✅ Issue/Return Books (Library)                             │
│  ✅ View My Books                                            │
│  ✅ Student Dashboard                                        │
│  ❌ Cannot Create Courses                                    │
│  ❌ Cannot Manage Users                                      │
│  ❌ Cannot Delete Books                                      │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

---

## 🔐 AUTHENTICATION & AUTHORIZATION FLOW

```
┌─────────────────────────────────────────────────────────────┐
│                   USER LOGIN FLOW                             │
└─────────────────────────────────────────────────────────────┘

1. USER SENDS LOGIN REQUEST
   ├─ POST /api/auth/login
   ├─ Body: {email, password}
   └─ No Authorization Header needed (Public Endpoint)
              │
              ▼
┌──────────────────────────────────────────────────────────┐
│            API GATEWAY → AUTH SERVICE (8083)              │
│            • Routes to Auth-Service                       │
│            • Validates credentials                        │
└──────────────────────────────────────────────────────────┘
              │
              ▼
2. AUTH SERVICE VALIDATES
   ├─ Check user exists
   ├─ Verify password
   ├─ Extract user role (ADMIN/FACULTY/LIBRARIAN/STUDENT)
   └─ Generate JWT token
              │
              ▼
3. RESPONSE TO CLIENT
   ├─ JWT Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ├─ User ID
   ├─ Role: STUDENT/FACULTY/LIBRARIAN/ADMIN
   └─ Expiry: 24 hours
              │
              ▼
4. SUBSEQUENT REQUESTS
   ├─ Client stores JWT token
   ├─ Adds to Authorization header: "Bearer <token>"
   └─ All protected endpoints require this token
              │
              ▼
┌──────────────────────────────────────────────────────────┐
│            API GATEWAY → AUTH FILTER                      │
│            • Extract token from header                    │
│            • Validate JWT signature                       │
│            • Extract username & role                      │
│            • Check if token blacklisted                   │
│            • Verify role has permission                   │
└──────────────────────────────────────────────────────────┘
              │
              ▼
5. DECISION
   ├─ ✅ Valid token + Has role → Allow request
   ├─ ❌ Invalid token → 401 Unauthorized
   ├─ ❌ Expired token → 401 Unauthorized
   └─ ❌ No permission → 401 Unauthorized
```

---

## 📊 ADMIN CRUD OPERATIONS FLOW

```
┌────────────────────────────────────────────────────────────────┐
│                        ADMIN OPERATIONS                        │
└────────────────────────────────────────────────────────────────┘

┌─────────────────────────┐
│   ADMIN LOGIN           │
└────────────┬────────────┘
             ▼
    ┌──────────────────┐
    │ Access Dashboard │
    │ POST /api/auth/  │
    │ login            │
    └────────┬─────────┘
             ▼
    ┌──────────────────────────────┐
    │ Receive JWT + Role: ADMIN    │
    └────────┬─────────────────────┘
             ▼
    ┌──────────────────────────────────────────┐
    │ GET /api/dashboard/admin                 │
    │ • View total users                       │
    │ • View total courses                     │
    │ • View system reports                    │
    └────────┬─────────────────────────────────┘
             ▼

ADMIN USER MANAGEMENT (CRUD)
┌────────────────────────────────────────────────┐

1. CREATE USER
   POST /api/users
   ├─ Body: {name, email, password, role}
   ├─ Role options: FACULTY, LIBRARIAN, STUDENT
   └─ Only ADMIN can do this
              │
              ▼
   API Gateway → User-Service → MySQL
   Create new user record
              │
              ▼
   ✅ Response: User created with ID
              │
              ▼

2. READ/VIEW USERS
   GET /api/users
   ├─ Returns all users
   └─ Only ADMIN can see all
              │
              ▼
   GET /api/users/{userId}
   ├─ Returns specific user
   └─ Shows: name, email, role
              │
              ▼

3. UPDATE USER
   PUT /api/users/{userId}
   ├─ Body: {name, email, role}
   ├─ Can change role (FACULTY → LIBRARIAN)
   └─ Can update details
              │
              ▼
   API Gateway → User-Service → MySQL
   Update user record
              │
              ▼
   ✅ Response: User updated

4. DELETE USER
   DELETE /api/users/{userId}
   ├─ Removes user from system
   ├─ Also removes from courses
   └─ Cascading delete
              │
              ▼
   API Gateway → User-Service → MySQL
   Delete user and related data
              │
              ▼
   ✅ Response: User deleted

└────────────────────────────────────────────────┘

ADMIN COURSE MANAGEMENT
┌────────────────────────────────────────────────┐

1. CREATE COURSE
   POST /api/courses
   ├─ Body: {name, code, description, credits}
   └─ ADMIN or FACULTY can create
              │
              ▼
   ✅ Course created

2. VIEW COURSES
   GET /api/courses
   ├─ Returns all courses
   └─ Shows faculty, students count
              │
              ▼

3. UPDATE COURSE
   PUT /api/courses/{courseId}
   ├─ Modify course details
   └─ Change credits, description
              │
              ▼
   ✅ Course updated

4. DELETE COURSE
   DELETE /api/courses/{courseId}
   ├─ Remove course
   └─ Unenroll all students
              │
              ▼
   ✅ Course deleted

└────────────────────────────────────────────────┘

ADMIN STUDENT MANAGEMENT
┌────────────────────────────────────────────────┐

1. CREATE STUDENT
   POST /api/students
   ├─ Body: {userId, enrollmentNo, semester}
   └─ Requires existing user

2. VIEW STUDENTS
   GET /api/students
   ├─ Returns all students
   └─ Shows enrollment info

3. ASSIGN COURSE
   POST /api/courses/{courseId}/
              assign-student/{studentId}
   ├─ Enroll student in course
   └─ Student can now access course

4. REMOVE STUDENT
   DELETE /api/students/{studentId}
   └─ Unenroll from all courses

└────────────────────────────────────────────────┘

ADMIN LIBRARY MANAGEMENT
┌────────────────────────────────────────────────┐

1. VIEW BOOKS
   GET /api/books
   ├─ All books in library
   └─ Shows availability

2. VIEW TRANSACTIONS
   GET /api/library/transactions
   ├─ All issue/return history
   └─ Audit trail

3. VIEW OVERDUE
   GET /api/library/overdue
   ├─ Books not returned on time
   └─ Send reminders

└────────────────────────────────────────────────┘
```

---

## 👨‍🏫 FACULTY CRUD OPERATIONS FLOW

```
┌────────────────────────────────────────────────────────────────┐
│                      FACULTY OPERATIONS                        │
└────────────────────────────────────────────────────────────────┘

┌─────────────────────────┐
│   FACULTY LOGIN         │
└────────────┬────────────┘
             ▼
    ┌──────────────────┐
    │ Access Dashboard │
    │ POST /api/auth/  │
    │ login            │
    └────────┬─────────┘
             ▼
    ┌──────────────────────────────────┐
    │ Receive JWT + Role: FACULTY      │
    └────────┬───────────────────────────┘
             ▼
    ┌──────────────────────────────────────────┐
    │ GET /api/dashboard/faculty               │
    │ • View assigned courses                  │
    │ • View enrolled students                 │
    │ • View course stats                      │
    └────────┬─────────────────────────────────┘
             ▼

FACULTY COURSE MANAGEMENT
┌────────────────────────────────────────────────┐

1. CREATE COURSE (Only own courses)
   POST /api/courses
   ├─ Body: {name, code, description, credits}
   ├─ Faculty ID auto-assigned
   └─ Faculty becomes course owner
              │
              ▼
   ✅ Course created (Faculty assigned)

2. VIEW COURSES
   GET /api/courses
   ├─ Returns own assigned courses
   ├─ Cannot see other faculty's courses
   └─ Shows student list

3. UPDATE COURSE
   PUT /api/courses/{courseId}
   ├─ Only if faculty owns course
   ├─ Modify name, description, credits
   └─ Cannot reassign faculty
              │
              ▼
   ✅ Course updated

4. DELETE COURSE
   DELETE /api/courses/{courseId}
   ├─ Only if no students enrolled
   └─ OR must unenroll first
              │
              ▼
   ✅ Course deleted

└────────────────────────────────────────────────┘

FACULTY STUDENT MANAGEMENT
┌────────────────────────────────────────────────┐

1. ASSIGN STUDENTS
   POST /api/courses/{courseId}/
              assign-student/{studentId}
   ├─ Add student to own course
   ├─ Student can now view course
   └─ Student appears in roster
              │
              ▼
   ✅ Student assigned to course

2. VIEW STUDENTS
   GET /api/students (for own course)
   ├─ Returns students in course
   ├─ Shows attendance, marks
   └─ Can track performance
              │
              ▼

3. REMOVE STUDENT
   DELETE /api/courses/{courseId}/
              remove-student/{studentId}
   ├─ Remove from course
   └─ Cannot delete user account

└────────────────────────────────────────────────┘

FACULTY LIBRARY OPERATIONS
┌────────────────────────────────────────────────┐

1. ISSUE BOOKS
   POST /api/library/issue
   ├─ Body: {studentId, bookId}
   ├─ Set due date
   └─ Track book location
              │
              ▼
   ✅ Book issued to student

2. RETURN BOOKS
   POST /api/library/return
   ├─ Body: {studentId, bookId}
   ├─ Mark as returned
   └─ Check for overdue
              │
              ▼
   ✅ Book returned (Check fine if overdue)

3. VIEW BOOKS
   GET /api/books
   ├─ See available books
   └─ Check inventory
              │
              ▼

└────────────────────────────────────────────────┘
```

---

## 📚 LIBRARIAN CRUD OPERATIONS FLOW

```
┌────────────────────────────────────────────────────────────────┐
│                     LIBRARIAN OPERATIONS                       │
└────────────────────────────────────────────────────────────────┘

┌─────────────────────────┐
│   LIBRARIAN LOGIN       │
└────────────┬────────────┘
             ▼
    ┌──────────────────┐
    │ Access Dashboard │
    │ POST /api/auth/  │
    │ login            │
    └────────┬─────────┘
             ▼
    ┌──────────────────────────────────┐
    │ Receive JWT + Role: LIBRARIAN    │
    └────────┬───────────────────────────┘
             ▼
    ┌──────────────────────────────────────────┐
    │ GET /api/dashboard/library               │
    │ • View issued books                      │
    │ • View overdue books                     │
    │ • View transactions                      │
    │ • Library stats                          │
    └────────┬─────────────────────────────────┘
             ▼

LIBRARIAN BOOK MANAGEMENT (CRUD)
┌────────────────────────────────────────────────┐

1. CREATE BOOK
   POST /api/books
   ├─ Body: {title, author, isbn, copies}
   ├─ Add new book to library
   └─ Set initial quantity
              │
              ▼
   API Gateway → Library-Service → MySQL
   Create book record
              │
              ▼
   ✅ Response: Book created with ID

2. READ/VIEW BOOKS
   GET /api/books
   ├─ Returns all books
   ├─ Shows: title, author, available copies
   └─ Sorted by name
              │
              ▼
   GET /api/books/{bookId}
   ├─ View specific book details
   ├─ Shows: ISBN, author, copies
   └─ Shows availability status
              │
              ▼

3. UPDATE BOOK
   PUT /api/books/{bookId}
   ├─ Body: {title, author, copies}
   ├─ Update book information
   └─ Adjust inventory
              │
              ▼
   API Gateway → Library-Service → MySQL
   Update book record
              │
              ▼
   ✅ Response: Book updated

4. DELETE BOOK
   DELETE /api/books/{bookId}
   ├─ Remove from library
   ├─ Only if no copies issued
   └─ Cascade unenroll
              │
              ▼
   API Gateway → Library-Service → MySQL
   Delete book and transactions
              │
              ▼
   ✅ Response: Book deleted

└────────────────────────────────────────────────┘

LIBRARIAN TRANSACTION MANAGEMENT
┌────────────────────────────────────────────────┐

1. ISSUE BOOK
   POST /api/library/issue
   ├─ Body: {studentId, bookId, dueDate}
   ├─ Student can borrow book
   ├─ Decrement available copies
   └─ Create transaction record
              │
              ▼
   ✅ Book issued - Student can collect

2. RETURN BOOK
   POST /api/library/return
   ├─ Body: {studentId, bookId}
   ├─ Mark book as returned
   ├─ Increment available copies
   ├─ Calculate fine if overdue
   └─ Update transaction
              │
              ▼
   ✅ Book returned - Process fine

3. VIEW TRANSACTIONS
   GET /api/library/transactions
   ├─ Returns all issue/return history
   ├─ Shows: student, book, issue date, due date
   └─ Status: issued, returned, overdue
              │
              ▼

4. VIEW OVERDUE BOOKS
   GET /api/library/overdue
   ├─ Books not returned by due date
   ├─ Shows: student, book, days overdue
   └─ Send automatic reminders
              │
              ▼

└────────────────────────────────────────────────┘
```

---

## 👨‍🎓 STUDENT CRUD OPERATIONS FLOW

```
┌────────────────────────────────────────────────────────────────┐
│                      STUDENT OPERATIONS                        │
└────────────────────────────────────────────────────────────────┘

┌─────────────────────────┐
│   STUDENT LOGIN         │
└────────────┬────────────┘
             ▼
    ┌──────────────────┐
    │ Access Dashboard │
    │ POST /api/auth/  │
    │ login            │
    └────────┬─────────┘
             ▼
    ┌──────────────────────────────────┐
    │ Receive JWT + Role: STUDENT      │
    └────────┬───────────────────────────┘
             ▼
    ┌──────────────────────────────────────────┐
    │ GET /api/dashboard/student               │
    │ • View enrolled courses                  │
    │ • View issued books                      │
    │ • View profile                           │
    │ • View assignments/materials             │
    └────────┬─────────────────────────────────┘
             ▼

STUDENT PROFILE OPERATIONS
┌────────────────────────────────────────────────┐

1. VIEW PROFILE (READ only)
   GET /api/users/profile
   ├─ Returns student's own data
   ├─ Shows: name, email, enrollment number
   ├─ Shows: semester, courses enrolled
   └─ Cannot see other students
              │
              ▼
   ✅ View own profile

2. UPDATE PROFILE (Limited)
   PUT /api/users/profile
   ├─ Body: {password, phone, address}
   ├─ Can only change own profile
   ├─ Cannot change role
   └─ Cannot change enrollment number
              │
              ▼
   API Gateway → User-Service → MySQL
   Update student record
              │
              ▼
   ✅ Profile updated

3. CANNOT DELETE OWN ACCOUNT
   ❌ Students cannot delete themselves
   └─ Only ADMIN can remove student

└────────────────────────────────────────────────┘

STUDENT COURSE OPERATIONS
┌────────────────────────────────────────────────┐

1. VIEW ENROLLED COURSES (READ only)
   GET /api/courses
   ├─ Returns only enrolled courses
   ├─ Cannot see all courses
   └─ Shows faculty, materials
              │
              ▼
   GET /api/courses/{courseId}
   ├─ View specific course details
   ├─ Shows: faculty, syllabus, materials
   └─ Cannot modify
              │
              ▼

2. VIEW COURSE MATERIALS
   GET /api/courses/{courseId}/materials
   ├─ See lecture slides
   ├─ See assignments
   └─ See announcements
              │
              ▼

3. SUBMIT ASSIGNMENTS
   POST /api/courses/{courseId}/submit
   ├─ Body: {assignmentId, file}
   ├─ Upload assignment
   └─ Get submission receipt
              │
              ▼
   ✅ Assignment submitted

4. CANNOT MODIFY COURSES
   ❌ Cannot create courses
   ❌ Cannot update courses
   ❌ Cannot delete courses
   └─ These are faculty operations

└────────────────────────────────────────────────┘

STUDENT LIBRARY OPERATIONS
┌────────────────────────────────────────────────┐

1. ISSUE BOOK (Request)
   POST /api/library/issue
   ├─ Body: {bookId}
   ├─ Request book from librarian
   └─ Await approval
              │
              ▼
   ✅ Book issued - Can collect

2. VIEW MY BOOKS
   GET /api/library/my-books
   ├─ Returns currently issued books
   ├─ Shows: title, author, due date
   └─ Shows: days remaining
              │
              ▼

3. RETURN BOOK
   POST /api/library/return
   ├─ Body: {bookId}
   ├─ Return borrowed book
   ├─ Librarian marks as returned
   └─ Check for overdue fine
              │
              ▼
   ✅ Book returned

4. VIEW TRANSACTION HISTORY
   GET /api/library/my-transactions
   ├─ Returns own issue/return history
   ├─ Shows: books issued, returned
   └─ Shows: fines paid
              │
              ▼

5. CANNOT MANAGE BOOKS
   ❌ Cannot create books
   ❌ Cannot update books
   ❌ Cannot delete books
   ❌ Cannot issue to others
   └─ These are librarian operations

└────────────────────────────────────────────────┘
```

---

## 🔄 COMPLETE REQUEST-RESPONSE FLOW (Example)

```
EXAMPLE: STUDENT VIEWING ENROLLED COURSES

1. CLIENT SIDE
   ├─ Student clicks "My Courses"
   └─ Browser prepares GET request

2. REQUEST PREPARATION
   ├─ URL: http://localhost:8080/api/courses
   ├─ Method: GET
   ├─ Header: Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   └─ (JWT Token from login)

3. API GATEWAY (8080)
   ├─ Receives request
   ├─ Extracts JWT token from Authorization header
   ├─ Validates token signature
   ├─ Extracts username and role from token
   └─ Checks if token is blacklisted

4. AUTH FILTER (In Gateway)
   ├─ Validates JWT token ✅
   ├─ Extracts role: STUDENT ✅
   ├─ Checks endpoint permissions ✅
   ├─ STUDENT can access /api/courses ✅
   └─ Adds X-User-Name header to request

5. ROUTE MATCHING (In Gateway)
   ├─ Path /api/courses matches /api/courses/**
   └─ Routes to: lb://Course-Service

6. LOAD BALANCER
   ├─ Looks up Course-Service in Eureka
   ├─ Finds instance at: localhost:8082
   └─ Forwards request to Course-Service

7. COURSE SERVICE (8082)
   ├─ Receives request with X-User-Name header
   ├─ Identifies user: "john@student.com"
   ├─ Queries database: SELECT courses WHERE studentId = ?
   ├─ Database returns:
   │  • Course 1: Java Programming (Faculty: Dr. Smith)
   │  • Course 2: Data Structures (Faculty: Dr. Johnson)
   │  • Course 3: Database Systems (Faculty: Dr. Williams)
   └─ Builds response object

8. RESPONSE BACK TO GATEWAY
   ├─ Status: 200 OK
   ├─ Body:
   │  {
   │    "courses": [
   │      {
   │        "id": 1,
   │        "name": "Java Programming",
   │        "code": "CS101",
   │        "faculty": "Dr. Smith",
   │        "credits": 4
   │      },
   │      ...
   │    ]
   │  }
   └─ Content-Type: application/json

9. GATEWAY RETURNS TO CLIENT
   ├─ Forwards response as-is
   ├─ Client receives 200 OK
   └─ Browser displays courses

10. CLIENT SIDE
    ├─ JavaScript parses JSON response
    ├─ Renders courses in UI
    ├─ Student sees:
    │  • Course list with faculty names
    │  • Course codes and credits
    │  • Links to view details
    └─ Student can click to view materials

✅ COMPLETE FLOW - REQUEST PROCESSED SUCCESSFULLY!
```

---

## 📱 SERVICE INTEGRATION MAP

```
┌─────────────────────────────────────────────────────────────────┐
│                   STUDENT JOURNEY                               │
└─────────────────────────────────────────────────────────────────┘

LOGIN
  │
  ├─→ POST /api/auth/login
  │   └─→ Auth-Service (8083)
  │       └─→ MySQL (auth_service_db)
  │           ✅ Returns JWT + Role
  │
DASHBOARD
  │
  ├─→ GET /api/dashboard/student
  │   └─→ Dashboard-Service (8085)
  │       ├─→ Calls User-Service
  │       ├─→ Calls Course-Service
  │       └─→ Calls Library-Service
  │           ✅ Returns dashboard data
  │
VIEW COURSES
  │
  ├─→ GET /api/courses
  │   └─→ Course-Service (8082)
  │       └─→ MySQL (courseservice)
  │           ✅ Returns enrolled courses only
  │
ISSUE BOOK
  │
  ├─→ POST /api/library/issue
  │   └─→ Library-Service (8084)
  │       └─→ MySQL (libraryservice)
  │           ✅ Creates transaction
  │
RETURN BOOK
  │
  └─→ POST /api/library/return
      └─→ Library-Service (8084)
          └─→ MySQL (libraryservice)
              ✅ Completes transaction
              ✅ Calculates fine if overdue
```

---

## 🎯 DECISION TREE - WHAT CAN EACH ROLE DO?

```
REQUEST COMES IN
   │
   ▼
IS TOKEN VALID?
   ├─ NO ──→ ❌ 401 Unauthorized
   │
   └─ YES ──→ EXTRACT ROLE
               │
               ├─ ADMIN ──→ ✅ Full Access
               │           • User CRUD
               │           • Course CRUD
               │           • Student CRUD
               │           • Library CRUD
               │           • Dashboard
               │
               ├─ FACULTY ──→ ✅ Limited Access
               │              • Create/Update own Courses
               │              • Assign Students
               │              • Issue/Return Books
               │              • Faculty Dashboard
               │
               ├─ LIBRARIAN ──→ ✅ Library Access
               │                 • Book CRUD
               │                 • Manage Transactions
               │                 • Track Overdue
               │                 • Library Dashboard
               │
               └─ STUDENT ──→ ✅ Minimal Access
                              • View Profile
                              • View Enrolled Courses
                              • View Materials
                              • Issue/Return Books
                              • Student Dashboard
```

---

**This flowchart covers ALL CRUD operations for ALL roles! 🎉**


