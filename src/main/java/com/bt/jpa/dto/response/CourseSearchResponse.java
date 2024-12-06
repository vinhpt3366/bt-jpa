package com.bt.jpa.dto.response;

import com.bt.jpa.entity.CourseEntity;

import java.util.List;

public class CourseSearchResponse {
    private List<CourseEntity> content;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    
    public List<CourseEntity> getContent() {
        return content;
    }
    
    public void setContent(List<CourseEntity> content) {
        this.content = content;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    public long getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
