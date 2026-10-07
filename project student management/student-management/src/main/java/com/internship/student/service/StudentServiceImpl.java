package com.internship.student.service;

import com.internship.student.entity.Student;
import com.internship.student.dto.StudentRequestDTO;
import com.internship.student.dto.StudentResponseDTO;
import com.internship.student.event.StudentCreatedEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.internship.student.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public StudentServiceImpl(StudentRepository repository,
                              ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @CacheEvict(value = "students", allEntries = true)
    public StudentResponseDTO save(StudentRequestDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        student.setCourse(dto.getCourse());

        Student saved = repository.save(student);
        eventPublisher.publishEvent(new StudentCreatedEvent(this, saved));
        return new StudentResponseDTO(saved);
    }

    @Override
    @Cacheable(value = "students")
    public List<StudentResponseDTO> getAll() {
        System.out.println(">>> CACHE MISS - Fetching all students from DATABASE");
        return repository.findAll()
                .stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO getById(Long id) {
        Student s = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        return new StudentResponseDTO(s);
    }

    @Override
    @CacheEvict(value = "students", allEntries = true)
    public StudentResponseDTO update(Long id, StudentRequestDTO dto) {
        Student s = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));

        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setAge(dto.getAge());
        s.setCourse(dto.getCourse());

        return new StudentResponseDTO(repository.save(s));
    }

    @Override
    @CacheEvict(value = "students", allEntries = true)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found: " + id);
        }
        repository.deleteById(id);
    }
}