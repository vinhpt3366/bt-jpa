package com.bt.jpa.service.impl;

import com.bt.jpa.dto.request.StudentSearchRequest;
import com.bt.jpa.entity.StudentEntity;
import com.bt.jpa.repository.StudentRepository;
import com.bt.jpa.service.interfaces.StudentService;
import com.bt.jpa.specification.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    @Override
    public StudentEntity save(StudentEntity student) {
        return studentRepository.save(student);
    }
    
    @Override
    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }
    
    @Override
    public Optional<StudentEntity> findById(int id) {
        return studentRepository.findById(id);
    }
    
    @Override
    public void deleteById(int id) {
        studentRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsById(int id) {
        return studentRepository.existsById(id);
    }
    
    @Override
    public List<StudentEntity> searchByNameContaining(String keyword) {
        return studentRepository.searchByNameContaining(keyword);
    }
    
    @Override
    public List<StudentEntity> searchStudents(StudentSearchRequest request) {
        Specification<StudentEntity> spec = Specification.where(null);
        
        if (request.getName() != null) {
            spec = spec.and(StudentSpecification.hasNameLike(request.getName()));
        }
        
        if (request.getAgeFrom() != null && request.getAgeTo() != null) {
            spec = spec.and(StudentSpecification.hasAgeBetween(
                    request.getAgeFrom(),
                    request.getAgeTo()
            ));
        }
        
        if (request.getEmail() != null) {
            spec = spec.and(StudentSpecification.hasEmailEndingWith(request.getEmail()));
        }
        
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
        
        return studentRepository.findAll(spec, pageable).getContent();
    }
}
