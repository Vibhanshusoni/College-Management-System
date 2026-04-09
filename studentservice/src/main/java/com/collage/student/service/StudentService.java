package com.collage.student.service;


import com.collage.student.client.CourseClient;
import com.collage.student.entity.Student;
import com.collage.student.exception.ResourceNotFoundException;
import com.collage.student.repository.StudentRepository;
import com.collage.student.validation.StudentValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository repository;
    @Autowired
    private CourseClient courseClient;


    public Student addStudent(StudentValidation request) {
        logger.info("Adding student: {}", request.getName());
        try {
            courseClient.getCourseById(request.getCourseId());
        } catch (Exception e) {
            throw new RuntimeException("Course not found with id: " + request.getCourseId());
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setCourseId(request.getCourseId());
        return repository.save(student);
    }

    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return repository.findAll();
    }

    public Student getStudentById(Long id) {
        logger.info("Fetching student with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

    }

    public Student updateStudent(Long id, StudentValidation request) {
        logger.info("Updating student with ID: {}", id);
        Student existing = getStudentById(id);
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setCourseId(request.getCourseId());
        return repository.save(existing);
    }

    public void deleteStudent(Long id) {
        logger.info("Deleting student with ID: {}", id);
        repository.deleteById(id);
    }
}