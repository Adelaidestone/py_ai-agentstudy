package com.fletcher.helloai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多模型 ChatClient 装配。
 *
 * <p>关键背景：Spring AI 的 {@code OpenAiChatAutoConfiguration} 上带有
 * {@code @ConditionalOnMissingBean}。一旦本类手写出任意一个 {@link OpenAiChatModel} 类型的
 * Bean（如 DeepSeek、MiMo），自动配置就<b>不再创建默认的 {@code openAiChatModel}</b>，
 * 导致通义千问链路丢失。
 *
 * <p>因此本类把全部 3 个 {@link OpenAiChatModel} Bean 都显式声明出来，且所有注入点
 * <b>必须用 {@code @Qualifier}</b> 显式指定，避免歧义。
 * <ul>
 *   <li>{@code openAiChatModel} — 通义千问（OpenAI 兼容协议）</li>
 *   <li>{@code deepseekChatModel} — DeepSeek</li>
 *   <li>{@code mimoChatModel} — 小米 MiMo</li>
 * </ul>
 */
@Configuration
public class ModelConfig {

    // ============== 通义千问（显式声明，替代被 @ConditionalOnMissingBean 跳过的自动配置）==============

    @Bean("openAiChatModel")
    public OpenAiChatModel openAiChatModel(
            @Value("${spring.ai.openai.api-key:}") String apiKey,
            @Value("${spring.ai.openai.base-url:https://dashscope.aliyuncs.com/compatible-mode}") String baseUrl,
            @Value("${spring.ai.openai.chat.options.model:qwen-turbo}") String model,
            @Value("${spring.ai.openai.chat.options.temperature:0.7}") Double temperature) {
        requireKey(apiKey, "DASHSCOPE_API_KEY");
        OpenAiApi api = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model(model)
                .temperature(temperature)
                .build();
        return OpenAiChatModel.builder()
                .openAiApi(api)
                .defaultOptions(options)
                .build();
    }

    /**
     * 通义千问 ChatClient（走 OpenAI 兼容协议）。
     */
    @Bean("qwenClient")
    public ChatClient qwenClient(@Qualifier("openAiChatModel") OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel).build();
    }

    /**
     * Ollama 本地模型 ChatClient
     */
    @Bean("ollamaClient")
    public ChatClient ollamaClient(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel).build();
    }

    // ============== DeepSeek ==============

    @Bean("deepseekClient")
    public ChatClient deepseekClient(@Qualifier("deepseekChatModel") OpenAiChatModel model) {
        return ChatClient.builder(model).build();
    }

    @Bean("deepseekChatModel")
    public OpenAiChatModel deepseekChatModel(@Value("${DEEPSEEK_API_KEY:}") String apiKey) {
        requireKey(apiKey, "DEEPSEEK_API_KEY");
        OpenAiApi api = OpenAiApi.builder()
                .baseUrl("https://api.deepseek.com")
                .apiKey(apiKey)
                .build();

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("deepseek-chat")     // 或 "deepseek-reasoner"
                .temperature(0.7)
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(api)
                .defaultOptions(options)
                .build();
    }

    // ============== 小米 MiMo ==============

    @Bean("mimoClient")
    public ChatClient mimoClient(@Qualifier("mimoChatModel") OpenAiChatModel model) {
        return ChatClient.builder(model).build();
    }

    @Bean("mimoChatModel")
    public OpenAiChatModel mimoChatModel(
            @Value("${MIMO_API_KEY:}") String apiKey,
            // 注意：base-url 不要带 /v1，Spring AI 的 OpenAiApi 会自动追加 /v1/chat/completions
            @Value("${MIMO_BASE_URL:https://token-plan-cn.xiaomimimo.com}") String baseUrl) {
        requireKey(apiKey, "MIMO_API_KEY");
        // 容错：若用户的环境变量仍然带了 /v1，自动剥掉，避免拼成 .../v1/v1/chat/completions
        if (baseUrl.endsWith("/v1") || baseUrl.endsWith("/v1/")) {
            baseUrl = baseUrl.replaceAll("/v1/?$", "");
        }
        OpenAiApi api = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("mimo-v2.5-pro")     // 注意：MiMo 接口的 model id 是全小写
                .temperature(0.7)
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(api)
                .defaultOptions(options)
                .build();
    }

    private static void requireKey(String value, String envName) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "缺少环境变量 " + envName + "，请在 ~/.config/ai-keys/keys.env 或 shell 中 export");
        }
    }
}

