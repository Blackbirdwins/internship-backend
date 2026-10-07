package com.internship.student.controller;

import com.internship.student.dto.StudentRequestDTO;
import com.internship.student.dto.StudentResponseDTO;
import com.internship.student.entity.User;
import com.internship.student.repository.UserRepository;
import com.internship.student.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final UserRepository userRepository;

    public StudentController(StudentService studentService, UserRepository userRepository) {
        this.studentService = studentService;
        this.userRepository = userRepository;
    }

    // Sirf ADMIN — naya student add karo
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> create(@RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(studentService.save(dto));
    }

    // Sirf ADMIN — saare students dekho
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentResponseDTO>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    // Har logged-in user — sirf apna data
    @GetMapping("/me")
    public ResponseEntity<StudentResponseDTO> getMyData(Authentication auth) {
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getStudent() == null) {
            throw new RuntimeException("No student linked to this user");
        }

        return ResponseEntity.ok(new StudentResponseDTO(user.getStudent()));
    }

    // Sirf ADMIN — ek student ID se dekho
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    // Sirf ADMIN — update
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long id,
                                                     @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    // Sirf ADMIN — delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}