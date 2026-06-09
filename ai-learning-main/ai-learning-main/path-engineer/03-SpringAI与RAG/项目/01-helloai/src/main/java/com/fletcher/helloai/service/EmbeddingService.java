package com.fletcher.helloai.service;

import com.alibaba.dashscope.embeddings.TextEmbedding;
import com.alibaba.dashscope.embeddings.TextEmbeddingParam;
import com.alibaba.dashscope.embeddings.TextEmbeddingResult;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {
    // 复用通义/OpenAI 兼容协议的 api-key 配置；提供默认空串避免占位符解析失败
    @Value("${spring.ai.openai.api-key:}")
    private String apiKey;

    /** 调用通义 text-embedding-v3，返回 1024 维向量 */
    public List<Double> embed(String text) throws NoApiKeyException {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "DashScope api-key 未配置，请设置环境变量 DASHSCOPE_API_KEY");
        }
        TextEmbeddingParam param = TextEmbeddingParam.builder()
                .apiKey(apiKey)
                .model(TextEmbedding.Models.TEXT_EMBEDDING_V3)  // 维度 1024
                .text(text)
                .build();

        TextEmbeddingResult result = new TextEmbedding().call(param);
        // 取第一条文本的向量
        return result.getOutput().getEmbeddings().get(0).getEmbedding();
    }

    public static void main(String[] args) throws NoApiKeyException {
        // 注意：main 入口不走 Spring 容器，apiKey 不会被注入
        // 这里直接读环境变量，方便手动跑相似度小测试
        EmbeddingService service = new EmbeddingService();
        service.apiKey = System.getenv("DASHSCOPE_API_KEY");
        var v1 = service.embed("我喜欢吃苹果");
        var v2 = service.embed("爱吃苹果");
        var v3 = service.embed("今天股市跌了");
        System.out.println("v1 vs v2 = " + cosineSimilarity(v1, v2));  // 应该 > 0.7
        System.out.println("v1 vs v3 = " + cosineSimilarity(v1, v3));  // 应该 < 0.4
    }

    public static double cosineSimilarity(List<Double> a, List<Double> b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("dim mismatch: " + a.size() + " vs " + b.size());
        }
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.size(); i++) {
            dot += a.get(i) * b.get(i);
            normA += a.get(i) * a.get(i);
            normB += b.get(i) * b.get(i);
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}


