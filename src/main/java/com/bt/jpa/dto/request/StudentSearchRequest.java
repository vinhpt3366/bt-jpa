package com.bt.jpa.dto.request;

public class StudentSearchRequest {
    private String name;
    private Integer ageFrom;
    private Integer ageTo;
    private String email;
    private int page = 0;
    private int size = 10;
    private String sort;
    
    public StudentSearchRequest() {
    }
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getAgeFrom() {
        return ageFrom;
    }
    
    public void setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
    }
    
    public Integer getAgeTo() {
        return ageTo;
    }
    
    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public String getSort() {
        return sort;
    }
    
    public void setSort(String sort) {
        this.sort = sort;
    }
}
