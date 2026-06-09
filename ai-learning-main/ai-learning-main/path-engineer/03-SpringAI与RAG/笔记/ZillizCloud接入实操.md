# Zilliz Cloud 接入实操（Serverless 免费版）

> 📌 本笔记记录从零接入 Zilliz Cloud Serverless 免费集群的全过程，含踩坑记录。
> 适用场景：本地装不了 Docker、不想维护 Milvus standalone，只想专心学 SpringAI + RAG。

---

## 0. 我的控制台与集群信息

> ⚠️ Token 属于敏感凭据，不要提交到 Git。本文件已加入 `.gitignore` 规则保护（见末尾）。

| 项目 | 值 |
|------|----|
| **控制台地址** | https://cloud.zilliz.com/orgs/org-mhlcrtnhfupmuhehzdxiex/projects/proj-51fe16d00bfa02ea9d8ce3/clusters/in03-65ca0c32190e657 |
| **Cluster ID** | `in03-65ca0c32190e657` |
| **类型** | Serverless（Free Tier） |
| **Region** | AWS `eu-central-1`（法兰克福） |
| **Public Endpoint** | `https://in03-65ca0c32190e657.serverless.aws-eu-central-1.cloud.zilliz.com` |
| **Token 获取位置** | 控制台 → Cluster 详情 → 右上 **Connect** 按钮 → API Key |

### Token 拆分（SpringAI 用 username/password 模式时需要）

Zilliz Token 整串形如 `db_xxxx:yyyy`，但**免费版 Serverless 直接给的是一个完整 Bearer Token**（无冒号），可以两种方式使用：

- **REST API**：直接放到 `Authorization: Bearer <token>`
- **Java SDK / SpringAI**：用 `token` 字段一把传入即可，不需要拆分

---

## 1. 连通性验证（30 秒）

### ✅ 正确写法：v2 list collections 必须用 POST

```bash
curl --request POST \
  --url "${ZILLIZ_ENDPOINT}/v2/vectordb/collections/list" \
  --header "Authorization: Bearer ${ZILLIZ_TOKEN}" \
  --header "Content-Type: application/json" \
  --data '{"dbName":"default"}'
```

**预期返回**：
```json
{"code":0,"data":[]}
```

### ❌ 踩坑：GET 请求会 404

```bash
# 错误示范：v2 接口用 GET 会返回 404 page not found
curl --request GET \
  --url "${ZILLIZ_ENDPOINT}/v2/vectordb/collections/list" ...
# → 404 page not found
```

**原因**：Milvus 2.4+ REST API v2 规范——**所有操作（包括"列表/查询"）一律用 POST**，参数走 body。

### 备选：v1 接口支持 GET

```bash
curl --url "${ZILLIZ_ENDPOINT}/v1/vector/collections" \
  --header "Authorization: Bearer ${ZILLIZ_TOKEN}"
# → {"code":200,"data":[]}
```

> v1 是老接口，新项目建议直接用 v2。

---

## 2. REST API 完整 CRUD 速查

```bash
export ZILLIZ_ENDPOINT="https://in03-65ca0c32190e657.serverless.aws-eu-central-1.cloud.zilliz.com"
export ZILLIZ_TOKEN="<你的Token>"

# 1) 列表
curl -X POST "${ZILLIZ_ENDPOINT}/v2/vectordb/collections/list" \
  -H "Authorization: Bearer ${ZILLIZ_TOKEN}" -H "Content-Type: application/json" \
  -d '{"dbName":"default"}'

# 2) 创建
curl -X POST "${ZILLIZ_ENDPOINT}/v2/vectordb/collections/create" \
  -H "Authorization: Bearer ${ZILLIZ_TOKEN}" -H "Content-Type: application/json" \
  -d '{"collectionName":"demo","dimension":8,"metricType":"COSINE"}'

# 3) 插入
curl -X POST "${ZILLIZ_ENDPOINT}/v2/vectordb/entities/insert" \
  -H "Authorization: Bearer ${ZILLIZ_TOKEN}" -H "Content-Type: application/json" \
  -d '{"collectionName":"demo","data":[{"id":1,"vector":[0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8]}]}'

# 4) 检索
curl -X POST "${ZILLIZ_ENDPOINT}/v2/vectordb/entities/search" \
  -H "Authorization: Bearer ${ZILLIZ_TOKEN}" -H "Content-Type: application/json" \
  -d '{"collectionName":"demo","data":[[0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8]],"limit":3}'

# 5) 删除集合
curl -X POST "${ZILLIZ_ENDPOINT}/v2/vectordb/collections/drop" \
  -H "Authorization: Bearer ${ZILLIZ_TOKEN}" -H "Content-Type: application/json" \
  -d '{"collectionName":"demo"}'
```

