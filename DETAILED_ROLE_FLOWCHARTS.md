# 📈 DETAILED ROLE-SPECIFIC FLOWCHARTS & DIAGRAMS

---

## 🔵 STUDENT COMPLETE WORKFLOW

```
┌──────────────────────────────────────────────────────────────────┐
│                     STUDENT WORKFLOW                              │
└──────────────────────────────────────────────────────────────────┘

START: Student accesses system
   │
   ▼
┌─────────────────────────────────┐
│ 1. LOGIN                        │
│ POST /api/auth/login            │
│ Email + Password                │
└────────────┬────────────────────┘
             │
             ▼
       ┌──────────────┐
       │ Auth-Service │
       │ Validates    │
       │ Credentials  │
       └──────┬───────┘
              │
              ▼
        ┌──────────────┐
        │ ✅ Generate  │
        │ JWT Token    │
        │ Role: Student│
        └──────┬───────┘
               │
               ▼
    ┌────────────────────────┐
    │ 2. STUDENT DASHBOARD   │
    │ GET /api/dashboard/    │
    │     student            │
    └────────┬───────────────┘
             │
             ├─→ View Courses (3)
             ├─→ View Books Issued (2)
             ├─→ View Deadlines
             └─→ View Profile
               │
               ▼
    ┌────────────────────────────────┐
    │ 3. VIEW ENROLLED COURSES       │
    │ GET /api/courses               │
    └────────┬───────────────────────┘
             │
             ├─→ Java Programming (Dr. Smith)
             ├─→ Data Structures (Dr. Johnson)
             └─→ Databases (Dr. Williams)
               │
               ▼
    ┌────────────────────────────────┐
    │ 4. CLICK ON COURSE             │
    │ GET /api/courses/{courseId}    │
    └────────┬───────────────────────┘
             │
             ├─→ View Syllabus
             ├─→ View Materials
             ├─→ View Assignments
             └─→ Submit Assignment
               │
               ▼
    ┌────────────────────────────────┐
    │ 5. SUBMIT ASSIGNMENT           │
    │ POST /api/courses/{courseId}/  │
    │ submit                         │
    └────────┬───────────────────────┘
             │
             └─→ Upload file
                 │
                 ▼
            ✅ Assignment Submitted
               │
               ▼
    ┌────────────────────────────────┐
    │ 6. GO TO LIBRARY               │
    │ GET /api/library/my-books      │
    └────────┬───────────────────────┘
             │
             ├─→ Book 1: Java (Due: 2026-04-20)
             └─→ Book 2: DBMS (Due: 2026-04-25)
               │
               ▼
    ┌────────────────────────────────┐
    │ 7. ISSUE NEW BOOK              │
    │ POST /api/library/issue        │
    │ Body: {bookId: 3}              │
    └────────┬───────────────────────┘
             │
             ▼
        ┌──────────────┐
        │Library Service│
        │ Checks        │
        │ Availability  │
        └──────┬───────┘
               │
               ├─ Book available? ✅
               │
               ▼
        ┌──────────────┐
        │ Update MySQL │
        │ Decrement    │
        │ Copy Count   │
        └──────┬───────┘
               │
               ▼
            ✅ Book Issued
               │
               ▼
    ┌────────────────────────────────┐
    │ 8. RETURN BOOK                 │
    │ POST /api/library/return       │
    │ Body: {bookId: 1}              │
    └────────┬───────────────────────┘
             │
             ▼
        ┌──────────────┐
        │Library Service│
        │ Checks Due   │
        │ Date         │
        └──────┬───────┘
               │
               ├─ On Time? ✅ (No Fine)
               │
               ▼
        ┌──────────────┐
        │ Update MySQL │
        │ Increment    │
        │ Copy Count   │
        └──────┬───────┘
               │
               ▼
            ✅ Book Returned
               │
               ▼
    ┌────────────────────────────────┐
    │ 9. UPDATE PROFILE              │
    │ PUT /api/users/profile         │
    │ Body: {password, phone}        │
    └────────┬───────────────────────┘
             │
             └─→ Update own data only
                 │
                 ▼
            ✅ Profile Updated
               │
               ▼
    ┌────────────────────────────────┐
    │ 10. LOGOUT                     │
    │ POST /api/auth/logout          │
    └────────┬───────────────────────┘
             │
             └─→ Blacklist JWT token
                 │
                 ▼
            ✅ Logged Out
               │
               ▼
            END

OPERATIONS SUMMARY:
✅ Can READ: Own profile, courses, books, materials
✅ Can CREATE: Assignment submissions, book requests
✅ Can UPDATE: Own profile (limited)
✅ Can DELETE: Cannot delete anything
❌ Cannot: Create courses, manage users, manage books
```

