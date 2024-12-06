package com.bt.jpa.controller;

import com.bt.jpa.dto.request.CourseSearchRequest;
import com.bt.jpa.entity.CourseEntity;
import com.bt.jpa.entity.StudentEntity;
import com.bt.jpa.service.interfaces.CourseService;
import com.bt.jpa.service.interfaces.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    private final RegistrationService registrationService;
    
    @Autowired
    public CourseController(CourseService courseService, RegistrationService registrationService) {
        this.courseService = courseService;
        this.registrationService = registrationService;
    }

//    @GetMapping
//    public ResponseEntity<?> getCourseByDurationGreaterThan(@RequestParam double durationGreaterThan) {
//        if (durationGreaterThan >= 0) {
//            return ResponseEntity.ok(courseService.findByDurationGreaterThan(durationGreaterThan));
//        }
//        return ResponseEntity.notFound().build();
//    }
    
    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(required = false) Double durationGreaterThan,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "duration,desc") String sort
    ) {
        if (durationGreaterThan != null) {
            if (durationGreaterThan >= 0) {
                return ResponseEntity.ok(courseService.findByDurationGreaterThan(durationGreaterThan));
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Duration must be greater than 0");
        }
        
        CourseSearchRequest courseSearchRequest = new CourseSearchRequest();
        courseSearchRequest.setPage(page);
        courseSearchRequest.setSize(size);
        courseSearchRequest.setSort(sort);
        
        List<CourseEntity> coursePage = courseService.searchCourses(courseSearchRequest);
        
        return ResponseEntity.ok(coursePage);
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> getCountCourses() {
        return ResponseEntity.ok(courseService.countCourses());
    }
    
    @GetMapping("/{courseId}/students")
    public ResponseEntity<?> searchByName(@PathVariable int courseId) {
        List<StudentEntity> responseEntities = registrationService.getCourseStudents(courseId);
        if (responseEntities.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Get Students failed. Please try again!");
        } else {
            return ResponseEntity.ok(responseEntities);
            
        }
    }
}
