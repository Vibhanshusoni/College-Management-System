package com.dashboard.clients;


import com.dashboard.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// User-Service Client
@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/api/users/count/by-role/{role}")
    Map<String, Object> getUserCountByRole(@PathVariable String role);
    @GetMapping("/api/users/internal/by-username")
    ResponseEntity<ApiResponse> lookupUserByUsername(@RequestParam String username);

}
