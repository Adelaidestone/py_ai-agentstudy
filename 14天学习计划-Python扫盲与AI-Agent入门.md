# 14 天学习计划：Python 扫盲 + AI Agent 扫盲（最终版）

> 总时间：14 天 × 4 小时 = **56 小时**
> 资料 A：`ai-learning-main` → **只用 Week1-Python速通（Day1-5）**
> 资料 B：`ai-agents-from-zero` → **核心章节全部覆盖**
> 背景：有 Java 基础，Python 零基础
> 对比评估详见：`两套教程重叠内容对比评估.md`

---

## 核心策略

1. **A 只贡献 Python 基础**（5天×1.5h），其余全部用 B
2. 两套教程重叠的 7 个主题全部已评估，**每个主题选了更合适的版本**（详见对比评估文档）
3. Week 1 的 Agent 内容是概念性的，不需要 Python → 用来并行学 Python
4. Week 2 全部是 Python 代码实战，Python 基础此时已到位

---

## Week 1：Python 入门 + Agent 概念扫盲

### Day 1（4h）— Python 基础语法 + 大模型认知

| 时间 | 内容 | 资料 |
|------|------|------|
| 2h | Python Day1：变量、控制流、函数、类（Java 对照版） | A: `Day1-基础语法.md` + 练习 |
| 2h | 大模型认知：什么是 LLM、训练流程、落地场景 | B: `1-1-大模型认知与工程概览.md` |

**Python 重点**：缩进代替花括号、动态类型、`def` 函数、`class` 基本写法
**Agent 重点**：搞懂大模型基本原理、token、上下文窗口

---

### Day 2（4h）— Python 数据结构 + 提示词工程

| 时间 | 内容 | 资料 |
|------|------|------|
| 2h | Python Day2：list/dict/set/tuple、推导式、切片 | A: `Day2-数据结构.md` + 练习 |
| 2h | 提示词工程：六要素框架、Zero/Few-shot、结构化组织 | B: `1-2-提示词工程基础.md` |

**Python 重点**：字典和列表推导式（LangChain 里满天飞）
**Agent 重点**：学会写有效提示词，理解 System/User/Assistant 角色

---

### Day 3（4h）— Python 进阶 + RAG/智能体选型

| 时间 | 内容 | 资料 |
|------|------|------|
| 2h | Python Day3：装饰器、上下文管理器、异常、模块导入 | A: `Day3-Python进阶.md` + 练习 |
| 2h | RAG/微调/智能体概览 + 搭一个知识库 | B: `1-3-RAG、微调、续训与智能体选型.md` + `2-RAG-搭建企业私有&个人知识库.md` |

**Python 重点**：装饰器 `@xxx`（LangChain 大量使用）、`import` 模块、`try/except`
**Agent 重点**：理解 RAG 流程，用 Cherry Studio 或 Dify 搭一个知识库

---

### Day 4（4h）— NumPy + 低代码平台 + Docker

| 时间 | 内容 | 资料 |
|------|------|------|
| 1.5h | Python Day4：NumPy 基础、ndarray、矩阵运算 | A: `Day4-NumPy基础.md`（难题可跳过） |
| 1h | Docker 基础（能看懂命令即可） | B: `8-Docker快速入门与Dify部署排障.md` |
| 1.5h | Coze/Dify 平台实操，搭一个完整工作流 | B: `3-基于Coze&Dify平台的智能体开发.md` |

**Python 重点**：NumPy 向量化和矩阵运算（后面 Embedding 会用）
**Agent 重点**：在 Coze 或 Dify 上搭一个完整工作流

---

### Day 5（4h）— Python 综合演练 + Python 调平台 API

| 时间 | 内容 | 资料 |
|------|------|------|
| 1.5h | Python Day5：读懂代码 + 写小脚本（验证成果） | A: `Day5-综合演练.md` + 练习 |
| 2.5h | 用 Python 调 Dify/Coze 的 API | B: `4-Python调用Dify平台工作流.md` + `5-Python调用Coze平台工作流.md` |

**Python 重点**：综合运用，确保能独立写 50 行脚本、调 HTTP API
**Agent 重点**：理解 API 调用方式，为 LangChain 打基础

