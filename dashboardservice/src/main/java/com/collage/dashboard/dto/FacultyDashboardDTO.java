package com.collage.dashboard.dto;

import lombok.Data;
import java.util.List;

@Data
public class FacultyDashboardDTO {

    private List<Object> courses;
    private List<Object> students;
}