---

## 🟢 FACULTY COMPLETE WORKFLOW

```
┌──────────────────────────────────────────────────────────────────┐
│                     FACULTY WORKFLOW                              │
└──────────────────────────────────────────────────────────────────┘

START: Faculty accesses system
   │
   ▼
┌─────────────────────────────────┐
│ 1. LOGIN                        │
│ POST /api/auth/login            │
│ Email + Password                │
└────────┬────────────────────────┘
         │
         ▼
    ┌──────────────┐
    │Auth-Service  │
    │Validates     │
    └──────┬───────┘
           │
           ▼
        ✅ JWT Token
           Role: FACULTY
           │
           ▼
    ┌────────────────────────────┐
    │ 2. FACULTY DASHBOARD       │
    │ GET /api/dashboard/faculty │
    └────────┬───────────────────┘
             │
             ├─→ Courses Assigned: 3
             ├─→ Total Students: 85
             └─→ Available Books
               │
               ▼
    ┌────────────────────────────────┐
    │ 3. CREATE NEW COURSE           │
    │ POST /api/courses              │
    │ Body: {name, code, credits}    │
    └────────┬───────────────────────┘
             │
             └─→ Faculty auto-assigned
                 │
                 ▼
            ┌──────────────┐
            │ Course       │
            │ Service      │
            │ Creates      │
            │ Course       │
            └──────┬───────┘
                   │
                   ▼
            ✅ Course Created
               ID: CS301
               │
               ▼
    ┌────────────────────────────────┐
    │ 4. VIEW MY COURSES             │
    │ GET /api/courses               │
    └────────┬───────────────────────┘
             │
             ├─→ Course 1: Java (30 students)
             ├─→ Course 2: DBMS (25 students)
             └─→ Course 3: CS301 (New - 0 students)
               │
               ▼
    ┌────────────────────────────────┐
    │ 5. UPDATE COURSE               │
    │ PUT /api/courses/{courseId}    │
    │ Body: {credits: 3}             │
    └────────┬───────────────────────┘
             │
             └─→ Update own course only
                 │
                 ▼
            ✅ Course Updated
               │
               ▼
    ┌────────────────────────────────┐
    │ 6. ASSIGN STUDENTS TO COURSE   │
    │ POST /api/courses/{courseId}/  │
    │ assign-student/{studentId}     │
    └────────┬───────────────────────┘
             │
             ├─→ Student 1: John
             ├─→ Student 2: Jane
             └─→ Student 3: Mike
               │
               ▼
            ✅ Students Assigned
               │
               ▼
    ┌────────────────────────────────┐
    │ 7. VIEW COURSE ROSTER          │
    │ GET /api/students (in course)  │
    └────────┬───────────────────────┘
             │
             ├─→ John (Absent: 2)
             ├─→ Jane (Present: All)
             └─→ Mike (Absent: 1)
               │
               ▼
    ┌────────────────────────────────┐
    │ 8. ISSUE BOOKS                 │
    │ POST /api/library/issue        │
    │ Body: {studentId, bookId}      │
    └────────┬───────────────────────┘
             │
             └─→ Set due date
                 │
                 ▼
            ✅ Book Issued
               │
               ▼
    ┌────────────────────────────────┐
    │ 9. RECEIVE BOOKS               │
    │ POST /api/library/return       │
    │ Body: {studentId, bookId}      │
    └────────┬───────────────────────┘
             │
             └─→ Check for overdue
                 │
                 ▼
            ✅ Book Returned
               │
               ▼
    ┌────────────────────────────────┐
    │ 10. DELETE COURSE              │
    │ DELETE /api/courses/{courseId} │
    └────────┬───────────────────────┘
             │
             ├─ If no students ✅ Delete
             │
             └─ If students ❌ Cannot Delete
               │
               ▼
            Action Taken
               │
               ▼
    ┌────────────────────────────────┐
    │ 11. LOGOUT                     │
    │ POST /api/auth/logout          │
    └────────┬───────────────────────┘
             │
             ▼
            END

OPERATIONS SUMMARY:
✅ Can READ: Own courses, students, books
✅ Can CREATE: Courses (own only), assign students
✅ Can UPDATE: Own courses only
✅ Can DELETE: Own courses (if no students)
❌ Cannot: Create courses (others), manage users, manage books
```

