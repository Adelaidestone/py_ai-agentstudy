package com.fletcher.docsearch.service;

import com.fletcher.docsearch.dto.AddDocRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文档检索服务：封装 VectorStore 的写入与检索能力。
 *
 * <p>VectorStore 内部会自动调用 EmbeddingModel 把文本向量化后写入 Zilliz/Milvus。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocSearchService {

    private final VectorStore vectorStore;

    /** 写入文档（自动 embedding + 入库） */
    public int add(List<AddDocRequest.Doc> docs) {
        if (docs == null || docs.isEmpty()) {
            return 0;
        }
        List<Document> documents = docs.stream()
                .map(d -> new Document(
                        d.text(),
                        d.metadata() == null ? new HashMap<>() : new HashMap<>(d.metadata())
                ))
                .toList();
        vectorStore.add(documents);
        log.info("写入 {} 条文档到 VectorStore", documents.size());
        return documents.size();
    }

    /** 语义检索 */
    public List<Document> search(String query, int topK) {
        log.info("语义检索: query={}, topK={}", query, topK);
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build()
        );
    }

    /** 灌入一批 Demo 文档，方便快速验证全链路 */
    public int seedDemo() {
        List<AddDocRequest.Doc> demo = List.of(
                new AddDocRequest.Doc(
                        "Spring AI 是 Spring 官方推出的 AI 应用开发框架，对接 OpenAI、通义、Ollama 等多家模型。",
                        Map.of("topic", "spring-ai")),
                new AddDocRequest.Doc(
                        "Milvus 是开源的云原生向量数据库，支持十亿级向量的高性能检索。",
                        Map.of("topic", "milvus")),
                new AddDocRequest.Doc(
                        "Zilliz Cloud 是 Milvus 团队提供的全托管向量数据库服务，免去运维成本，支持 Serverless 免费集群。",
                        Map.of("topic", "zilliz")),
                new AddDocRequest.Doc(
                        "RAG（检索增强生成）通过把外部知识库的检索结果注入 Prompt，缓解 LLM 的幻觉问题，让回答有据可查。",
                        Map.of("topic", "rag")),
                new AddDocRequest.Doc(
                        "Embedding 模型把一段文本映射为一个高维向量，语义相近的文本在向量空间中距离更近。",
                        Map.of("topic", "embedding"))
        );
        return add(demo);
    }

    /** 健康检查：调一次检索来确认 VectorStore + Embedding + Zilliz 三者全部可用 */
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Document> probe = vectorStore.similaritySearch(
                    SearchRequest.builder().query("健康检查").topK(1).build()
            );
            result.put("ok", true);
            result.put("vectorStore", vectorStore.getClass().getSimpleName());
            result.put("probeHits", probe.size());
            result.put("message", probe.isEmpty()
                    ? "连通正常，但集合为空，可调用 POST /api/demo/seed 灌入示例数据"
                    : "连通正常，集合中已有数据");
        } catch (Exception e) {
            log.error("Zilliz 健康检查失败", e);
            result.put("ok", false);
            result.put("error", e.getClass().getSimpleName());
            result.put("message", e.getMessage());
        }
        return result;
    }
}
