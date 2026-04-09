package com.collage.dashboard.service;


import com.collage.dashboard.clients.*;
import com.collage.dashboard.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private LibraryClient libraryClient;

    // ADMIN DASHBOARD
    public AdminDashboardDTO getAdminDashboard() {

        List<Object> courses = courseClient.getCourses();
        List<Object> students = studentClient.getStudents();
        List<Object> transactions = libraryClient.getTransactions();

        AdminDashboardDTO dto = new AdminDashboardDTO();
        dto.setTotalCourses(courses.size());
        dto.setTotalStudents(students.size());
        dto.setBooksIssued(transactions.size());

        return dto;
    }

    // FACULTY DASHBOARD
    public FacultyDashboardDTO getFacultyDashboard(Long facultyId) {

        List<Object> courses = courseClient.getCourses();
        List<Object> students = studentClient.getStudents();

        FacultyDashboardDTO dto = new FacultyDashboardDTO();
        dto.setCourses(courses);
        dto.setStudents(students);

        return dto;
    }

    // LIBRARIAN DASHBOARD
    public LibrarianDashboardDTO getLibrarianDashboard() {

        List<Object> transactions = libraryClient.getTransactions();

        long overdue = transactions.stream().filter(tx -> {
            // assume map structure (simple logic)
            return true; // customize later
        }).count();

        LibrarianDashboardDTO dto = new LibrarianDashboardDTO();
        dto.setTotalIssued(transactions.size());
        dto.setOverdueBooks(overdue);

        return dto;
    }
}