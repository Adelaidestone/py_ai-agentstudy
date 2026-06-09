package com.fletcher.helloai.dto;

public record SearchRequest(String query, int topK) {
    public SearchRequest {
        if (topK <= 0) {
            topK = 3;
        }
    }
}
