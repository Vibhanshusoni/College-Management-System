package com.collage.dashboard.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "studentservice", url = "http://localhost:8083")
public interface StudentClient {

    @GetMapping("/api/students")
    List<Object> getStudents();
}