---

## 🟣 LIBRARIAN COMPLETE WORKFLOW

```
┌──────────────────────────────────────────────────────────────────┐
│                   LIBRARIAN WORKFLOW                              │
└──────────────────────────────────────────────────────────────────┘

START: Librarian accesses system
   │
   ▼
┌─────────────────────────────────┐
│ 1. LOGIN                        │
│ POST /api/auth/login            │
│ Email + Password                │
└────────┬────────────────────────┘
         │
         ▼
        ✅ JWT Token
           Role: LIBRARIAN
           │
           ▼
    ┌────────────────────────────────┐
    │ 2. LIBRARY DASHBOARD           │
    │ GET /api/dashboard/library     │
    └────────┬───────────────────────┘
             │
             ├─→ Books in Library: 500
             ├─→ Currently Issued: 120
             ├─→ Overdue: 5
             └─→ Today's Transactions: 15
               │
               ▼
    ┌────────────────────────────────┐
    │ 3. CREATE NEW BOOK             │
    │ POST /api/books                │
    │ Body: {title, author, copies}  │
    └────────┬───────────────────────┘
             │
             └─→ Library Service
                 │
                 ▼
            ┌──────────────┐
            │ Create Book  │
            │ MySQL        │
            └──────┬───────┘
                   │
                   ▼
            ✅ Book Added
               ID: B001
               │
               ▼
    ┌────────────────────────────────┐
    │ 4. VIEW ALL BOOKS              │
    │ GET /api/books                 │
    └────────┬───────────────────────┘
             │
             ├─→ Book 1: Java (5 copies)
             ├─→ Book 2: DBMS (3 copies)
             └─→ Book 3: OS (2 copies)
               │
               ▼
    ┌────────────────────────────────┐
    │ 5. UPDATE BOOK DETAILS         │
    │ PUT /api/books/{bookId}        │
    │ Body: {copies: 4}              │
    └────────┬───────────────────────┘
             │
             └─→ Update inventory
                 │
                 ▼
            ✅ Book Updated
               │
               ▼
    ┌────────────────────────────────┐
    │ 6. ISSUE BOOK TO STUDENT       │
    │ POST /api/library/issue        │
    │ Body: {studentId, bookId,      │
    │        dueDate}                │
    └────────┬───────────────────────┘
             │
             ├─→ Validate student
             ├─→ Check book availability
             └─→ Create transaction
                 │
                 ▼
            ┌──────────────┐
            │ Update MySQL │
            │ - Copy count │
            │ - Transaction│
            └──────┬───────┘
                   │
                   ▼
            ✅ Book Issued
               │
               ▼
    ┌────────────────────────────────┐
    │ 7. RECEIVE BOOK RETURN         │
    │ POST /api/library/return       │
    │ Body: {studentId, bookId}      │
    └────────┬───────────────────────┘
             │
             ├─→ Check due date
             ├─→ Calculate fine (if overdue)
             └─→ Update transaction
                 │
                 ▼
            ┌──────────────┐
            │ Update MySQL │
            │ - Copy count │
            │ - Mark Return│
            │ - Fine       │
            └──────┬───────┘
                   │
                   ▼
            ✅ Book Received
               Fine: $0 (On time)
               OR Fine: $5 (Overdue)
               │
               ▼
    ┌────────────────────────────────┐
    │ 8. VIEW ALL TRANSACTIONS       │
    │ GET /api/library/transactions  │
    └────────┬───────────────────────┘
             │
             ├─→ John: Java (Issued)
             ├─→ Jane: DBMS (Returned)
             └─→ Mike: OS (Overdue)
               │
               ▼
    ┌────────────────────────────────┐
    │ 9. VIEW OVERDUE BOOKS          │
    │ GET /api/library/overdue       │
    └────────┬───────────────────────┘
             │
             ├─→ Mike: OS (5 days overdue)
             └─→ Tom: Java (2 days overdue)
               │
               ▼
            Send Reminders
               │
               ▼
    ┌────────────────────────────────┐
    │ 10. DELETE BOOK                │
    │ DELETE /api/books/{bookId}     │
    └────────┬───────────────────────┘
             │
             ├─ All copies returned? ✅ Delete
             │
             └─ Issued copies? ❌ Cannot Delete
               │
               ▼
            Action Taken
               │
               ▼
    ┌────────────────────────────────┐
    │ 11. LOGOUT                     │
    │ POST /api/auth/logout          │
    └────────┬───────────────────────┘
             │
             ▼
            END

OPERATIONS SUMMARY:
✅ Can READ: All books, transactions, overdue list
✅ Can CREATE: Books
✅ Can UPDATE: Book details
✅ Can DELETE: Books (if all copies returned)
✅ Can MANAGE: Issue/Return transactions
❌ Cannot: Create courses, manage users
```

