package com.bt.jpa.service.impl;

import com.bt.jpa.dto.request.CourseSearchRequest;
import com.bt.jpa.entity.CourseEntity;
import com.bt.jpa.repository.CourseRepository;
import com.bt.jpa.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseReposity;
    
    @Autowired
    public CourseServiceImpl(CourseRepository courseReposity) {
        this.courseReposity = courseReposity;
    }
    
    @Override
    public List<CourseEntity> findByDurationGreaterThan(double hours) {
        return courseReposity.findByDurationGreaterThan(hours);
    }
    
    @Override
    public long countCourses() {
        return courseReposity.count();
    }
    
    @Override
    public Optional<CourseEntity> findById(int id) {
        return courseReposity.findById(id);
    }
    
    @Override
    public List<CourseEntity> searchCourses(CourseSearchRequest request) {
        Specification<CourseEntity> spec = Specification.where(null);
        
        Sort sort = Sort.unsorted();
        if (request.getSort() != null) {
            String[] sortParams = request.getSort().split(",");
            sort = Sort.by(sortParams[1].equals("asc") ?
                    Sort.Direction.ASC : Sort.Direction.DESC, sortParams[0]);
        }
        
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                sort
        );
        
        return courseReposity.findAll(spec, pageable);
    }
}
