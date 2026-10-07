package com.internship.student.service;

import com.internship.student.dto.StudentRequestDTO;
import com.internship.student.dto.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    StudentResponseDTO save(StudentRequestDTO dto);
    List<StudentResponseDTO> getAll();
    StudentResponseDTO getById(Long id);
    StudentResponseDTO update(Long id, StudentRequestDTO dto);
    void delete(Long id);
}