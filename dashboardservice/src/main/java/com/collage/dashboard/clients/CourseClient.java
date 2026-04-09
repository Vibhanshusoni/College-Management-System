package com.collage.dashboard.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "courseservice", url = "http://localhost:8082")
public interface CourseClient {

    @GetMapping("/api/courses")
    List<Object> getCourses();
}