---

## 🔴 ADMIN COMPLETE WORKFLOW

```
┌──────────────────────────────────────────────────────────────────┐
│                     ADMIN WORKFLOW                                │
└──────────────────────────────────────────────────────────────────┘

START: Admin accesses system
   │
   ▼
┌─────────────────────────────────┐
│ 1. LOGIN                        │
│ POST /api/auth/login            │
│ Email + Password                │
└────────┬────────────────────────┘
         │
         ▼
        ✅ JWT Token
           Role: ADMIN (FULL ACCESS)
           │
           ▼
    ┌────────────────────────────────┐
    │ 2. ADMIN DASHBOARD             │
    │ GET /api/dashboard/admin       │
    └────────┬───────────────────────┘
             │
             ├─→ Total Users: 500
             ├─→ Total Courses: 50
             ├─→ Total Students: 300
             ├─→ System Reports
             └─→ Analytics
               │
               ▼
    ┌────────────────────────────────┐
    │ 3. USER MANAGEMENT - CREATE    │
    │ POST /api/users                │
    │ Body: {name, email, password,  │
    │        role}                   │
    └────────┬───────────────────────┘
             │
             ├─→ Role: FACULTY
             ├─→ Role: LIBRARIAN
             └─→ Role: STUDENT
               │
               ▼
            ┌──────────────┐
            │ User Service │
            │ Creates User │
            └──────┬───────┘
                   │
                   ▼
            ✅ User Created
               │
               ▼
    ┌────────────────────────────────┐
    │ 4. VIEW ALL USERS              │
    │ GET /api/users                 │
    └────────┬───────────────────────┘
             │
             ├─→ User 1: Dr. Smith (FACULTY)
             ├─→ User 2: Ms. Johnson (LIBRARIAN)
             └─→ User 3: John (STUDENT)
               │
               ▼
    ┌────────────────────────────────┐
    │ 5. UPDATE USER                 │
    │ PUT /api/users/{userId}        │
    │ Body: {name, email, role}      │
    └────────┬───────────────────────┘
             │
             └─→ Change role if needed
                 │
                 ▼
            ✅ User Updated
               │
               ▼
    ┌────────────────────────────────┐
    │ 6. DELETE USER                 │
    │ DELETE /api/users/{userId}     │
    └────────┬───────────────────────┘
             │
             └─→ Remove from system
                 │
                 ▼
            ┌──────────────┐
            │ User Service │
            │ Cascades:    │
            │ -Remove from │
            │  courses     │
            │ -Delete      │
            │  transactions│
            └──────┬───────┘
                   │
                   ▼
            ✅ User Deleted
               │
               ▼
    ┌────────────────────────────────┐
    │ 7. COURSE MANAGEMENT - CREATE  │
    │ POST /api/courses              │
    │ Body: {name, code, credits}    │
    └────────┬───────────────────────┘
             │
             ▼
            ✅ Course Created
               │
               ▼
    ┌────────────────────────────────┐
    │ 8. VIEW ALL COURSES            │
    │ GET /api/courses               │
    └────────┬───────────────────────┘
             │
             ├─→ Course 1: Java (30 students)
             ├─→ Course 2: DBMS (25 students)
             └─→ Course 3: OS (20 students)
               │
               ▼
    ┌────────────────────────────────┐
    │ 9. UPDATE COURSE               │
    │ PUT /api/courses/{courseId}    │
    │ Body: {credits}                │
    └────────┬───────────────────────┘
             │
             ▼
            ✅ Course Updated
               │
               ▼
    ┌────────────────────────────────┐
    │ 10. DELETE COURSE              │
    │ DELETE /api/courses/{courseId} │
    └────────┬───────────────────────┘
             │
             └─→ Cascade unenroll students
                 │
                 ▼
            ✅ Course Deleted
               │
               ▼
    ┌────────────────────────────────┐
    │ 11. VIEW SYSTEM REPORTS        │
    │ GET /api/dashboard/reports     │
    └────────┬───────────────────────┘
             │
             ├─→ User Statistics
             ├─→ Course Statistics
             ├─→ Enrollment Reports
             └─→ Library Reports
               │
               ▼
    ┌────────────────────────────────┐
    │ 12. LOGOUT                     │
    │ POST /api/auth/logout          │
    └────────┬───────────────────────┘
             │
             ▼
            END

OPERATIONS SUMMARY:
✅ Can READ: Everything
✅ Can CREATE: Users, Courses, Books
✅ Can UPDATE: All data
✅ Can DELETE: All data (with cascading)
✅ FULL ACCESS: All services and operations
```