---

### Week 1 自检清单

- [ ] 能读懂一段 Python 代码（随机打开一个 `.py` 文件，理解 80%）
- [ ] 能用 Python 调一个 HTTP API
- [ ] 能解释大模型、token、RAG、智能体这几个概念
- [ ] 在 Coze 或 Dify 上搭过至少一个完整工作流
- [ ] 理解提示词的六要素写法

---

## Week 2：LangChain + LangGraph + 项目实战

> 从这里开始全部使用资料 B，Python 已经够用了

### Day 6（4h）— LangChain 入门

| 时间 | 内容 | 资料 |
|------|------|------|
| 1h | LangChain 概述与架构 | B: `9-LangChain概述与架构.md` |
| 1.5h | 快速上手 + HelloWorld | B: `10-LangChain快速上手与HelloWorld.md` + 跑代码 `01-helloworld/` |
| 1.5h | Model I/O 与模型接入 | B: `11-Model-I-O与模型接入.md` + 跑 `02-models_io/` |

**目标**：跑通第一个 LangChain 程序，理解模型是怎么接进来的

---

### Day 7（4h）— 提示词模板 + 输出解析 + LCEL

| 时间 | 内容 | 资料 |
|------|------|------|
| 1.5h | 提示词模板 + Ollama 本地模型 | B: `13-提示词与消息模板.md` + `12-Ollama本地部署与调用.md` |
| 1h | 输出解析器 | B: `14-输出解析器.md` + 跑 `05_parser/` |
| 1.5h | LCEL 与链式调用 | B: `15-LCEL与链式调用.md` + 跑 `06-lcel/` |

**目标**：理解 Runnable 接口、链式调用（顺序/并行/分支）

---

### Day 8（4h）— Memory + Tools + Embedding/向量

| 时间 | 内容 | 资料 |
|------|------|------|
| 1.5h | 记忆与对话历史 | B: `16-记忆与对话历史（含Redis基础）.md` + 跑 `07-memory/` |
| 1.5h | Tools 工具调用 | B: `17-Tools工具调用.md` + 跑 `08-tools/` |
| 1h | 向量数据库与 Embedding | B: `18-向量数据库与Embedding实战.md` + 跑 `09-embedding/` |

**目标**：Memory + Tools + 向量检索，Agent 的三大支柱

---

### Day 9（4h）— RAG 实战 + LangGraph 入门

| 时间 | 内容 | 资料 |
|------|------|------|
| 1.5h | RAG 综合实战（文档加载→分割→向量存储→检索→生成） | B: `19-RAG检索增强生成.md` + 跑 `10-rag/` |
| 2.5h | LangGraph 入门 + State/Node/Edge | B: `22-LangGraph概述与快速入门.md` + `23-LangGraphAPI：图与状态.md` + 跑 `案例与源码-3-LangGraph框架/01-helloworld/` |

**目标**：跑通一个完整 RAG 链路；从链式思维切换到图式思维

---

### Day 10（4h）— LangGraph 核心 + MCP 协议

| 时间 | 内容 | 资料 |
|------|------|------|
| 2h | LangGraph 节点/边/高级特性（流式、持久化、HITL） | B: `24-LangGraphAPI：节点、边与进阶.md` + `25-LangGraph高级特性.md` + 跑 `04-node/` `05-edge/` `07-senior/` |
| 2h | MCP 协议：原理 + 写一个本地 MCP 服务端 | B: `20-MCP模型上下文协议.md` + 跑 `11-mcp/` |

**目标**：能用 LangGraph 搭有状态的 Agent；理解 MCP 并写一个 MCP Server

---

### Day 11（4h）— Agent 智能体 + 多智能体 + A2A

| 时间 | 内容 | 资料 |
|------|------|------|
| 2h | Agent 智能体（ReAct 范式、工具调用） | B: `21-Agent智能体.md` + 跑 `12-agent/` |
| 2h | LangGraph 多智能体 + A2A 协议 | B: `26-LangGraph多智能体与A2A.md` + 跑 `08-multi_agent/` |

**目标**：理解单智能体→多智能体的演进，Supervisor/Handoff 两种模式

