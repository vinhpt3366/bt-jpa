package com.bt.jpa.repository;

import com.bt.jpa.entity.CourseEntity;
import com.bt.jpa.entity.RegistrationEntity;
import com.bt.jpa.entity.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Integer> {
    @Query("SELECT reg.course FROM registration reg WHERE reg.student.id = :studentId")
    List<CourseEntity> getStudentCourses(@Param("studentId") int studentId);
    
    @Query("SELECT reg.student FROM registration reg WHERE reg.course.id = :courseId")
    List<StudentEntity> getCourseStudents(@Param("courseId") int courseId);
    
    @Query("SELECT COUNT(reg) > 0 FROM registration reg WHERE reg.student.id = :studentId AND reg.course.id = :courseId")
    boolean existsByStudentIdAndCourseId(@Param("studentId") int studentId, @Param("courseId") int courseId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM registration reg WHERE reg.student.id = :studentId AND reg.course.id = :courseId")
    int deleteByStudentIdAndCourseId(@Param("studentId") int studentId, @Param("courseId") int courseId);
}
