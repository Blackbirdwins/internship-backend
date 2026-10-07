package com.internship.student;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@Valid @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(studentService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}