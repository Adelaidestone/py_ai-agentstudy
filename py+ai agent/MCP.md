## 1. MCP概念
Model Context Protocol 模型上下文协议
面向大模型，把外部上下文、根据和数据源带给模型的一套标准通信协议。更准确来说是MCP Client和MCP Server之间的通信协议。Host负责承载用户交互和模型调用，Client负责和Server说话，Server负责把具体能力暴露出来。MCP的核心价值是让工具开发和Agent开发解耦



# 2. MCP架构
![[MCP四层架构.png]]
Host是AI应用本身，比如claude desktop、cursor、vscode中的ai插件，或者自己做的agent
Client是Host内部负责和MCP通信的那一层，不需要自己写，一个Host可以连接多个MCP Server，通常每个Server对应一个Client
Server，可以把写的文件读取、SQL 查询、GitHub Issue 查询、内部工单查询这些能力暴露出去

## 3.MCP流程
用户提问后，模型判断自己缺少外部信息，于是生成一个工具调用。Host 把这个调用交给 MCP Client，Client 通过 JSON-RPC 请求 MCP Server。Server 去调用工具（查 Git 日志），结果再一路返回给模型，由模型组织成最终回答。
3个细节：
1.模型选不选对工具，很大程度上看工具描述。工具名、description、参数说明、禁用场景，都要写清楚
2.模型传来的参数不能默认可信。读文件要限制目录，查 SQL 要参数化，高危操作要审批，返回数据要脱敏。这是后端要求
3.Client 和 Server 在正式调用工具前，会先完成初始化握手。Client 发送 `initialize` 请求，带上自己支持的协议版本和能力列表；Server 返回自己支持的协议版本、能力和基础信息。确认之后，Client 再发 `initialized` 通知，双方才进入可用状态。


## 4.MCP暴露的能力
### 4.1 Resource
**Resources 更像只读上下文。** 比如本地文件、日志片段、数据库 Schema、某条配置记录。它们通常适合“给模型看”，让模型拿来理解和推理。

### 4.2 Tools
**Tools 是可执行动作。** 比如查询数据库、发送消息、创建工单、调用业务接口。只要会主动执行逻辑，或者可能改变外部世界，通常都应该放到 Tools。

## 4.3 Prompt
**Prompts 是可复用的提示词模板。** 比如“按团队规范做代码审查”“生成故障复盘初稿”“把接口文档整理成测试用例”。这类固定任务可以沉淀成模板，不必每次让用户重新写一遍。



## 5.为什么MCP用JSON-RPC 2.0
### 5.1调用简单
RESR API思维偏向资源调用（比如Get/users）RPC思维则是直接调用具体方法
对于大模型而言，工具调用的本质就是远程函数调用，llm不需要理解复杂的URL或者HTTP谓词语义，只需要知道函数名和调用参数。JSON-RPC的结构天然契合

### 5.2 支持双向通信
在ai和工具交互时，并不是简单的请求-响应关系，往往需要更丰富的交互模式。比如单向通知：服务器可以随时向客户端推数据（例如进度更新、状态变更 `roots/list_changed`），而**无需客户端拉取，也不需要返回响应**，JSON-RPC原生定义了`Notification`（不带 `id` 的消息），这让 MCP 能够轻松支持长任务进度推送、实时资源更新等高级特性。

## 5.3 运输层无关
mcp需要在不同物理环境中运行。无论是本地的python/node.js还是HTTP/SSE的跨网络远程调用，JSON-RPC都能完美工作

### 5.4 轻量、跨语言、易于调试
与 gRPC / Protocol Buffers 等高性能二进制 RPC 框架相比：

- **不需要编译 Schema**：开发者不需要用编译工具生成 Stub 代码，任何语言打开即用。
    
- **可读性强**：JSON 是纯文本，开发者直接看日志就能抓包调试，非常符合开源生态快速搭建工具（MCP Server）的需求。
    
- **生态极佳**：几乎所有编程语言（Python, TypeScript, Go, Rust 等）都有成熟的 JSON 解析器和 JSON-RPC 库，极大地降低了开发者接腹 MCP 的门槛。

### 5.5 ## stdio 和 Streamable HTTP 怎么选？
- 本地工具、本地文件、个人使用，优先 stdio。
- 团队服务、远程 API、多用户访问，优先 Streamable HTTP。
- 涉及写操作和敏感数据时，不管哪种传输方式，都要额外做鉴权、限流和审计。


