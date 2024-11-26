package com.bt.jpa.service.interfaces;

import com.bt.jpa.dto.request.StudentSearchRequest;
import com.bt.jpa.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    StudentEntity save(StudentEntity student);

    List<StudentEntity> findAll();

    Optional<StudentEntity> findById(int id);

    void deleteById(int id);

    boolean existsByEmail(String email);

    boolean existsById(int id);

    List<StudentEntity> searchByNameContaining(String keyword);

    List<StudentEntity> searchStudents(StudentSearchRequest request);
}
