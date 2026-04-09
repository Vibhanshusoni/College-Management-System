package com.collage.dashboard.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "libraryservice", url = "http://localhost:8084")
public interface LibraryClient {

    @GetMapping("/api/library/transactions")
    List<Object> getTransactions();
}