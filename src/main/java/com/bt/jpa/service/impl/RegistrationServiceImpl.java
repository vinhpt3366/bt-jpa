package com.bt.jpa.service.impl;

import com.bt.jpa.entity.CourseEntity;
import com.bt.jpa.entity.RegistrationEntity;
import com.bt.jpa.entity.StudentEntity;
import com.bt.jpa.repository.CourseRepository;
import com.bt.jpa.repository.RegistrationRepository;
import com.bt.jpa.repository.StudentRepository;
import com.bt.jpa.service.interfaces.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final CourseRepository courseReposity;
    private final StudentRepository studentRepository;
    
    private final RegistrationRepository registrationRepository;
    
    
    @Autowired
    public RegistrationServiceImpl(CourseRepository courseReposity, StudentRepository studentRepository, RegistrationRepository registrationRepository) {
        this.courseReposity = courseReposity;
        this.studentRepository = studentRepository;
        this.registrationRepository = registrationRepository;
    }
    
    @Override
    public List<CourseEntity> getStudentCourses(int studentId) {
        return registrationRepository.getStudentCourses(studentId);
    }
    
    @Override
    public List<StudentEntity> getCourseStudents(int courseId) {
        return registrationRepository.getCourseStudents(courseId);
    }
    
    @Override
    public int registerCourses(int studentId, List<Integer> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            return -1;
        }
        Optional<StudentEntity> student = studentRepository.findById(studentId);
        int result = -1;
        if (student.isEmpty()) {
            result = 0;
        } else {
            for (Integer courseId : courseIds) {
                Optional<CourseEntity> course = courseReposity.findById(courseId);
                if (course.isEmpty() || registrationRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
                    result = 0;
                } else {
                    RegistrationEntity registration = new RegistrationEntity();
                    registration.setStudent(student.get());
                    registration.setCourse(course.get());
                    registration.setRegistrationDate(LocalDate.now());
                    result = 1;
                    registrationRepository.save(registration);
                }
            }
        }
        return result;
    }
    
    @Override
    public RegistrationEntity registerStudentToCourse(int studentId, int courseId) {
        if (!(courseId > 0)) {
            throw new IllegalArgumentException("not courseId");
        }
        Optional<StudentEntity> student = studentRepository.findById(studentId);
        Optional<CourseEntity> course = courseReposity.findById(courseId);
        RegistrationEntity registration = new RegistrationEntity();
        if (student.isEmpty() || course.isEmpty() || registrationRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            System.out.println("error registerStudentToCourse");
        } else {
            registration.setStudent(student.get());
            registration.setCourse(course.get());
            registration.setRegistrationDate(LocalDate.now());
            registrationRepository.save(registration);
        }
        return registration;
    }
    
    @Override
    public int unregisterStudentFromCourse(int studentId, int courseId) {
        boolean registration = registrationRepository.existsByStudentIdAndCourseId(studentId, courseId);
        int result = -1;
        if (registration) {
            result = registrationRepository.deleteByStudentIdAndCourseId(studentId, courseId);
        }
        return result;
    }
    
}
