package com.bt.jpa.service.interfaces;

import com.bt.jpa.dto.request.CourseSearchRequest;
import com.bt.jpa.entity.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseEntity> findByDurationGreaterThan(double hours);
    
    long countCourses();
    
    Optional<CourseEntity> findById(int id);
    
    List<CourseEntity> searchCourses(CourseSearchRequest request);
}
