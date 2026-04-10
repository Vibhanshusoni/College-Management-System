package com.facultyService.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacultyDTO {
    private Long id;
    private String universityId;
    private String facultyName;
    private String facultyEmail;
    private String facultyPhoneNumber;
    private String department;
    private String subRole;
    private String attendanceCalendar;
    private Float attendancePercentage;
    private Integer booksIssued;
    private Integer booksReturned;
    private String scheduleData;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}