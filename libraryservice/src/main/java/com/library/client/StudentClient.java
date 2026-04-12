package com.collage.library.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "studentservice", url = "http://localhost:8083")
public interface StudentClient {

    @GetMapping("/api/students/{id}")
    Object getStudentById(@PathVariable Long id);
}
