package com.facultyService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Facultyservice", url = "http://localhost:8083")
public interface FacultyClient {

    @GetMapping("/api/Facultys/{id}")
    Object getFacultyById(@PathVariable Long id);

    void getStudentById(Long studentId);
}
