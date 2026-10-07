package com.internship.student.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.internship.student.repository.StudentRepository;

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