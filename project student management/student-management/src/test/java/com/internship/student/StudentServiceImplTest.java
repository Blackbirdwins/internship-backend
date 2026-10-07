package com.internship.student;

import com.internship.student.dto.StudentRequestDTO;
import com.internship.student.dto.StudentResponseDTO;
import com.internship.student.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import com.internship.student.repository.StudentRepository;
import com.internship.student.service.StudentServiceImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private StudentServiceImpl service;

    @Test
    void shouldSaveStudent() {
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("Anas");
        dto.setEmail("anas@test.com");
        dto.setAge(20);
        dto.setCourse("CS");

        Student saved = new Student();
        saved.setId(1L);
        saved.setName("Anas");
        saved.setEmail("anas@test.com");

        when(repository.save(any(Student.class))).thenReturn(saved);

        StudentResponseDTO result = service.save(dto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Anas");
        verify(repository, times(1)).save(any(Student.class));
        verify(eventPublisher, times(1)).publishEvent(any());
    }

    @Test
    void shouldThrowWhenNameBlank() {
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("");
        dto.setEmail("a@b.com");

        assertThatThrownBy(() -> service.save(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name");
    }

    @Test
    void shouldThrowWhenEmailBlank() {
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName("Anas");
        dto.setEmail("");

        assertThatThrownBy(() -> service.save(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email");
    }

    @Test
    void shouldGetAllStudents() {
        Student s1 = new Student(); s1.setId(1L); s1.setName("A");
        Student s2 = new Student(); s2.setId(2L); s2.setName("B");
        when(repository.findAll()).thenReturn(Arrays.asList(s1, s2));

        assertThat(service.getAll()).hasSize(2);
    }

    @Test
    void shouldGetById() {
        Student s = new Student();
        s.setId(1L);
        s.setName("Anas");
        when(repository.findById(1L)).thenReturn(Optional.of(s));

        assertThat(service.getById(1L).getName()).isEqualTo("Anas");
    }

    @Test
    void shouldThrowWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldDeleteStudent() {
        when(repository.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(repository).deleteById(1L);
    }
}