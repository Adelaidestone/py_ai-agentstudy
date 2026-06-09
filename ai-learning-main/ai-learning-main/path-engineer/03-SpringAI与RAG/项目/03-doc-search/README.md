# 03-doc-search · 文档语义搜索（Spring AI + Zilliz Cloud）

> Week 3 毕业项目：用 Spring AI + Zilliz Cloud（Milvus）实现一个最小可用的文档语义搜索服务。
>
> 本项目同时也是 [`../../笔记/ZillizCloud接入实操.md`](../../笔记/ZillizCloud接入实操.md) 的配套代码。

---

## 1. 环境准备

| 依赖 | 版本 | 说明 |
|------|------|------|
| JDK | 21 | Spring Boot 3.3 要求 |
| Maven | 3.9+ | |
| Spring Boot | 3.3.5 | 与 helloai 对齐 |
| Spring AI | 1.0.2 | |
| 通义千问 API Key | - | [申请入口](https://dashscope.console.aliyun.com/apiKey) |
| Zilliz Cloud Token | - | [控制台](https://cloud.zilliz.com/orgs/org-mhlcrtnhfupmuhehzdxiex/projects/proj-51fe16d00bfa02ea9d8ce3/clusters/in03-65ca0c32190e657) → Connect |

### 设置环境变量

```bash
export DASHSCOPE_API_KEY="sk-xxxxxxxxxxxx"
export ZILLIZ_TOKEN="<你的 Zilliz Token>"

# 可选：覆盖默认 host
# export ZILLIZ_HOST="in03-xxxx.serverless.aws-eu-central-1.cloud.zilliz.com"
```

---

## 2. 启动

```bash
cd 项目/03-doc-search
mvn spring-boot:run
```

服务监听 `http://localhost:8083`。

> 💡 首次启动 Spring AI 会自动在 Zilliz 上创建 collection `doc_search_demo`（`initialize-schema: true`）。
> 看到日志 `Creating collection doc_search_demo` 即正常。

---

## 3. 快速验证

```bash
# (1) 健康检查：确认 VectorStore + Embedding + Zilliz 全链路 OK
curl http://localhost:8083/api/health/zilliz

# (2) 灌入 Demo 数据
curl -X POST http://localhost:8083/api/demo/seed

# (3) 语义检索
curl "http://localhost:8083/api/search?q=向量数据库有哪些&topK=3"

# (4) 写入自定义文档
curl -X POST http://localhost:8083/api/docs \
  -H "Content-Type: application/json" \
  -d '{
        "docs": [
          {"text": "Spring AI 1.0 在 2025 年 GA", "metadata": {"source": "blog"}},
          {"text": "Milvus 2.4 推出了 Milvus Lite，纯 Python 嵌入式运行"}
        ]
      }'
```

---

## 4. 项目结构

```
03-doc-search/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/fletcher/docsearch/
    │   ├── DocSearchApplication.java
    │   ├── controller/
    │   │   └── DocSearchController.java
    │   ├── dto/
    │   │   ├── AddDocRequest.java
    │   │   └── SearchRequest.java
    │   └── service/
    │       └── DocSearchService.java
    └── resources/
        └── application.yml
```

---

## 5. 配置要点

| 配置项 | 值 | 说明 |
|--------|----|----|
| `spring.ai.vectorstore.milvus.client.host` | Zilliz 域名（不带 `https://` 和端口）| Serverless 形如 `in03-xxx.serverless.aws-eu-central-1.cloud.zilliz.com` |
| `spring.ai.vectorstore.milvus.client.port` | `443` | Zilliz Cloud 全部走 HTTPS |
| `spring.ai.vectorstore.milvus.client.secure` | `true` | 开启 TLS |
| `spring.ai.vectorstore.milvus.client.token` | `${ZILLIZ_TOKEN}` | 整串 Token，无需拆 username/password |
| `embedding-dimension` | `1024` | **必须**与 embedding 模型输出一致（通义 v3=1024，v2=1536，OpenAI 3-small=1536） |
| `index-type` | `AUTOINDEX` | Zilliz Serverless 推荐 |
| `metric-type` | `COSINE` | 语义检索常用 |
| `initialize-schema` | `true` | 首次启动自动建集合，避免手动 DDL |

---

## 6. 常见问题

| 现象 | 原因 | 解决 |
|------|------|------|
| 启动报 `dimension mismatch` | embedding 模型维度 ≠ 集合维度 | 改 `embedding-dimension` 或换 embedding 模型 |
| 启动报 `collection not exists` | 没开 `initialize-schema` | 设为 `true`，或手动建集合 |
| 启动后第一次请求很慢（5~15s）| Zilliz Serverless 冷启动 | 正常现象，重试即可 |
| `request timeout` | DDL 异步 + 冷启动 | 等 10 秒后重试 |
| 控制台看不到刚建的集合 | Serverless 元数据异步刷新 | 等几秒刷新页面 |

更多踩坑参考：[`../../笔记/ZillizCloud接入实操.md`](../../笔记/ZillizCloud接入实操.md)

---

## 7. 下一步（进入 Week 4 RAG）

本项目只做了"文档入库 + 语义检索"，下一周会在此基础上加：
- 文档加载与解析（PDF/Word/MD）
- 分片策略（Recursive Splitter）
- 把检索结果注入 Prompt → 让 LLM 基于知识回答
- 来源引用与防幻觉

→ 进入 [`../../Week4-RAG核心机制/README.md`](../../Week4-RAG核心机制/README.md)
