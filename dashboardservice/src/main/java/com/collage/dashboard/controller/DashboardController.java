package com.collage.dashboard.controller;


import com.collage.dashboard.dto.*;
import com.collage.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/admin")
    public AdminDashboardDTO getAdminDashboard() {
        return service.getAdminDashboard();
    }

    @GetMapping("/faculty/{facultyId}")
    public FacultyDashboardDTO getFacultyDashboard(@PathVariable Long facultyId) {
        return service.getFacultyDashboard(facultyId);
    }

    @GetMapping("/librarian")
    public LibrarianDashboardDTO getLibrarianDashboard() {
        return service.getLibrarianDashboard();
    }
}