package com.bt.jpa.repository;

import com.bt.jpa.entity.CourseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    List<CourseEntity> findByDurationGreaterThan(double hours);
    
    List<CourseEntity> findAll(Specification<CourseEntity> spec, Pageable pageable);
}