---

### Day 12（4h）— 实战项目：电商问数（上）

| 时间 | 内容 | 资料 |
|------|------|------|
| 1h | 项目概述 + 数仓基础 + 工程初始化 | B: `实战项目-电商问数/0-前言.md` → `3-开发环境与基础服务准备.md` → `4-项目结构与基础服务配置管理.md` |
| 1h | 基础服务：Qdrant + ES + MySQL + Embedding | B: `实战项目-电商问数/5-Qdrant与ES快速入门与接入.md` + `6-MySQL、Embedding与日志管理.md` |
| 2h | 元数据知识库构建 + 字段检索能力 | B: `实战项目-电商问数/7-元数据知识库总览与构建入口.md` → `9-字段与指标检索能力构建.md` |

**目标**：理解 NL2SQL 项目整体架构，跑通元数据构建链路

---

### Day 13（4h）— 实战项目：电商问数（下）+ 深度研搜（上）

| 时间 | 内容 | 资料 |
|------|------|------|
| 2h | 问数工作流：关键词抽取→多路召回→SQL 生成执行 | B: `实战项目-电商问数/10-问数智能体总览与工作流骨架.md` → `14-SQL生成与执行闭环.md` |
| 2h | 深度研搜：DeepAgents 基础 + 子智能体 | B: `实战项目-深度研搜/0-前言.md` → `3-子智能体进阶与异步执行.md` |

**目标**：跑通 NL2SQL 全流程；理解主智能体+子智能体架构

---

### Day 14（4h）— 深度研搜（下）+ 总复习

| 时间 | 内容 | 资料 |
|------|------|------|
| 2h | 深度研搜：工程初始化 + 多来源检索 + 项目闭环 | B: `实战项目-深度研搜/8-项目总览与工程初始化.md` → `14-FastAPI接口与项目闭环.md` |
| 1h | 总复习：术语表 + 面试题库 | B: `全书术语表.md` + `AI智能体与大模型应用开发面试题库.md` |
| 1h | 查漏补缺，回顾薄弱环节 | 回看笔记 |

**目标**：能用自己的话说清楚整个技术栈的脉络

---

### Week 2 自检清单

- [ ] 能用 LangChain 写出带记忆、有工具、能检索的 Agent
- [ ] 能解释 LCEL、Runnable、链式调用
- [ ] 能用 LangGraph 搭一个有状态的 Agent 工作流
- [ ] 理解 MCP 协议的作用和基本写法
- [ ] 能说清电商问数项目的 NL2SQL 全流程
- [ ] 能说清深度研搜项目的主智能体+子智能体架构
- [ ] 能解释 RAG、Agent、LangChain、LangGraph 的关系

---

## 每日学习节奏建议

```
前 2 小时（精力好）：
  学习当天主要新知识，跟着教程敲代码

后 2 小时（理解消化）：
  跑示例代码 / 搭项目 / 复习前一天内容

最后 10 分钟：
  写 3 句话总结今天学了什么
```

---

## 可跳过的内容（时间不够时）

优先级从低到高，时间紧张时按顺序砍：

1. NumPy 深入练习（Day4 的 softmax/KNN 等难题）
2. Docker 部署细节（能看懂命令就行）
3. 深度研搜的中间件/Skills 章节
4. A2A 协议的认证授权细节
5. 电商问数的 FastAPI 接口实现细节

---

## 以后想深入时再看的内容

以下内容不影响 14 天计划，但对长期发展有价值：

| 内容 | 来源 | 什么时候看 |
|------|------|-----------|
| Transformer 完整架构（手写 TinyGPT） | A: `02-LLM认知/Week2` | 想理解模型内部原理时 |
| HuggingFace 生态 + 本地推理 | A: `02-LLM认知/Week3` | 想做模型部署/微调时 |
| LoRA 量化微调 | A: `02-LLM认知/Week3-Day4` | 想做模型定制时 |
| 经典 ML 算法扫盲 | A: `01-基础速通/Week2` | 想补 ML 基础时 |
| 分片策略深度（含评估方法论） | A: `03-SpringAI/Week4-Day2` | 想优化 RAG 质量时（只读概念部分） |
