package com.fletcher.docsearch.dto;

import java.util.List;
import java.util.Map;

/**
 * 写入文档请求体
 *
 * <pre>
 * {
 *   "docs": [
 *     {"text": "Spring AI 是 Spring 生态的 AI 框架", "metadata": {"source": "intro"}},
 *     {"text": "Milvus 是开源向量数据库"}
 *   ]
 * }
 * </pre>
 */
public record AddDocRequest(List<Doc> docs) {
    public record Doc(String text, Map<String, Object> metadata) {}
}
