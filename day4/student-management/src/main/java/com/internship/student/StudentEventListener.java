package com.internship.student;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {

    @EventListener
    public void handleStudentCreated(StudentCreatedEvent event) {
        Student s = event.getStudent();
        System.out.println(">>> EVENT RECEIVED: Student created - id=" + s.getId()
                + ", name='" + s.getName() + "', course='" + s.getCourse() + "'");
    }
}