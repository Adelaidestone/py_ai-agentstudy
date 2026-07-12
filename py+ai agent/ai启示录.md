# Tools calling
如何 agent 调用工具处理失败？


如果通过 **openai 兼容接口**接入的国产/第三方模型，用langChain的Padantic结构化输出兼容性参差不齐，应该怎么做？
这是业内常见的坑。因为「openai 兼容」通常只保证**对话功能**兼容，结构化输出这些高级特性不一定完整实现。

**以后遇到这类情况，两条更稳的路**：

1. 用模型厂商的**原生 SDK**（如智谱的 `zhipuai`），对自家模型的结构化输出支持更好
2. 用我们最终这套**多层兜底方案**：`json_mode` + `AliasChoices` + prompt 禁止 markdown——不依赖接口严格执行，靠容错取胜

为什么Padantic结构化输出机制这么受欢迎？ 
在没有 Pydantic 等结构化方案之前，开发者需要写大量的 Prompt 苦口婆心地求大模型“请返回 JSON， 不要带任何解释”，然后自己写繁琐的 json.loads() 和 try...except 。
而有了 Pydantic 等结构化方案结合.with_structured_output() 之后： 
Prompt 变干净了： 字段的 description 直接充当了 Prompt 的一部分。 
类型安全： 编辑器能自动补全，代码运行前就能做类型检查。 极其稳定： 依托大模型厂商底层的 JSON 模式，输出错误率降到了极低。