---

## 🔐 PERMISSION MATRIX

```
OPERATION          ADMIN  FACULTY  LIBRARIAN  STUDENT
─────────────────────────────────────────────────────
LOGIN              ✅     ✅       ✅         ✅
VIEW PROFILE       ✅     ✅       ✅         ✅
UPDATE PROFILE     ✅     ✅       ✅         ✅ (own)
─────────────────────────────────────────────────────
CREATE USER        ✅     ❌       ❌         ❌
READ USERS         ✅     ❌       ❌         ❌
UPDATE USER        ✅     ❌       ❌         ❌
DELETE USER        ✅     ❌       ❌         ❌
─────────────────────────────────────────────────────
CREATE COURSE      ✅     ✅       ❌         ❌
READ COURSES       ✅     ✅       ❌         ✅ (own)
UPDATE COURSE      ✅     ✅*      ❌         ❌
DELETE COURSE      ✅     ✅*      ❌         ❌
─────────────────────────────────────────────────────
CREATE STUDENT     ✅     ❌       ❌         ❌
READ STUDENTS      ✅     ✅       ❌         ❌
ASSIGN TO COURSE   ✅     ✅       ❌         ❌
DELETE STUDENT     ✅     ❌       ❌         ❌
─────────────────────────────────────────────────────
CREATE BOOK        ✅     ❌       ✅         ❌
READ BOOKS         ✅     ✅       ✅         ✅
UPDATE BOOK        ✅     ❌       ✅         ❌
DELETE BOOK        ✅     ❌       ✅         ❌
─────────────────────────────────────────────────────
ISSUE BOOK         ✅     ✅       ✅         ✅
RETURN BOOK        ✅     ✅       ✅         ✅
VIEW OVERDUE       ✅     ❌       ✅         ❌
─────────────────────────────────────────────────────
ADMIN DASHBOARD    ✅     ❌       ❌         ❌
FACULTY DASHBOARD  ✅     ✅       ❌         ❌
LIBRARIAN DASHBOARD✅     ❌       ✅         ❌
STUDENT DASHBOARD  ✅     ❌       ❌         ✅
─────────────────────────────────────────────────────

Legend:
✅ = Allowed
❌ = Not Allowed
✅* = Allowed (own courses only)
```

---

**Complete flowcharts for ALL CRUD operations across ALL roles! 📊**


