package com.fletcher.docsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Week 3 毕业项目：基于 Spring AI + Zilliz Cloud 的文档语义搜索
 *
 * <p>启动前请在环境变量中设置：
 * <ul>
 *   <li>DASHSCOPE_API_KEY：通义千问 API Key</li>
 *   <li>ZILLIZ_TOKEN：Zilliz Cloud Token（控制台 Connect 按钮获取）</li>
 * </ul>
 */
@SpringBootApplication
public class DocSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocSearchApplication.class, args);
    }
}
