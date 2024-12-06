package com.bt.jpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "duration")
    private Double duration;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Double getDuration() {
        return duration;
    }
    
    public void setDuration(Double duration) {
        this.duration = duration;
    }
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<RegistrationEntity> registrationEntities;
}
