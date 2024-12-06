package com.bt.jpa.specification;

import com.bt.jpa.entity.StudentEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StudentSpecification {
    public static Specification<StudentEntity> hasNameLike(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;
            return cb.like(root.get("name"), "%" + name + "%");
        };
    }
    
    public static Specification<StudentEntity> hasAgeBetween(Integer ageFrom, Integer ageTo) {
        return (root, query, cb) -> {
            if (ageFrom == null || ageTo == null) return null;
            return cb.between(root.get("age"), ageFrom, ageTo);
        };
    }
    
    public static Specification<StudentEntity> hasEmailEndingWith(String emailDomain) {
        return (root, query, cb) -> {
            if (emailDomain == null) return null;
            return cb.like(root.get("email"), "%" + emailDomain);
        };
    }
}
