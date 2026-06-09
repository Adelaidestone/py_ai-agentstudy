package com.fletcher.helloai.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动期校验所有外部凭证是否通过环境变量注入。
 * 缺失时清晰报错，避免运行到具体接口才抛 NoApiKey / 鉴权 401 / 连接失败等模糊异常。
 *
 * <p>需要的环境变量：
 * <ul>
 *   <li>{@code DASHSCOPE_API_KEY} —— 通义千问 / DashScope（chat + embedding 都用）</li>
 *   <li>{@code ZILLIZ_TOKEN}     —— Zilliz Cloud / Milvus 鉴权 token</li>
 *   <li>{@code DEEPSEEK_API_KEY} —— DeepSeek（可选，未配置则 deepseekClient 调用会失败）</li>
 * </ul>
 */
@Slf4j
@Component
public class StartupCredentialCheck {

    @Value("${spring.ai.openai.api-key:}")
    private String dashscopeApiKey;

    @Value("${spring.ai.vectorstore.milvus.client.token:}")
    private String zillizToken;

    @PostConstruct
    public void check() {
        List<String> missing = new ArrayList<>();
        if (isBlank(dashscopeApiKey)) {
            missing.add("DASHSCOPE_API_KEY (映射到 spring.ai.openai.api-key)");
        }
        if (isBlank(zillizToken)) {
            missing.add("ZILLIZ_TOKEN (映射到 spring.ai.vectorstore.milvus.client.token)");
        }

        if (!missing.isEmpty()) {
            String msg = "缺少必要的环境变量，应用拒绝启动：\n  - "
                    + String.join("\n  - ", missing)
                    + "\n请在启动 shell 中通过 export 设置后重试，例如：\n"
                    + "  export DASHSCOPE_API_KEY=sk-xxx\n"
                    + "  export ZILLIZ_TOKEN=xxx";
            log.error(msg);
            throw new IllegalStateException(msg);
        }

        // DeepSeek 是可选的，只警告
        String deepseek = System.getenv("DEEPSEEK_API_KEY");
        if (isBlank(deepseek)) {
            log.warn("未检测到 DEEPSEEK_API_KEY，/api/chat/deepseek/** 接口将不可用（其他接口不受影响）");
        }

        log.info("凭证校验通过：DASHSCOPE_API_KEY={}, ZILLIZ_TOKEN={}{}",
                mask(dashscopeApiKey), mask(zillizToken),
                isBlank(deepseek) ? "" : ", DEEPSEEK_API_KEY=" + mask(deepseek));
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    /** 只显示前 4 后 4，避免日志泄露 */
    private static String mask(String s) {
        if (s == null || s.length() <= 8) {
            return "****";
        }
        return s.substring(0, 4) + "****" + s.substring(s.length() - 4);
    }
}
