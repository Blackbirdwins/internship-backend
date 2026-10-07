package com.internship.student.event;

import com.internship.student.entity.Student;
import org.springframework.context.ApplicationEvent;

public class StudentCreatedEvent extends ApplicationEvent {

    private final Student student;

    public StudentCreatedEvent(Object source, Student student) {
        super(source);
        this.student = student;
    }

    public Student getStudent() { return student; }
}