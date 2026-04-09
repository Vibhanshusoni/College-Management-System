package com.collage.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StudentserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentserviceApplication.class, args);
	}

}