---

## 3. SpringAI 接入配置

### Maven 依赖

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-milvus-store-spring-boot-starter</artifactId>
</dependency>
```

### `application.yml`

```yaml
spring:
  ai:
    vectorstore:
      milvus:
        client:
          host: in03-65ca0c32190e657.serverless.aws-eu-central-1.cloud.zilliz.com
          port: 443
          secure: true
          token: ${ZILLIZ_TOKEN}        # 推荐：整串 Token，无需拆分
        databaseName: default
        collectionName: spring_ai_demo
        embeddingDimension: 1536        # 对应你用的 embedding 模型
        indexType: AUTOINDEX            # Serverless 推荐 AUTOINDEX
        metricType: COSINE
        initialize-schema: true         # 第一次跑会自动建集合
```

> 💡 老版本（Spring AI < 1.0）可能不支持 `token` 字段，需要把 token 当 `password`，`username` 留空或填 `root`。

### 环境变量（推荐写到 `~/.zshrc` 或项目 `.env`）

```bash
export ZILLIZ_ENDPOINT="https://in03-65ca0c32190e657.serverless.aws-eu-central-1.cloud.zilliz.com"
export ZILLIZ_TOKEN="<你的Token>"
```

---

## 4. Serverless 特性 & 注意点

| 特性 | 说明 | 影响 |
|------|------|------|
| **冷启动** | 长时间无请求后，首次请求需 5~15s | Demo/低频场景偶尔慢，重试即可 |
| **异步 DDL** | 创建/删除集合是异步的，元数据延迟可见 | curl 创建后立即 list 可能为空，等几秒再看 |
| **免费额度** | 2 个 Collection / 100 万向量 / 1GB | 学习够用，做大型评测要升级 |
| **QPS 限制** | Free 版有 QPS 上限 | 压测请用本地 Milvus 或 Dedicated 版 |
| **Region** | 默认在海外，国内访问延迟 200~400ms | 学习无感，生产请选就近 Region |

---

## 5. 踩坑记录

### Pit 1：v2 接口用 GET → 404

✅ 解决：所有 v2 接口一律 POST，参数放 body。

### Pit 2：Drop 集合返回 `request timeout`

```json
{"code":10001,"message":"request timeout"}
```

✅ 解决：Serverless 冷启动 + 异步元数据，**实际操作已下发**，等 10 秒再 list 验证即可。
重试 drop 也不会出错（幂等）。

### Pit 3：SpringAI 第一次启动报 `collection not exist`

✅ 解决：配置加 `initialize-schema: true`，SpringAI 会自动创建集合和索引。

### Pit 4：维度不匹配 `dimension mismatch`

✅ 解决：`embeddingDimension` 必须和你用的 embedding 模型输出一致：

| 模型 | 维度 |
|------|------|
| OpenAI `text-embedding-3-small` | 1536 |
| OpenAI `text-embedding-3-large` | 3072 |
| 通义 `text-embedding-v2` | 1536 |
| BGE-large-zh | 1024 |
| BGE-M3 | 1024 |

---

## 6. 安全：把 Token 排除出 Git

在仓库根 `.gitignore` 加：

```gitignore
# 敏感凭据
**/笔记/ZillizCloud接入实操.md     # 如果你确实在文件里写了 Token
**/application-local.yml
**/.env
**/.env.*
```

> 推荐做法：本文件**只记位置和占位符**，Token 走环境变量或 `application-local.yml`（gitignore）。

---

## 7. 相关链接

- 控制台：https://cloud.zilliz.com/orgs/org-mhlcrtnhfupmuhehzdxiex/projects/proj-51fe16d00bfa02ea9d8ce3/clusters/in03-65ca0c32190e657
- Milvus REST API v2 文档：https://milvus.io/api-reference/restful/v2.4.x/About.md
- Spring AI Milvus VectorStore：https://docs.spring.io/spring-ai/reference/api/vectordbs/milvus.html
- 关联项目骨架：[`../项目/03-doc-search/`](../项目/03-doc-search/)
