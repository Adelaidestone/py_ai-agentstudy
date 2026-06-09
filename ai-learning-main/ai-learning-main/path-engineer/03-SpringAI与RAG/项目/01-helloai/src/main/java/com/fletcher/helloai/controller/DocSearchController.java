package com.fletcher.helloai.controller;

import com.fletcher.helloai.dto.AddDocRequest;
import com.fletcher.helloai.dto.SearchRequest;
import com.fletcher.helloai.service.DocSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文档语义搜索 API
 *
 * <pre>
 * 测试流程：
 *  1. 写入：POST /api/docs
 *  2. 检索：GET  /api/search?q=向量数据库&topK=3
 *  3. 验证 Zilliz 连通性：GET /api/health/zilliz
 * </pre>
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DocSearchController {
    private final DocSearchService docSearchService;

    /** 写入文档（自动向量化 + 入 Zilliz） */
    @PostMapping("/docs")
    public Map<String, Object> add(@RequestBody AddDocRequest req) {
        int count = docSearchService.add(req.docs());
        return Map.of("ok", true, "count", count);
    }

    /** 语义检索
     * curl  -N -G  'http://127.0.0.1:8080/api/search' --data-urlencode 'q=数据库'
     * */
    @GetMapping("/search")
    public List<Document> search(@RequestParam("q") String query,
                                 @RequestParam(value = "topK", defaultValue = "3") int topK) {
        return docSearchService.search(query, topK);
    }

    /** 一键灌入 Demo 数据，方便快速验证 */
    @PostMapping("/demo/seed")
    public Map<String, Object> seed() {
        int count = docSearchService.seedDemo();
        return Map.of("ok", true, "seeded", count);
    }

    /** Zilliz 连通性 + 集合状态健康检查 */
    @GetMapping("/health/zilliz")
    public Map<String, Object> health() {
        return docSearchService.health();
    }

    /** 用搜索请求体的方式检索（带过滤元数据） */
    @PostMapping("/search")
    public List<Document> searchWithBody(@RequestBody SearchRequest req) {
        return docSearchService.search(req.query(), req.topK());
    }
}
