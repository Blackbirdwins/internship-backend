package com.internship.student;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StudentScheduler {

    private final StudentRepository repository;

    public StudentScheduler(StudentRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 300000)
    public void logStudentCount() {
        long count = repository.count();
        System.out.println(">>> SCHEDULED TASK: Total students = " + count);
    }
}