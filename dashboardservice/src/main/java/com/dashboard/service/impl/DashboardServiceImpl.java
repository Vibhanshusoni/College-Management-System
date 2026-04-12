package com.dashboard.service;

import com.dashboard.clients.*;
import com.dashboard.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final UserServiceClient userServiceClient;
    private final StudentServiceClient studentServiceClient;
    private final FacultyServiceClient facultyServiceClient;
    private final CourseServiceClient courseServiceClient;
    private final DepartmentServiceClient departmentServiceClient;
    private final LibraryServiceClient libraryServiceClient;
    private final AttendanceServiceClient attendanceServiceClient;

    /**
     * ADMIN DASHBOARD
     */
    public AdminDashboardDTO getAdminDashboard() {

        long totalStudents = studentServiceClient.getTotalStudentsCount()
                .getOrDefault("totalStudents", 0L);

        long totalFaculty = facultyServiceClient.getTotalFacultyCount()
                .getOrDefault("totalFaculty", 0L);

        long totalDepartments = departmentServiceClient.getTotalDepartmentsCount()
                .getOrDefault("totalDepartments", 0L);

        long totalCourses = courseServiceClient.getTotalCoursesCount()
                .getOrDefault("totalCourses", 0L);

        Map<String, Long> userDistribution = new HashMap<>();
        userDistribution.put("students", totalStudents);
        userDistribution.put("faculty", totalFaculty);

        return AdminDashboardDTO.builder()
                .totalStudents(totalStudents)
                .totalFaculty(totalFaculty)
                .totalDepartments(totalDepartments)
                .totalCourses(totalCourses)
                .userDistribution(userDistribution)
                .build();
    }

    /**
     * STUDENT DASHBOARD
     */
    public StudentDashboardDTO getStudentDashboard(String studentId) {

        List<?> attendanceList = attendanceServiceClient.getStudentAttendance(studentId);

        double percentage = calculateAttendance(attendanceList);

        return StudentDashboardDTO.builder()
                .studentName(studentId)
                .attendancePercentage(percentage)
                .booksIssued(0)
                .booksReturned(0)
                .build();
    }

    /**
     * FACULTY DASHBOARD
     */
    public FacultyDashboardDTO getFacultyDashboard(String facultyId) {

        return FacultyDashboardDTO.builder()
                .facultyName(facultyId)
                .totalClassesAssigned(0)
                .totalStudents(0)
                .attendancePercentage(0.0)
                .build();
    }

    /**
     * LIBRARIAN DASHBOARD
     */
    public LibrarianDashboardDTO getLibrarianDashboard() {

        Map<String, Object> data = libraryServiceClient.getLibraryDashboard();

        return LibrarianDashboardDTO.builder()
                .totalBooks((Integer) data.getOrDefault("totalBooks", 0))
                .availableBooks((Integer) data.getOrDefault("availableBooks", 0))
                .issuedBooks((Integer) data.getOrDefault("issuedBooks", 0))
                .build();
    }

    /**
     * ROLE BASED DASHBOARD
     */
    public DashboardDTO getDashboardByRole(String role, String userId) {

        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setLastUpdated(LocalDateTime.now());

        switch (role.toUpperCase()) {

            case "ADMIN":
                dashboard.setAdminDashboard(getAdminDashboard());
                break;

            case "STUDENT":
                dashboard.setStudentDashboard(getStudentDashboard(userId));
                break;

            case "FACULTY":
                dashboard.setFacultyDashboard(getFacultyDashboard(userId));
                break;

            case "LIBRARIAN":
                dashboard.setLibrarianDashboard(getLibrarianDashboard());
                break;
        }

        return dashboard;
    }

    /**
     * SIMPLE ATTENDANCE CALCULATION
     */
    private double calculateAttendance(List<?> list) {

        if (list == null || list.isEmpty()) return 0.0;

        long present = list.stream()
                .filter(obj -> obj.toString().contains("PRESENT"))
                .count();

        return (double) present / list.size() * 100;
    }
}