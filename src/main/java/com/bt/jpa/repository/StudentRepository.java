package com.bt.jpa.repository;

import com.bt.jpa.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity> {
    boolean existsByEmail(String email);
    
    boolean existsById(int id);
    
    List<StudentEntity> searchByNameContaining(String keyword);
}
