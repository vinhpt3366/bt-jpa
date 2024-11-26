package com.bt.jpa.controller;

import com.bt.jpa.dto.request.StudentSearchRequest;
import com.bt.jpa.entity.CourseEntity;
import com.bt.jpa.entity.RegistrationEntity;
import com.bt.jpa.entity.StudentEntity;
import com.bt.jpa.service.interfaces.RegistrationService;
import com.bt.jpa.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final RegistrationService registrationService;

    @Autowired
    public StudentController(StudentService studentService, RegistrationService registrationService) {
        this.studentService = studentService;
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<?> getStudents(
            @RequestParam(required = false) Integer id
    ) {
        if (id != null) {
            return ResponseEntity.ok(studentService.findById(id));
        }
        return ResponseEntity.ok(studentService.findAll());
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> searchByName(@RequestParam String name) {
//        if (name != null) {
//            return ResponseEntity.ok(studentService.searchByNameContaining(name));
//        }
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentEntity>> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort
    ) {
        StudentSearchRequest searchRequest = new StudentSearchRequest();
        searchRequest.setName(name);
        searchRequest.setAgeFrom(ageFrom);
        searchRequest.setAgeTo(ageTo);
        searchRequest.setPage(page);
        searchRequest.setSize(size);
        searchRequest.setSort(sort);

        List<StudentEntity> result = studentService.searchStudents(searchRequest);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<?> searchByName(@PathVariable int studentId) {
        List<CourseEntity> responseEntities = registrationService.getStudentCourses(studentId);
        if (responseEntities.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Get Courses failed. Please try again!");
        } else {
            return ResponseEntity.ok(responseEntities);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertStudent(@RequestBody StudentEntity student) {
        try {
            if (studentService.existsByEmail(student.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body("Email already exists");
            }

            StudentEntity savedStudent = studentService.save(student);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedStudent);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error creating student: " + e.getMessage());
        }
    }

    @PostMapping("/{studentId}/courses")
    public ResponseEntity<?> insertCourses(@PathVariable int studentId, @RequestBody List<Integer> courseIds) {
        int result = registrationService.registerCourses(studentId, courseIds);
        if (result > 0)
            return ResponseEntity.ok("Courses registration successful");
        else
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Courses registration failed. Please try again!");
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> registerStudentToCourse(@PathVariable int studentId, @PathVariable int courseId) {
        RegistrationEntity responseEntity = registrationService.registerStudentToCourse(studentId, courseId);
        if (responseEntity.getRegistrationID() > 0) {
            return ResponseEntity.ok("Course registration successful!");
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Courses registration failed. Please try again!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable int id, @RequestBody StudentEntity student) {
        Optional<StudentEntity> existingStudent = studentService.findById(id);
        if (existingStudent.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student could not be found");
        }
        student.setId(id);
        StudentEntity updatedStudent = studentService.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int id) {
        if (!studentService.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student could not be found");
        }
        studentService.deleteById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> deleteStudentToCourse(@PathVariable int studentId, @PathVariable int courseId) {
        int result = registrationService.unregisterStudentFromCourse(studentId, courseId);
        if (result > 0) {
            return ResponseEntity.ok("Delete successful!");
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Delete failed. Please try again!");
        }
    }
}
