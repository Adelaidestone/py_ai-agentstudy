package com.fletcher.helloai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ModelRouterService {
    private final ChatClient qwenClient;
    private final ChatClient ollamaClient;
    private final ChatClient deepseekClient;
    private final ChatClient mimoClient;

    public ModelRouterService(@Qualifier("qwenClient") ChatClient qwenClient,
                              @Qualifier("ollamaClient") ChatClient ollamaClient,
                              @Qualifier("deepseekClient") ChatClient deepseekClient,
                              @Qualifier("mimoClient") ChatClient mimoClient) {
        this.qwenClient = qwenClient;
        this.ollamaClient = ollamaClient;
        this.deepseekClient = deepseekClient;
        this.mimoClient = mimoClient;
    }

    private enum Complexity { SIMPLE, COMPLEX, SENSITIVE }

    /**
     * 按复杂度路由
     */
    public String routeByComplexity(String question) {
        Complexity c = judgeComplexity(question);
        return switch (c) {
            case SIMPLE -> {
                System.out.println("[ROUTER] SIMPLE → qwen-turbo");
                yield qwenClient.prompt().user(question).call().content();
            }
            case COMPLEX -> {
                System.out.println("[ROUTER] COMPLEX → deepseek");
                // 这里为了简化，依然用 deepseekClient 但可以切换模型配置
                yield mimoClient.prompt().user(question).call().content();
            }
            case SENSITIVE -> {
                System.out.println("[ROUTER] SENSITIVE → Ollama 本地");
                yield ollamaClient.prompt().user(question).call().content();
            }
        };
    }

    /**
     * 按"敏感词"判断是否走本地
     */
    public String routeByPrivacy(String question) {
        boolean sensitive = containsSensitiveKeyword(question);
        if (sensitive) {
            System.out.println("[ROUTER] SENSITIVE → Ollama");
            return ollamaClient.prompt().user(question).call().content();
        }
        System.out.println("[ROUTER] PUBLIC → qwen");
        return qwenClient.prompt().user(question).call().content();
    }

    /**
     * 带降级的调用：主模型挂了自动切备用
     */
    public String callWithFallback(String question) {
        try {
            return qwenClient.prompt().user(question).call().content();
        } catch (Exception e) {
            System.err.println("[FALLBACK] 通义挂了，切 Ollama: " + e.getMessage());
            return ollamaClient.prompt().user(question).call().content();
        }
    }

    public String routeByModel(String question) {
        String lowerCase = Objects.requireNonNull(qwenClient.prompt().system("""
                你是一个任务分类器。下面用户问题属于哪一类？
                只返回分类 ID，不要多说。
                分类 ID：
                - simple：闲聊、问候、事实性问答
                - code：代码相关
                - reasoning：数学推理、分析
                - sensitive：涉及敏感信息（合同、密码、薪资）
                """).user(question).call().content()).trim().toLowerCase();
        System.out.println("[ROUTER] " + lowerCase + " → " + question);
        return switch (lowerCase){
            case "code", "reasoning" -> mimoClient.prompt().user(question).call().content();
            case "sensitive" -> ollamaClient.prompt().user(question).call().content();
            default -> qwenClient.prompt().user(question).call().content();
        };
    }

    private Complexity judgeComplexity(String question) {
        int len = question.length();
        boolean hasCodeKeyword = question.contains("代码") || question.contains("算法")
                || question.contains("实现") || question.contains("写一段");

        // 简单规则：长文本 or 带代码关键词 = 复杂
        if (len > 100 || hasCodeKeyword) return Complexity.COMPLEX;
        return Complexity.SIMPLE;
    }

    private boolean containsSensitiveKeyword(String q) {
        if (q == null) return false;
        return q.contains("合同") || q.contains("内部") || q.contains("密码")
                || q.contains("身份证") || q.contains("薪资");
    }
}
