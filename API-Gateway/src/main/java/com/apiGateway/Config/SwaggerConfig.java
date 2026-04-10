package com.apiGateway.Config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gatewayAPI() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("API Gateway"))
                .info(new Info()
                        .title("College Management System - API Gateway")
                        .version("1.0")
                        .description("Unified API Gateway for all College Management Services")
                        .contact(new Contact()
                                .name("College Management System")
                                .email("admin@college.edu"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }

    // Auth Service APIs
    @Bean
    public GroupedOpenApi authGroup() {
        return GroupedOpenApi.builder()
                .group("auth-service")
                .displayName("🔐 Authentication Service")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    // User Service APIs
    @Bean
    public GroupedOpenApi userGroup() {
        return GroupedOpenApi.builder()
                .group("user-service")
                .displayName("👥 User Management Service")
                .pathsToMatch("/api/users/**")
                .build();
    }

    // Course Service APIs
    @Bean
    public GroupedOpenApi courseGroup() {
        return GroupedOpenApi.builder()
                .group("course-service")
                .displayName("📚 Course Management Service")
                .pathsToMatch("/api/courses/**")
                .build();
    }

    // Student Service APIs
    @Bean
    public GroupedOpenApi studentGroup() {
        return GroupedOpenApi.builder()
                .group("student-service")
                .displayName("🎓 Student Management Service")
                .pathsToMatch("/api/students/**")
                .build();
    }

    // Library Service APIs
    @Bean
    public GroupedOpenApi libraryGroup() {
        return GroupedOpenApi.builder()
                .group("library-service")
                .displayName("📖 Library Management Service")
                .pathsToMatch("/api/library/**")
                .build();
    }

    // Dashboard Service APIs
    @Bean
    public GroupedOpenApi dashboardGroup() {
        return GroupedOpenApi.builder()
                .group("dashboard-service")
                .displayName("📊 Dashboard & Analytics Service")
                .pathsToMatch("/api/dashboard/**")
                .build();
    }
}
