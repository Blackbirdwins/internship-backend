package com.internship.student.dto;

import com.internship.student.entity.Student;

import java.time.LocalDateTime;

public class StudentResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String course;
    private LocalDateTime createdAt;

    public StudentResponseDTO() {}

    public StudentResponseDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.age = student.getAge();
        this.course = student.getCourse();
        this.createdAt = student.getCreatedAt();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}