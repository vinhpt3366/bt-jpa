package com.bt.jpa.dto.response;

import com.bt.jpa.entity.StudentEntity;

import java.util.List;

public class StudentSearchResponse {
    private List<StudentEntity> content;
    private int totalPages;
    private long totalElements;
    private int currentPage;

    public List<StudentEntity> getContent() {
        return content;
    }

    public void setContent(List<StudentEntity> content) {
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
