package com.fletcher.helloai.dto;

import java.util.List;
import java.util.Map;

public record AddDocRequest(List<Doc> docs) {
    public record Doc(String text, Map<String, Object> metadata) {}
}
