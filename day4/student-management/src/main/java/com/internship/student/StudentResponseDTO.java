package com.internship.student;

import java.time.LocalDateTime;

public class StudentResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String course;
    private LocalDateTime createdAt;

    public StudentResponseDTO(Student s) {
        this.id = s.getId();
        this.name = s.getName();
        this.email = s.getEmail();
        this.age = s.getAge();
        this.course = s.getCourse();
        this.createdAt = s.getCreatedAt();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Integer getAge() { return age; }
    public String getCourse() { return course; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}