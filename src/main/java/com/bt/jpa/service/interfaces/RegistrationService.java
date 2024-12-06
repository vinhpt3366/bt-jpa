package com.bt.jpa.service.interfaces;

import com.bt.jpa.entity.CourseEntity;
import com.bt.jpa.entity.RegistrationEntity;
import com.bt.jpa.entity.StudentEntity;

import java.util.List;

public interface RegistrationService {
    List<CourseEntity> getStudentCourses(int studentId);
    
    List<StudentEntity> getCourseStudents(int courseId);
    
    int registerCourses(int studentId, List<Integer> courseId);
    
    RegistrationEntity registerStudentToCourse(int studentId, int courseId);
    
    int unregisterStudentFromCourse(int studentId, int courseId);
}
