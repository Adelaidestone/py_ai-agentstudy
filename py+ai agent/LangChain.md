# 1.LangChain是什么
LangChain是一个面向LLM应用开发的开源框架。他本身不是大模型，也不是数据库；他更像一层“应用编排层”，负责把模型、提示词、外部工具、知识、Memory、输出解析、调试追踪等能力组织成一个完整、可维护、可拓展的AI应用

比如说，在一个“企业知识库问答助手”中，用户提问后，系统需要先去向量库检索相关资料，再把检索结果和问题组装成prompt，然后让大模型生成答案，最后把结果整理后返回前端，这里真正扮演组织者角色的就是LangChain

一句话来说,**LangChain = 用代码把大模型和外部世界连接起来的应用开发框架**

其中，Lang 指的是Language Model，Chain指的是把多个环节形成流程。现在的LangChain，围绕Agent、RAG、工具调用、状态管理、可观测性形成了一整套生态

从工程视角看，LangChain 的价值不在于“替你发一次请求”，而在于帮你把下面这些原本零散的东西组织起来：

- 不同厂商的大模型调用方式
    
- Prompt 与多角色消息
    
- 结构化输出与解析
    
- 检索增强生成（RAG）
    
- 工具调用、Agent、多步任务
    
- 记忆、会话状态、持久化
    
- 日志、追踪、调试、评估
    

这也是为什么它会被很多人看作 AI 应用开发里的“基础框架”。

## 1.1 已经可以直接调模型，为什么还要学LangChain
直接调api可以，做简单任务时甚至更直接；但是你开始做应用，框架就会越来越有价值
放到项目开发里，两种路线的边界大致是：

- **只调 API**：适合单次文本生成、简单脚本、快速验证。
- **用 LangChain**：适合做成真实应用，例如客服助手、知识库问答、数据分析助手、工具调用 Agent、多轮对话系统。

所以，LangChain 不是“必须学”，但如果你的目标是做 **RAG、Agent、企业级 AI 应用、AI 服务接入现有业务系统**，它会显著降低你后续的组织成本。


## 1.2 Langchain和Coze/Dify区别
很多人第一次接触 LangChain 时，都会问一句：**“我不是已经会用 Coze 或 Dify 了吗，为什么还要学这个？”**

本质上，它们不是同一类东西：

|维度|Coze / Dify 等平台|LangChain|
|---|---|---|
|**是什么**|**产品 / 平台**：通过网页可视化界面、工作流节点、知识库管理等方式搭建 AI 应用|**代码框架 / 库**：在 Python 或 JS 中以代码方式编排模型、工具、RAG、Agent|
|**使用方式**|打开浏览器，拖拽节点、配置模型、填写提示词、连接知识库，最后发布应用或 API|在本地或服务器写代码，安装依赖，调用 LangChain API 构建自己的 AI 应用|
|**适合谁**|产品、运营、低代码开发者，或希望快速验证业务想法的人|开发者、后端、算法工程师，需要把 AI 能力深度接入已有系统的人|
|**灵活性**|受限于平台已有节点、插件、部署方式和权限设计|由代码完全控制，可接内部 API、自建数据库、私有模型、定制工具链|
|**典型场景**|快速做一个客服机器人、知识库问答、流程自动化 Demo|做企业内部 AI 中台、复杂 RAG 服务、可定制 Agent、与业务系统深度耦合的服务|

实际项目里，这两类工具经常不是“二选一”，而是**先平台、后框架；先低代码验证，再代码化落地**：

- 在业务探索阶段，用 **Coze / Dify** 快速验证需求。
    
- 在需要深度定制、接公司内部系统、做复杂编排时，用 **LangChain / LangGraph** 重构为代码服务。
    

这个思路，和你在本套教程里前半部分先学 Coze / Dify，后半部分再进入 LangChain / LangGraph，正好是一致的。


## 1.3 LangChain的主要模块

- langchain-core：官方推荐的核心API,比如Runnable,BaseMessage等
- langchain-classic：冗余代码或不推荐使用的经典API移到此
- langchain-community：第三方集成，比如：langchain-openai、langchain-anthropic，按需安装，避免臃肿
- langgraph:深度整合LangGraph 1.0，协调多个Chain，Agent、Tools完成更复杂的任务，而且支持循环调用


## 1.4 LangChain家族四大支柱

![[LangChain智能体生命周期.png|697]]
### 1.4.1 LangChain(基础能力层)
LangChain为开发者提供了调用模型、工具和中间件集成、智能体构建等一整套基础能力
核心价值：
- 统一的模型抽象层：屏蔽了不同模型服务商的接口差异，提供了一致的调用方式
- 高度模块化的设计：使用Message、Tool、Agent、Middleware等组件实现灵活的拓展
- 丰富的集成生态：预备了丰富的数据源、api、中间件等
结论：如果你需要构建简单智能体应用，无需复杂的编排，就选择LangChain
### 1.4.2 LangGraph(运行时编排层)
当智能体的任务从单一指令拓展为多步骤、有状态的复杂工作流时，Langgraph通过将智能体内部抽象为一张有向图来编排，通过节点、边、状态的图示结构，使得智能体的工作流节点交互变得显示、可控、可观测
### 1.4.3  DeepAgent(智能体的执行框架)
DeepAgent是新推出的全新组件，被定位为Agent Harness（智能体执行框架）。他构建在LangChain和LangGraph之上，增加了规划能力、文件系统、子Agent等高级功能。让开发者无须从0构建复杂的控制逻辑，即可创建具备深度规划、长期记忆和多专家协作能力的智能体。
核心能力：显式规划、虚拟文件系统、子智能体、长期记忆、可拓展中间件
![[LangChain、LangGraph、DeepAgent关系.png]]
用LangChain快速搭建，用LangGraph打磨生产稳定性，用Deep Agent赋予Agent更强的自主能力能力--这才是完整的LangChain玩法
### 1.4.4 LangSmith(可视化监控和测试平台)
当智能体系统逐渐复杂时，单靠日志和打印输出调试无法满足调试和质量管理的需求

LangSmith是LangChain推出的可视化监控和测试平台，用于跟踪、记录、分析智能体在运行过程中的完整调用链路，让智能体内部运行过程变得透明和可评估


# 2.LangChain模型调用
langchain内部设置了统一接口，如ChatDeepSeek，ChatOpenAi等
此处我们要介绍的是init_chat_model
问题：init_chat_model和直接使用ChatTongyi、ChatOpenAI有什么区别？
- 统一接口：无需记住每个提供商的不同初始化方式
- 易于切换：简化了智能体系统中模型切换策略（只需修改模型字符串）
- 简洁明了：更简洁的语法，减少样板代码
- 自动适配：内部根据模型标识自动选择对应的驱动类（ChatOpenAI、ChatDeepSeek）

## 2.2 参数
| **参数**               | **类型** | **说明**                                                                                                                                                 | **默认值**           |
| -------------------- | ------ | ------------------------------------------------------------------------------------------------------------------------------------------------------ | ----------------- |
| ```model```          | str    | 模型名称（必需）                                                                                                                                               | 无                 |
| ```model_provider``` | str    | 模型提供商名称                                                                                                                                                | 无                 |
| ```api_key```        | str    | API密钥。如果不提供，会从环境变量中读取(如`DEEPSEEK_API_KEY`)                                                                                                             | None              |
| ```base_url```       | str    | 大模型供应商API请求地址                                                                                                                                          | None              |
| ```temperature```    | float  | 控制输出随机性，范围0.0-2.0，温度越高输出越随机          `0.0`：最确定性，输出几乎不变                                          `1.0`:平衡创造性和一致性                        `2.0`：最随机，最有创造性 | 0.7               |
| ```max_tokens```     | int    | 限制模型输出的最大token数                                                                                                                                        | None              |
| ```timeout```        | float  | 超时时间(秒)，超时未响应，请求会被取消                                                                                                                                   | None              |
| `max_retries`        | int    | 请求失败(如网络问题、速率限制)时的最大重试次数                                                                                                                               | 6，claude code应该是7 |

temperature参数根据使用场景选择：

| **Temperature 取值** | **输出特点**                                 | **适用场景实例**                     |
| ------------------ | ---------------------------------------- | ------------------------------ |
| **0.0**            | **确定性最高。** 结果基本完全固定，每次输入得到的回答几乎都一样。      | 代码生成、数学计算、客观事实问答、数据提取。         |
| **0.2 ~ 0.5**      | **专注且严谨。** 在保证事实准确性的前提下，语言流畅度较好，极少胡言乱语。  | 专业文档翻译、技术文档编写、学术总结。            |
| **0.7 ~ 0.8**      | **均衡型（多数 AI 的默认值）。** 兼顾了语言的丰富度和基本逻辑的合理性。 | 日常对话、邮件撰写、通用的文章创作。             |
| **1.0 ~ 1.2**      | **高创造性。** 词汇选择更多样，思维更发散，但偶尔会出现逻辑跳跃。      | 头脑风暴、故事创作、广告文案、角色扮演（Roleplay）。 |
| **> 1.5**          | **极度混乱。** 词汇组合变得怪异，极易产生逻辑断层或乱码。          | 很少在实际业务中使用，仅用于极端压力测试或纯随机文本实验。  |

Token:大模型通过分词器将文本拆分后的最小语义单位，大语言模型将此作为收费依据

## 2.3 模型的调用
LangChain在模型调用上提供了几个核心的调用方式：
- `invoke`:阻塞式，一次性返回完整结果问答、批处理任务、无需实时反馈的场景
 基本语法：
 ```
 response = model.invoke(input,config=None)
 ```
 invoke的返回值:
 ```
 AIMessage(
    content='2', ##模型生成的答案
    additional_kwargs={'refusal': None, ' ##拒绝回答的情况 None表示安全
	#--- 响应元数据(api返回的详细原始数据) ---
	content': '我们要求1+1=?，只回复答案数字，所以答案是2。'},
    response_metadata={
        'token_usage': {
            'completion_tokens': 18, 
            'prompt_tokens': 18,
            'total_tokens': 36,
            'completion_tokens_details': {
                'accepted_prediction_tokens': None, #预测生成的token数
                'audio_tokens': None,
                'reasoning_tokens': 16,#推理模型消耗的token
                'rejected_prediction_tokens': None #被拒绝的预测Token
            },
            'prompt_tokens_details': {'audio_tokens': None,  #输入的音频Token数
            'cached_tokens': 0},
            'prompt_cache_hit_tokens': 0, #命中的缓存token数
            'prompt_cache_miss_tokens': 18
        },
        'model_provider': 'deepseek',
        'model_name': 'deepseek-v4-flash',
        'system_fingerprint':  'fp_8b330d02d0_prod0820_fp8_kvcache_20260402', #系统指纹，用来追踪模型后端的配置变更
        'id': '36156ac5-dd6c-4862-8e15-ede90afbbfbb',
        'finish_reason': 'stop',
        'logprobs': None
    },
    
    # LangChain内部唯一标识
    id='lc_run--019f4a76-ff41-7db2-a062-628fb62ab27d-0',
    
    #工具调用信息
    tool_calls=[], #正常触发的外部工具调用列表
    invalid_tool_calls=[], #触发失败或格式错误的工具调用
    
    #统一消耗元数据（LangChain标准化后的消耗格式）
    usage_metadata={
        'input_tokens': 18,
        'output_tokens': 18,
        'total_tokens': 36,
        'input_token_details': {'cache_read': 0}, #从缓存中读取的输入数量
        'output_token_details': {'reasoning': 16}
    }
)
 ```
 
- `ainvoke`:非阻塞式，提高系统吞吐量高并发web应用、IO密集型任务
- `stream`:流式输出，实时返回每个Token。聊天机器人、长文本生成，需要提升用户体验的交互应用。调用后，返回一个迭代器（iterator），可以通过循环实时处理每一个新的chunk内容块
  stream的优点：
   - `响应速度更快` -用户不必等待完整输出
   - `交互体验更流畅` -尤其是在长文本或复杂推理场景下
   - `可实时展示模型思考过程` 
- `astream`:非阻塞式，提高系统吞吐量高并发web应用、IO密集型任务
- `batch`：批量处理多个输入高并发场景，需要同时处理大量请求
   运行你一次性发送一组请求（含多条独立请求），模型会在后台并行处理，然后返回所有结果的列表
   与`invoke`相比，可以`大幅减少网络往返开销 和等待时间` ,显著提升性能，降低成本
   场景：文档摘要、批量问答、数据预处理、多样本分类等
- `abatch`:非阻塞式，提高系统吞吐量高并发web应用、IO密集型任务

### 2.4 异步调用
同步(sync)：
- 概念：发起一个任务后，需要等待该任务完成，才能继续执行后续任务
- 表现：当前执行流会被阻塞

异步(async)：
- 概念 ：发起一个任务后，不必等该任务结束，就可以继续执行其他任务
- 表现：当前执行流不会被阻塞

异步方法（ainvoke\astream\abatch）与他们的同步版本相比，具备以下特点：
- `避免阻塞主线程`：同步调用会阻塞程序执行，异步方法让应用在等待api响应时保持响应性
- `优化资源利用`：异步操作可以更高效地利用系统资源，减少空闲等待时间、


## 2.5 拓展内容

### 2.5.1 美化模型输出响应
- 使用pretty_print()
- 使用rich库
### 2.5.2 模型配置信息profile
LangChain1.1及更高版本可以通过profile属性查看模型的配置信息，不过是否能看到也取决于是否声明了能力画像
```
{
'name': 'DeepSeek V4 Flash', 
'release_date': '2026-04-24', 
'last_updated': '2026-04-24', 
'open_weights': True,
'max_input_tokens': 1000000, 
'max_output_tokens': 384000, 
'text_inputs': True, 
'image_inputs': False, 
'audio_inputs': False, 
'video_inputs': False, 
'text_outputs': True,
'image_outputs': False, 
'audio_outputs': False, 
'video_outputs': False,
'reasoning_output': True, 
'tool_calling': True,
'structured_output': True, 
'attachment': False, 
'temperature': True
}
```

### 2.5.3 两个重要的参数
**model_kwargs**
用于存放那些传递 **OpenAI 官方 API 支持但 LangChain 尚未单独封装** 的标准参数，这些参数会直接合并到请求顶层，如用于支持Function Call的tools字段
![[openai的tools.png]]
```
from langchain.chat_models import init_chat_model

from dotenv import load_dotenv

import os

from rich import print as rprint

  
  

load_dotenv(override=True)

DEEPSEEK_API_KEY = os.getenv("DEEPSEEK_API_KEY")

DEEPSEEK_BASE_URL = os.getenv("DEEPSEEK_BASE_URL")

  

model=init_chat_model(

    model="deepseek-v4-flash",  # 模型名称

    api_key=DEEPSEEK_API_KEY,

    base_url=DEEPSEEK_BASE_URL,  # DeepSeek API 的基础 URL

    model_kwargs={

        "tools": [

    {

        "type": "function",

        "function": {

            "name": "get_weather",

            "description": "Get weather of a location, the user should supply a location first.",

            "parameters": {

                "type": "object",

                "properties": {

                    "location": {

                        "type": "string",

                        "description": "The city and state, e.g. San Francisco, CA",

                    }

                },

                "required": ["location"]

            },

        }

    },

]

    }  

)

  

response = model.invoke("帮我获取海口的天气")

rprint(response)
```


![[用参数调用tools.png]]

**extra_body**
用来存放模型厂商（vLLM、LM Studio、OpenRouter等） OpenAI 兼容服务的私有扩展参数，这些会放到 `extra_body` 中，而不是请求顶层。
比如 thinking是DeepSeek拓展的字段，用于控制是否启用思考模型
```
model=init_chat_model(

    model="deepseek:deepseek-v4-flash",  # 模型名称

    extra_body={

        "thinking": {"type": "enabled"}

    }

)
rprint(model.invoke("如果我想要考公，我应该怎么做？"))
```


 ==对于 OpenAI 兼容服务的**非官方扩展参数**，应优先使用 `extra_body`，而不是 `model_kwargs`==
 
# 3.LangSmith
LangSmith 是 LangChain 生态系统中专门用于 LLM（大语言模型）应用调试、监控、评估和管理 的平 台。
- 追踪(tracing)：记录每次 LLM 调用的详细信息 
- 监控(monitoring)：实时查看应用性能 
- 调试(debug)：排查问题和优化性能 
- 评估(evaluate)：系统化测试 LLM 应用

### 具体功能
#### 功能1：核心应用与开发 
1、Tracing（追踪） 
- 功能：这是 LangSmith 最核心的功能。它会完整记录你大模型应用的每一次调用链路（Trace）。 
- 作用：当你的 Agent（智能体）或 RAG 系统运行变慢或报错时，点击进入对应的项目（如上图中 的 langchain1.2_smith），你可以看到每一步具体的 Prompt 是什么、模型返回了什么、消耗了 多少 Token，以及每一个链条节点的耗时，非常方便排查 Bug 和优化性能。 
2、Monitoring（监控） 
- 功能：提供生产环境的高级数据可视化看板。 
- 作用：帮你从宏观角度监控应用在一段时间内的运行状况。你可以看到 Token 消耗趋势、 QPS（每秒请求数）、错误率、平均延迟（Latency）以及成本预估。适合应用上线后观察系统的 稳定性和开销。 
3、Datasets & Experiments（数据集与实验） 
- 功能：用于管理测试数据集并运行对比实验。 
- 作用：你可以把用户的真实输入、特定的边界情况（Edge Cases）存为数据集。当你修改了 Prompt 或更换了底层大模型时，可以在这里运行自动化对比测试，直观看到新旧版本在同一批测 试集上的表现差异。 
4、Evaluators（评估器） 
- 功能：配置和自动化评估任务。 
- 作用：大模型的输出往往难以用传统的断言（Assert）来测试。这里允许你配置基于规则（如关键 词匹配）或基于模型（LLM-as-a-judge）的评估指标（如：答案相关性、是否包含幻觉等），对追 踪到的数据或实验结果进行自动打分。 
5、Annotation Queues（标注队列） 
- 功能：人工反馈与数据清洗工具。 
- 作用：在应用开发或初上线阶段，你可以把一部分痕迹（Traces）发送到标注队列中，让团队中的 核心成员、业务专家或人工客服进行手动打分、纠正回答或贴标签，这些高质量的人工标注数据后 续可直接用于微调模型或充当测试集。
#### 功能2：提示词与调试工具
1、Prompts（提示词管理） 
- 功能：类似“提示词版的 GitHub”。 
- 作用：把 Prompt 从代码中解耦出来，统一在云端管理。你可以在这里对 Prompt 进行版本控制 （如 v1、v2），直接在代码中通过 API 动态拉取最新的提示词。它还支持团队协作和 Prompt 的分享。 
2、Playground（演练场） 
- 功能：一个网页端的模型交互界面。 
- 作用：无需写任何代码，直接在这里选择不同的模型（如 OpenAI、Anthropic 或是本地模型）， 快速微调并测试你的 Prompt 效果，还可以一键将调整好的 Prompt 保存到上方的 Prompts 仓库 中。 
3、Studio（工作室） 
- 功能：通常与 LangGraph 深度集成，提供可视化的图形交互界面。 
- 作用：如果你的应用是基于图结构（Graph-based）的复杂复杂 Agent 架构，Studio 可以让你可 视化地看到状态机（State）在各个节点之间的流转，甚至支持在某个节点“暂停”，手动修改数据 后再继续向下执行，是调试复杂智能体交互的利器。
4、Context Hub（上下文中心） 
- 功能：管理全局上下文或通用组件配置。 
- 作用：用于存放可在多个项目或 Prompt 中复用的公共上下文模板、全局变量或系统预设提示。
#### 功能3：部署与沙盒 
1、Deployments（部署） 
- 功能：一键将你的 LangChain 应用或 LangGraph Agent 部署为线上可用的 API 服务（通常依托于 LangGraph Cloud）。 
- 作用：提供开箱即用的生产端点，帮你处理高并发、队列管理和状态持久化，让你专注于编写业务 逻辑。 
2、Sandboxes（沙盒） 
- 功能：提供轻量级的在线运行和测试环境。 
- 作用：在不污染生产环境的前提下，供开发人员安全地试运行、测试新部署的 Agent 或执行自动化 脚本。
	建议：现阶段大家可以重点关注 Tracing（观察你的项目里的调用细节）和 Playground（快速调 优提示词）。当你的应用结构开始走向复杂（比如引入了复杂的 RAG 检索或多 Agent 协同）时， 再逐步引入 Datasets 进行量化评估，并利用 Studio 进行可视化调试
# 4.消息和提示词模板
LangChain在1.0中提供了跨模型统一的Message标准。无论是用的是OpenAI、Anthropic、Gemini还是本地模型，这一标准都能保持一致的行为。好处：
- `兼容性强`：不通模型的消息格式自动对齐
- `可拓展性高`：方便添加多模态内容或自定义字段
- `可追踪性好`：为LangSmith等调试工具提供了一致的上下文数据结构

### 4.1 消息的内部结构
Message包含三个字段
- Role：消息所属角色或类型，如system,user,assistant
- Content：消息内容
- Metadata：（可选）元数据，存储额外信息。如消息id，响应时间，token消耗，消息标签

问题：为什么使用不同的消息类型？ 
- 明确角色：清晰区分系统提示、用户输入和 AI 回复 
- 控制行为：通过 SystemMessage 精确控制 AI 的行为 
- 对话历史：构建完整的多轮对话上下文 
- 调试友好：更容易追踪和调试对话流程

### 4.2 对话历史管理
关键规则：每次调用必须传递完整的对话历史
```
第 1 轮： [system, user] → AI回复 → 保存回复 
第 2 轮： [system, user, assistant, user] → AI回复 → 保存回复 
第 3 轮： [system, user, assistant, user, assistant, user] → AI回复 注意：每次对话都要在原有的消息列表中 错误举例1❌： 1 2 3 4 5 添加新消息，不可重新创建新的列表
```
	注意：每次对话都要在原有的消息列表中 错误举例1❌： 1 2 3 4 5 添加新消息，不可重新创建新的列表。

```
conversation = [] 
# 第一次 
conversation.append({"role": "user", "content": "我叫张三"}) 
response1 = model.invoke(conversation) 
# 关键：保存 AI 回复 
conversation.append({"role": "assistant", "content": response1.content}) 
# 第二次（传递完整历史） 
conversation.append({"role": "user", "content": "我叫什么？"}) 
response2 = model.invoke(conversation)  # AI 记得
```

### 4.3 对话历史优化 
**问题**：对话历史会越来越长，消耗大量 tokens 和成本。
**解决方案**：只保留最近 N 轮对话。具体的：
- 总是保留 system 消息（定义角色） 
- 只保留最近 N 轮对话，丢弃更早的历史
```
def keep_recent_messages(messages,max_pairs = 3):

    """

    保留最近的N轮对话

    max_pairs : 保留对话的轮数 （每轮 = user + assistant）

    """

  

    # 分离system 消息和对话消息

    system_messages = [m for m in messages if m.get("role") == "system"]

    conversation_messages = [m for m in messages if m.get("role") != "system"]

  

    # 只保留最近的消息对

    recent_messages = conversation_messages[-(max_pairs * 2):]

  

    # 返回系统消息和最近的消息对

    return system_messages + recent_messages
```


### 4.4 消息属性：content
消息的 content 可以理解为数据内容，它是弱类型的，支持字符串和列表（列表元素通常为字典）
**存储字符串**
如果只是纯文本内容，直接传递字符串就好
```
from LangChain.messages import HumanMessage
msg1 = HumanMessage(content = "你好啊") 
msg2 = HumanMessage("你好啊") 
print(msg1) 
print(msg2)
```
**存储字典类型**
如果需要发送的不只是文本，如多模态内容，则需要content的 字典内容遵循模型供应商的API规范，以 字典列表形式。
参考官方文档：[Create chat completion | OpenAI API Reference](https://developers.openai.com/api/reference/python/resources/chat/subresources/completions/methods/create)
```content=[ 
	{'type': 'text', 'text': '这张图里有什么？'},
	{ 'type': 'image_url', 
	"image_url": base64_image,
	}
```
### 4.5 content_blocks
在 LangChain 1.x 中， `content_blocks` 是消息对象（BaseMessage）的一项重大升级。它的核心目标 是提供一种跨模型供应商、标准化的多模态数据结构。
过去，处理图片、音频、甚至是模型生成的“思维链（Reasoning）”内容时，不同供应商（OpenAI, Anthropic, Google 等）的 API 格式各异，导致开发者需要写大量的适配代码。` content_blocks `的出现终结了这种混乱,他可以把content解析成标准、类型安全的表示。
- **数据结构**：他是一个`list`
- **统一格式**：每个`block`都有一个`type`字段，用于区分内容类型
- **支持类型**：包括text、image、audio、video、tool_call（工具调用）以及reasonling(推理/思维链)

==① 输入格式化==
对于复杂的对话（带图片或工具结果），建议使用建议使用content_blocks 列表形式构建HumanMessage或 AIMessage。 
借助 content_blocks 我们可以用一套标准代码，无缝地在不同厂商的模型之间切换。
② 输出格式化 
content_blocks还可用于输出格式化，以deepseek官网的deepseek-v4-flash为例，其输出包含思考 内容，后者位于additional_kwargs的reasoning_content字段下。
![[content_blocks输出格式化.png]]
不同的模型其输出格式可能不同，仅为提取思考内容，切换模型都可能需要更改代码，非常不方便。 content_blocks提供了统一的输出格式，可以将不同格式的响应统一为标准格式。 注意：content_blocks是懒加载的，即调用时才会解析。
	说明：优先检查 response.content_blocks 而不是 response.content，特别是当你需要获取“思维 链”或者“引用（Citations）”信息时。

### 4.6 提示词模板（Prompt Templates）
在 LangChain 开发中，构造提示词既可以直接使用 Python 字符串拼接（如 f-string、format() 或 +），也可以使用 LangChain 提供的 `PromptTemplate`或 `ChatPromptTemplate` 
1. **字符串拼接**
```
# 字符串拼接 
topic = "Python" 
difficulty = "初学者" 
# 难以维护，容易出错 
prompt_str = f"你是一个{difficulty}级别的编程导师。请用简单易懂的语言解释{topic}。" 
response = model.invoke(prompt_str) 
print(f"AI 回复：{response.content}...\n"

```
优点 ✅ ： 
简单直接，上手快 
适合临时 demo 无额外学习成本 
缺点 ❌ ： 
可读性差（变量多时混乱） 
不易维护（修改容易出错） 
无变量校验（容易漏/拼错） 
难以支持复杂场景（多轮对话 / RAG / Few-shot）

2. **提示词模板**
在LangChain 1.0中，**ChatPromptTemplate** 是用于生成消息列表的核心组件。
ChatPromptTemplate是创建聊天消息列表的提示模板。它比普通 PromptTemplate 更适合处理多角色、多轮次的对话场景。支持 System / Human /AI 等不同角色的消息模板

#### 4.6.1 实例化
**调用from_messages()（推荐）**
该方法允许传入一个由元组（Tuple）构成的列表，列表中的每一个元组都代表一条具有特定角色的消息。
```
# 导入相关依赖 from langchain_core.prompts import ChatPromptTemplate 
# 定义聊天提示词模版 
chat_template = ChatPromptTemplate.from_messages( 
	[("system", "你是一个有帮助的AI机器人，你的名字是{name}。"), 
	("human", "你好，最近怎么样？"), 
	("ai", "我很好，谢谢！"), 
	("human", "{user_input}"), ] ) 
# 打印格式化后的聊天提示词模版内容 
print(prompt)
```

**直接init**
```
from langchain_core.prompts import ChatPromptTemplate 
#参数类型这里使用的是tuple构成的
list prompt_template = ChatPromptTemplate([ 
# 字符串 role + 字符串 
	("system", "你是一个AI开发工程师. 你的名字是 {name}."), 
	("human", "你能开发哪些AI应用?"), 
	("ai", "我能开发很多AI应用, 比如聊天机器人, 图像识别, 自然语言处理等."), ("human", "{user_input}") ]) 
	#调用invoke()方法，返回
	ChatPromptValue prompt = prompt_template.invoke({"name":"小谷AI", "user_input":"你能帮我做什么?"}) 
	print(prompt)
```

#### 4.6.2模板如何调用
方式1：使用 invoke()
输出：ChatPromptValue的list对象
![[invoke()的输出是ChatPromptValue.png]]
方式2：使用format()
输出：字符串（str）
![[format()的输出是str.png]]
方式3：使用format_messages()
输出：消息列表
![[format_message的输出.png]]

**丰富的输入参数类型**

参数是列表类型，列表的元素可以是字符串、字典、字符串构成的元组、消息类型、提示词模板 类型、消息提示词模板类型等
源码
```
def __init__(self,            
 messages: Sequence[BaseMessagePromptTemplate | BaseMessage | BaseChatPromptTemplate | tuple[str | type, str | list[dict] | list[object]] | str | dict[str, Any]],             
 *,             
 template_format: Literal["f-string", "mustache", "jinja2"] = "f string",             **kwargs: Any) -> None
```
举例：Message列表类型
```
from langchain_core.messages import SystemMessage,HumanMessage 

chat_prompt_template = ChatPromptTemplate.from_messages([ 
SystemMessage(content="我是一个贴心的智能助手"), 
HumanMessage(content="我的问题是:人工智能英文怎么说？") ]) 
messages = chat_prompt_template.invoke({}) 
print(messages) print(type(messages))
```
MessagePromptTemplate列表类型
LangChain提供不同类型的MessagePromptTemplate。最常用的是`SystemMessagePromptTemplate` 、 `HumanMessagePromptTemplate` 和 `AIMessagePromptTemplate `，分别创建系统消息、人工消息和AI消息。



#### 4.6.3 高级特性
##### ==部分变量预填充：partial==
预填充某些固定不变的变量，创建模板的变体。
1. 存在「全局固定常量」，每次调用不变

场景：角色、模型人设、行业限定、输出格式要求，全流程统一。 
不用 partial：每一次 format 都重复粘贴同一堆参数，代码冗余、改一处要全量改。
用 partial：一次性绑定常量，下游只传动态变量，统一维护。
2. 参数分两段获取，不能一次性凑齐（最核心刚需）

这是 partial 不可替代的场景：

- 第一段：初始化时拿到固定参数（用户身份、系统配置、数据库配置）
- 第二段：运行时才拿到动态参数（用户实时提问、工具返回结果）

示例：

	1. 启动服务，读取配置 `product="电商客服"`，先 partial 固化；
	2. 用户发消息时才拿到 `user_msg`，此时只需要传这一个变量。 如果不用 partial，你必须把全局配置全局存起来，每次拼接传入，代码耦合严重。

	 3. 嵌套模板、多模板复用

一套基础模板，衍生多套细分场景： 基础模板：`{scene}场景下，根据{content}输出答案`

- 客服模板 = 基础模板.partial (scene="电商售后")
- 教育模板 = 基础模板.partial (scene="中小学答疑") 不用 partial 就要复制粘贴模板字符串，维护灾难。
3. 搭配链（Chain）、Agent、工具时自动传参
LangChain 的 Chain 只会自动传入**运行时上下文变量**，全局固定参数无法自动注入。
- 不用 partial：自定义 Chain 重写输入逻辑，手动拼接参数；
- 用 partial：提前把固定参数绑定到 prompt，Chain 无需额外改造。

##### ==消息占位符：MessagesPlaceholder==
当你不确定消息提示模板使用什么角色，或者希望在格式化过程中插入消息列表时，该怎么办？ 这就 需要使用消息占位符，负责在特定位置添加消息列表

`MessagesPlaceholder` 是 LangChain 内置的**特殊 Prompt 模板组件**，专门用来**批量插入一组完整消息列表**
使用场景：多轮对话系统存储历史消息以及Agent的中间步骤处理此功能非常有用
作用：
- 承载对话历史：对话机器人需要把历史聊天记录传给大模型，历史是多条消息数组，不能用普通 `{history}` 字符串占T位。 用`MessagesPlaceholder(variable_name="history")` 可以直接注入一整段对话上下文，实现多轮记忆。
- 2. 支持动态可变长度消息列表:历史对话条数不固定（1 轮、10 轮、100 轮都可以），占位符会自动把列表里每一条消息平铺到提示词中，无需手动拼接。
- 类型安全：普通 `PromptTemplate` 变量只能是字符串； `ChatPromptTemplate` 搭配 `MessagesPlaceholder` 支持结构化消息，兼容 OpenAI / 通义千问 / Claude 等所有支持消息格式的模型，不会出现格式错乱。

# 5. Tools
## 5.1 概述
工具是赋予大模型语言与外部世界交互能力的关键组件，从而能够让智能体哦执行搜索、计算、数据库查询、邮件发送或调用第三方API等，进而构建功能强大的ai应用。借助工具，大模型才能从认识世界走向改变世界
![[工具是构建智能体的核心要素之一.png]]

## 5.2 工具调用的整体流程
经典流程如下
```mermaid
flowchart LR 
	User[用户] -->|①今天天气如何| AI[AI助手或应用] 
	AI -->|②用户问题：xxx<br/>可调用'get_weather'| Model[模型] 
	Model -->|③'get_weather'调用参数：xxx| AI 
	AI -->|④调用天气工具并记录结果| Tool[天气查询工具 get_weather] 
	Tool -.->|返回数据| AI 
	AI -->|⑤工具结果+对话传给模型| Model 
	Model -->|⑥生成天气回答| AI 
	AI -->|⑦返回结果给用户| User
```

![[模型调用流程.png]]
工具调用流程总结： 
如果真正要大模型根据工具调用结果进行回复，完整的调用流程包括如下四个步骤： 
步骤1：模型绑定工具 ：通过model.bind_tools([...])绑定一个或者多个工具。 
步骤2：模型生成工具调用请求：用户输入问题，调用模型（比如invoke()）。如果需要调用工具，模型返回包含工具调用信息（如工具名称和参数）的AIMessage。 
步骤3：开发者手动执行工具：用户从响应中提取工具调用信息并手动调用对应的工具（比如工具.invoke()）。 
步骤4：将工具执行结果ToolMessage传递给模型生成回复

## 5.3 从Message流转看工具的调用
```
from langchain.messages import HumanMessage, ToolMessage

from rich import print as rprint

from langchain_core.tools import tool

  

@tool

def get_weather(city: str):

    """获取天气的工具"""

    return f"{city}天气晴朗~"

  
  

# 将模型和工具绑定

model_with_tools = model.bind_tools([get_weather])

  

# 声明一个消息列表

messages = [

    HumanMessage("今天北京天气如何")

]

  

# 模型生成调用工具请求

response = model_with_tools.invoke(messages)

  

# 添加AIMessage到消息列表中

messages.append(response)

  

# rprint(response)

  

tool_calls = response.tool_calls

  

for tool_call in tool_calls:

    if tool_call["name"] == "get_weather":

        # 大模型和Agent的主要区别在于：大模型不会主动的调用工具，所以这时候我们需要主动让工具调用。

        # 返回的是ToolMessage类型消息，添加到消息列表中

        tool_response = get_weather.invoke(tool_call)

        print(type(tool_response))

        messages.append(tool_response)

  

print("=====================> messages <=====================")

for msg in messages:

    msg.pretty_print()

print("=====================> messages <=====================")

final_response = model_with_tools.invoke(messages)

print(f"final_response: \n{final_response}")
```

![[大模型调用工具过程.png]]

## 5.4 `convert_to_openai_tool`
位于 `langchain_core.utils.function_calling`，是 LangChain 底层核心工具转换函数，专门把各类工具对象转换成 **OpenAI 官方 Tool Call 标准 JSON Schema**
**作用**
OpenAI 接口调用工具时，必须传入固定结构：
```
{
  "type": "function",
  "function": {
    "name": "...",
    "description": "...",
    "parameters": { ... }
  }
}
```
该函数自动完成：
1. 提取函数 / 工具名称、文档描述、参数类型；
2. 自动生成符合 OpenAI 规范的 JSON Schema；
3. 统一输出标准字典，直接传给 ChatOpenAI 的 `tools` 参数；
4. 兼容多类输入：原生函数、Pydantic、LangChain BaseTool、字典、第三方工具格式（Anthropic/Bedrock）。
使用场景：
`ChatOpenAI.bind_tools()` 底层自动调用
```
from langchain_openai import ChatOpenAI
llm = ChatOpenAI(model="gpt-4o")
tools = [add, WeatherTool()]
# bind_tools 内部循环执行 convert_to_openai_tool(tool)
llm_with_tools = llm.bind_tools(tools)
```
注意事项：
1. 函数必须写规范 docstring（Args 字段），否则参数 description 为空，模型调用准确率下降；
2. 仅支持基础类型（int/str/float/bool）、Pydantic 嵌套，复杂自定义对象会解析失败；
3. 字典输入会自动兼容 Anthropic/Bedrock 工具格式，自动转成 OpenAI 标准；

## 5.5 @tool 装饰器
`@tool` 是 `langchain_core.tools` 提供的装饰器，**快速把普通 Python 函数包装成 `BaseTool` 工具对象**，不用手动继承类写 `_run`，配合 `convert_to_openai_tool`、`bind_tools` 开箱即用，是日常定义工具最简洁的方式。
示例：
```
@tool
def get_weather(city: str) -> str:
    """查询指定城市的天气
    Args:
        city: 城市名称，例如北京、上海
    """
    return f"{city} 晴天，26℃"

# 转为OpenAI标准工具schema
schema = convert_to_openai_tool(get_weather)
print(json.dumps(schema, ensure_ascii=False, indent=2))
```
**支持的参数**
```
@tool(
    name="query_weather",  # 自定义工具名，不填默认函数名
    description="根据城市查询实时天气情况",  # 覆盖函数docstring描述
    return_direct=False,   # False：工具结果交给LLM总结；True：直接返回工具结果给用户，不经过模型
)
def get_weather(city: str) -> str:
    """原文档字符串"""
    return f"{city} 多云"
```
==**注意**==
1. **必须加参数类型注解** 
	错误：`def func(city):` 
	正确：`def func(city: str):` 装饰器靠类型注解自动生成 JSON Schema；
2. **docstring 规范写 Args** 
	用于提取每个参数的描述，模型知道该传什么；
3. 支持多参数、int/float/bool 基础类型
4. 没有description就必须写docstring，如果@tool的参数description和docstring同时存在，describeption参数优先级更高

## 5.6 `args_schema`
`args_schema` 是 LangChain 工具体系中**定义工具入参规范**的核心属性，全称 arguments schema，用来规定工具接收哪些参数、类型、描述、校验规则、枚举、默认值等
支持两种载体：
1. **Pydantic BaseModel 子类**（最常用，推荐
2. ）
3. 原生 JSON Schema 字典（少数场景）

**作用**：
- 自动生成OpenAI tools标准parameters
- 参数自动校验、抛异常
- 复杂参数支持：嵌套对象、枚举、正则、数值边界、长度限制，纯函数注解做不到
- 统一参数说明：不用依赖函数docstring写Args，模型读取更稳定

#### 方式1：使用Pydantic模型定义
当工具的参数变得复杂，需要枚举、范围限制或者更复杂的业务逻辑验证，Pydantic是理想的选择，提供强大的类型检测和数据验证
能够精确控制工具参数的格式和验证规则，让大模型更准确理解如何调用工具

##### ==pandantic类型的定义==
1. BaseModel基类
通过继承核心基类`BaseModel`定义数据模型，从而声明字段结构、类型约束、默认值以0及校验规则。
```
from pydantic import BaseModel 
class WeatherInput(BaseModel):    
	city: str 
print(WeatherInput(city="北京"))
```

2. field
用来“定制字段”的函数，可用于设置默认值、描述等。
- 设置默认值
  ```
  from pydantic import BaseModel, Field 
  class WeatherInput(BaseModel):    
	  city: str = Field(        
	  default= "北京"   
	  ) 
	print(WeatherInput())
  ```
  
- 设置参数描述信息
```
from pydantic import BaseModel, Field 
class WeatherInput(BaseModel):    
	city: str = Field(        
		default= "北京",        
		description="城市"   )    
		include_forecast: bool = Field(        
		default=False,        
		description="是否包含未来五日天气预报"   ) 
		
		print(WeatherInput())
```

3. Literal
`Literal` 是 Python 标准库（`typing`）的类型限定工具，搭配 Pydantic 用于**限制字段只能取固定几个值**，相当于内置枚举，专门给 LangChain 工具 `args_schema` 做参数约束。
```

class WeatherArgs(BaseModel):
    city: str
    # 默认使用摄氏度
    unit: Literal["celsius", "fahrenheit"] = Field(
        default="celsius",
        description="温度单位"
    )

```
`convert_to_openai_tool` 读取 Pydantic Literal,parameters 中会自动出现 `enum:[...]`,只会从`enum`里选择传参,大模型会看到可选列表，调用准确率大幅提升

##### ==使用 Json Schema定义==
在 LangChain 中，还可以直接使用 `JSON Schema 字典`来定义工具的参数模式。这种方式提供了极大 的灵活性。 
因为工具参数模式可以基于数据库配置或用户输入在运行时动态生成，所以这种方式特别适合参数结构需要动态生成的场景。
```
from langchain.tools import tool 
from langchain_core.utils.function_calling import convert_to_openai_tool 

weather_schema = {    
	"type": "object",    
	"properties": {        
		"location": {"type": "string"},        
		"units": {"type": "string"},        
		"include_forecast": {"type": "boolean"}   },    
		"required": ["location", "units", "include_forecast"]
	}
}
```

## 5.7 拓展：强制使用工具
### tool_choice参数说明

`bind_tools` 可以传递参数 `tool_choice `，用于控制是否强制使用工具。 该字段最终会作为 `payload` 的 `tool_choice` 字段传递给模型，OpenAI和Deepseek的官方API服务对于 `tool_choice `的取值做了相同的规定。
![[deepseek中关于tool_choice的解释.png]]
none ：模型不会调用任何工具。 
auto ： 默认值，模型可以自主决定不调用或调用任意数量的工具。 
required ：模型必须调用工具，数量不限。
某些场景下我们希望调用特定的工具，仍然可以用tool_choice解决。


# 6.结构化输出（Structured Output）
LangChain的结构化输出（Structured Output） 指的是：
`要求模型最终返回一个符合预定义结构的数据对象，例如固定字段的JSON、Pydantic 模型、 TypedDict，而不再是无格式的自然语言文本`
它的核心目标是把“自然语言回答”变成“ 程序可以稳定消费的数据”。
这样做的价值主要有三点： 
- 更容易被代码处理：下游系统可以直接读字段，而不是再从自然语言里做解析。 
- 结果更稳定：减少“模型说法变了但意思差不多”导致的解析失败。 
- 更适合工程化：适用于表单抽取、分类、路由、调用工具参数生成、工作流状态传递等场景。

**结构化输出模式**
目前LangChain 1.x 支持多种Schema与结构化输出方式： 
- Pydantic（字段校验、描述、嵌套结构，功能最丰富）
- TypedDict（轻量类型约束）
- JSON Schema（与前后端/跨语言接口最通用） 
- dataclass
模型对象可以调用 .with_structured_output() 绑定输出模式（schema）。 只有Pydantic返回的是Schema类实例，其余三种方式返回的都是 匹配时会抛出异常


为什么Padantic结构化输出机制这么受欢迎？ 
在没有 Pydantic 等结构化方案之前，开发者需要写大量的 Prompt 苦口婆心地求大模型“请返回 JSON， 不要带任何解释”，然后自己写繁琐的 json.loads() 和 try...except 。
而有了 Pydantic 等结构化方案结合.with_structured_output() 之后： 
Prompt 变干净了： 字段的 description 直接充当了 Prompt 的一部分。 
类型安全： 编辑器能自动补全，代码运行前就能做类型检查。 极其稳定： 依托大模型厂商底层的 JSON 模式，输出错误率降到了极低。


## 6.1 Pydantic
它通过在运行时强制执行类型提示，确保数据的正确性和一致性，是 生产场景首选。

**基本使用**
需要满足的几个要素： 
- 所有结构化输出的数据模型都必须继承 BaseModel 使用类型提示。
- Pydantic 支持丰富的字段类型：str 、int、float、List[xxx]、Optional[xxx]等 
- 使用 Field() 添加字段默认值和描述，帮助 LLM 理解字段含义
```
from pydantic import BaseModel, Field class Person(BaseModel):    
"""人物信息"""    
name: str = Field(description="姓名")    
age: int = Field(description="年龄")    
occupation: str = Field(description="职业")
#需要有描述，没有描述，llm可能格式错误
```


**高级特性**
1. 可选字段
	使用Optional指定字段为可选的。
	```from typing import Optional from pydantic import BaseModel, Field class 
	Person(BaseModel):    
	"""人物信息"""    
	name: str = Field(description="姓名")    
	age: Optional[int] = Field(description="年龄")         occupation: str = Field(description="职业") structured_llm = model_with_closeai.with_structured_output(Person)
	
	 structured_llm.invoke("张三是一名医生")
	```
2. 默认值
	LLM 未提供的信息会使用默认值。格式如下： 
	- ```
	  Field(default="默认值", description="描述") 
	  ```
	注意：不同模型提供商对default字段的支持是不同的。
3. 枚举类型
	使用枚举可以限制字段的可选值
	应用场景： 自动填充 CRM 系统 工单自动分类 客服辅助
4. 列表提取
	应用场景： 
	批量处理用户评论 
	自动生成分析报告 
	发现产品改进点
	 自动化财务处理 
	 OCR 后结构化 
	 数据录入
5. 嵌套结构输出
	说明：LLM 能力有限，复杂嵌套结构可能会出错。
	所以建议： 
	嵌套层级 ≤ 3 层
	使用清晰的 description 
	必要时拆分成多个调用
6. 限制条件

**工作流解析**
LangChain 会将 Pydantic Schema 转换为模型可理解的 JSON Schema，再通过 **Provider Native Structured Output** 或 **Function Calling** 等方式约束模型输出，最后利用 Pydantic 完成数据校验和对象化，将 LLM 的自然语言输出转化为类型安全、可验证、可直接供业务系统消费的数据对象。

**第一步：定义结构**
首先定义的是**业务需要的数据结构**,可以理解成：

> **Pydantic = 后端接口中的 DTO（Data Transfer Object）**

它规定了：
- 有哪些字段
- 字段类型
- 是否必填
- 默认值
- 字段描述
- 校验规则
- 
**第二步：Schema转换**
LLM 并不认识 Pydantic。
因此 LangChain 会先调用Pydantic的底层方法（model_json_schema())，将定义的结构转换成模型能够理解的 Schema（标准的 JSON Schema）。

**第三步：约束模型输出策略**
LangChain 会将这个 JSON Schema 包装进给大模型的 API 请求中。 
- 现代方法（ .with_structured_output ）： 现代大模型普遍支持“函数调用/工具调用（Function/Tool Calling）”或“JSON Mode”。LangChain 会把 JSON Schema 作为 Tools 传入。 
- 大模型侧的约束： 像 OpenAI 的 strict=True 参数，会启动模型的语法采样约束（Grammar based sampling）。大模型在解码生成 token 时，不是瞎猜，而是严格按照 JSON Schema 的语 法树进行选择，从而在模型底层级保证了输出格式绝不走样。

**第四步：自动解析与验证**
当大模型返回符合 JSON 规范的字符串后，LangChain 的 PydanticStructuredOutputParser）会接管工作： 
1. 解析（Parsing）： 将字符串解析为 Python 字典。 
2. 验证（Validation）： 将字典喂给你的 Pydantic 模型。Pydantic 会自动检查数据类型是否正确。 如果模型漏掉了必填字段，或者类型错误，这里会直接抛出验证错误（或者触发 LangChain 的重试机制）

#### LangChain + GLM-5.2 结构化输出踩坑记录

##### 问题现象

使用 LangChain 的 Pydantic 结构化输出时，GLM-5.2 一直无法正常返回符合 Schema 的结果，最终导致结构化解析失败。

---

##### 原因分析

虽然 GLM-5.2 提供的是 **OpenAI Compatible API**，但 **OpenAI Compatible 并不意味着完全兼容 OpenAI 的所有高级能力**。

LangChain 的结构化输出底层主要有两种实现方式：

1. **Provider Native Structured Output**
    - 使用模型提供商原生的 `response_format=json_schema` 能力。
    - 依赖模型本身完整支持 JSON Schema。
2. **Function Calling（Tool Calling）**
    - 将 Pydantic Schema 转换成一个 Tool。
    - 模型通过调用 Tool 返回符合 Schema 的参数，再由 LangChain 转换为 Pydantic 对象。

默认情况下，LangChain 会根据模型能力自动选择实现方式。

由于使用的是 **OpenAI Compatible 接口**，LangChain 会将 GLM-5.2 当作 OpenAI 模型处理，因此自动选择的策略未必适合 GLM-5.2。

---

##### 解决方案

强制指定结构化输出方式为 **Function Calling**。

修改后，结构化输出立即恢复正常。

---

##### 为什么 Function Calling 更稳定？

Function Calling 本质上是将 Pydantic Schema 转换成一个 Tool，模型只需要调用 Tool 并填写参数即可。

相比之下，Provider Native Structured Output 需要模型完整支持 `response_format=json_schema`，而不少第三方 OpenAI Compatible 模型对此支持并不完善。

目前很多国产模型的能力表现大致如下：

- ✅ Tool Calling 支持较成熟
- ⚠️ `response_format=json_schema` 支持存在兼容性问题

因此，Function Calling 的兼容性通常更好。


## 6.2 TypedDict
TypedDict 是 Python 3.8+ 引入的一种类型提示工具，即带有类型声明的字典结构。适合需要快速定义 字典结构且无需 Pydantic 重量级功能的场景
普通 dict 没有类型信息：
```
{   
	"title": "盗梦空间",    
	"year": 2010,    
	"director": "克里斯托弗·诺兰",    
	"rating": 9.3 
}
```
TypedDict可以进一步说明：
- 这个字典应该有哪些字段
- 这个字段的类型是什么
- TypedDict主要是类型声明,不是运行时强检验器。如果实例化字典给的字段名称和TypeDict不完全一致，会标记但是不会导致运行时异常
	```
	from typing_extensions import TypedDict
	 class MovieDict(TypedDict):    
	 title: str    
	 year: int    
	 director: str    
	 rating: float 
	 movie: MovieDict = {   
	  "title1": "盗梦空间",    
	  "year": 2010,    
	  "director": "克里斯托弗·诺兰",    
	  "rating": 8.8, } 
	 print(movie)
	```

**基本使用**
Annotated的使用
用来在“类型”之外，添加一些额外信息，即元数据，类似Pydantic的Field
基本形式
```
Annootated[类型，附加信息1，附加信息2...]
```

...的使用
...是Python的字面量，等价于Ellipsis，可以理解为占位符。下游框架（如 LangChain）可以对...作定制化处理，如LangChain中Annotated的...表示当前字段是必须存在的， 不可省略，用来指示模型的输出

**模式3：JSON Schema 不推荐**
**模式4：@dataclass 不推荐**

用Pydantic定义schema，在接收到响应后会进行校验，字段不匹配则抛出异常，其余三种方式不校验。


# 7.智能体
通用人工智能（AGI）是普遍认知中的AI的终极形态。而智能体（Agent）是当前AI工程应用的“终极形态“，即Agent是大模型应用开发的核心
![[Agent是大模型应用开发的核心.png]]
在大模型应用开发中，智能体通常指一种以 大语言模型为推理与决策核心，结合 环境交互能力，能够进行 规划决策并执行 复杂任务以达成目标的软件系统。

Agent的关键能力 
- 理解用户问题 
- 如何拆解任务 
- 判断 是否需要工具 
- 需要 调用哪些工具 
- 如何利用好 工具结果生成回答&推进任务

Agent的核心组件
![[Agent的架构.png]]
一句话总结：
- 必须的：行动（Action）
- 几乎总是存在的：工具（Tools）
- 有条件存在的：规划决策（Planning）
- 最容易被忽略的：记忆（Memory）
## 7.1 Agent创建和调用
在 LangChain 0.x 时代，框架内的 Agent 系统经历了“碎片化”阶段。当时的设计理念是 “针对场景设计 特定 Agent”：
如果你要实现思维链推理（ReAct），就用 `create_react_agent `；
如果需要结构化输出，就用 `create_structured_chat_agent `； 
要工具调用，则用` create_tool_calling_agent` 。

这种方式灵活，但也带来了三个明显问题： 
1. 心智负担高——每种 Agent 都要单独记忆 API 与参数； 
2. 可组合性差——多个 Agent 之间无法统一调度； 
3. 生态碎片化——不同模块难以复用或协同演化;


LangChain 在 1.0 版本后，团队做出了彻底重构：将所有 Agent 的创建方式统一为一个入口： `create_agent()`。它取代了旧版本中的 `create_react_agent 、 create_json_agent 、 create_tool_calling_agent `等多种分支函数，真正让开发者用一行代码即可创建任何类型的智能体。 同时在底层通过“中间件机制（Middleware）”和“标准模型接口（invoke / stream）”实现全局统一。这让框架更轻、更稳，也更易于被集成到其他 Agent 平台中。

创建agent
```
from langchain.chat_models import init_chat_model 
from langchain.agents import create_agent 
from langchain_deepseek import ChatDeepSeek 
from dotenv import load_dotenv 
import os load_dotenv(override=True) 
# 以ChatDeepSeek为例 
# model = ChatDeepSeek(model="deepseek-v4-flash") 
# 以init_chat_model为例 
model = init_chat_model(    
	model="gpt-5.4-mini",    
	model_provider="openai",    
	api_key=os.getenv("CLOSEAI_API_KEY"),    
	base_url=os.getenv("CLOSEAI_BASE_URL") 
	) 
	agent = create_agent(model) 
	print(type(agent)) 
```

`agent.invoke()`是Agent 最基本的同步调用方法，它会阻塞程序执行直到返回最终结果。
具体的： 
输入：传入的参数为字典类型，字典内通过messages字段传递消息列表。即：“ {"messages": [{"role": "...", "content": "..."}]} ” 
输出：通过invoke调用Agent，底层可能会经历多轮交互，返回的是完整的消息列表，被封装在 字典中，是messages字段的值
```
response = agent.invoke({"messages": [...]}) 
# response 是字典类型 
{    "messages": 
	[        
	HumanMessage(...),       # 用户问题
	AIMessage(...),          # AI 工具调用        
	ToolMessage(...),        # 工具返回结果        
	AIMessage(...)           # 最终回答 ← 通常取这个  
	] 
} 

# 获取最终回答 

final_answer = response['messages'][-1].content
```
invoke调用的核心就是输入一系列消息（messages），每条消息通常包含 role（如 "user", "assistant", "system", "tool"）和 "content"。 我们也可以在message列表的开头加入"system"角色的消息来定义Agent的行为。



## 7.2 Agent调用Tools

只有接入了一些工具，create_agent完成Agent创建才算完整。 Agent支持 静态和动态绑定工具，后者需要用到中间件，后面会讲。在执行时：

```mermaid
graph TD
    %% 节点定义
    A["用户问题 (User Input)<br>输入问题或指令"]
    B["[Agent 分析]<br>理解、规划、意图识别"]
    C{需要工具?}
    D["直接回答"]
    E["调用工具<br>API 调用、查询数据、计算"]
    F["获取结果<br>处理和解析工具返回数据"]
    G["生成回答<br>综合信息，提供最终响应"]

    %% 连线
    A -- 1 --> B
    B -- 2 --> C
    C -- 否 (No) --> D
    C -- 是 (Yes) --> E
    E -- 3a --> F
    F -- 4 --> G
    D -- 5 --> G

    %% 样式配色（贴近原图）
    classDef styleA fill:#cce5ff,stroke:#3388dd,stroke-width:2px
    classDef styleB fill:#e5ddff,stroke:#9966dd,stroke-width:2px
    classDef styleC fill:#ffe8bb,stroke:#ee9933,stroke-width:2px
    classDef styleDE fill:#ddeeff,stroke:#4477bb,stroke-width:2px
    classDef styleG fill:#d9f7dd,stroke:#44bb66,stroke-width:2px

    class A styleA
    class B styleB
    class C styleC
    class D,E,F styleDE
    class G styleG
```
LangChain内置工具列表： [Tool integrations - Docs by LangChain](https://docs.langchain.com/oss/python/integrations/tools)
具有代表性工具：
![[langchain内置工具.png]]

当用户提出一个复杂需求时，Agent会像人类一样，先理解任务、规划步骤、使用合适的工具（如搜索 网络、查询数据库、执行计算）获取信息，Agent 会在一个循环中 反复调用模型和工具，直到某次模 型输出中 不再包含工具调用则结束，最后综合所有信息给出最终答案。
```
用户问题
    │
    ▼
LLM 思考
    │
    ├── 调用 Tool？
    │      │
    │      ▼
    │   Tool 返回结果
    │      │
    └──────┘
       （进入下一轮）
    │
    ▼
LLM 判断是否结束
    │
    ├── 否 → 继续循环
    └── 是 → 输出最终答案
```

### 一次任务需要调用多个工具（Multi-Step Tool Calling）
有些问题，仅靠一次工具调用无法完成，需要多个工具协同工作。
例如：

> "帮我查一下北京今天的天气，然后根据天气推荐附近适合吃火锅的餐厅。"

```
Round 1
LLM
↓
调用 Weather Tool
↓
返回：北京今天小雨，22℃

模型拿到天气之后继续思考：
Round 2
LLM
↓
调用 Map/Restaurant Tool
↓
返回：附近火锅店列表


模型再综合两个工具返回的信息：
Round 3
附近火锅店列表+天气情况
↓
LLM
↓
生成最终回答

因此，一个用户问题可能经历：
LLM
→ Weather Tool
→ LLM
→ Restaurant Tool
→ LLM
→ Final Answer

整个过程中，Agent Loop 运行了多轮，每轮模型都会根据新的上下文重新规划下一步。
```

### 重试机制
```
from langchain.agents import create_agent

from langchain.tools import tool

from langchain.messages import SystemMessage, HumanMessage

from dotenv import load_dotenv

from rich import print as rprint

  

load_dotenv(override=True)

  

flag = 0

  

@tool

def get_weather(city: str):

    """

    天气查询工具

  

    Args:

        city: 城市名称

    """

    global flag

    flag += 1

  

    if flag < 3:

        # raise Exception("暂时无法访问")

        return "TEMP_UNAVAILABLE: 天气服务暂时不可用，请稍后重试"

  

    return f"{city}今天天气挺好"

  
  

messages = [

    SystemMessage("""

    你是一个天气助手。

    当工具返回以 'TEMP_UNAVAILABLE:' 开头的结果时，

    说明是临时故障，不要立即放弃；

    你应再次调用同一个工具，最多重试 3 次。

    如果 3 次后仍失败，再向用户说明服务暂时不可用。

    """),

    HumanMessage("你好，杭州今天的天气如何？")

]

agent = create_agent(model, tools=[get_weather])

response = agent.invoke({"messages": messages})

  

rprint(response)
```

比如，在此例中，在 Agent 中，**LLM 并不知道工具什么时候恢复正常**。

因此，当 Tool 返回一个**可恢复错误（Recoverable Error）**时，LLM 会重新进入下一轮思考（Reasoning），判断是否需要再次调用 Tool。

整个过程并不是 Tool 自己重试，而是：

> **LLM 在 Agent Loop 中重新决定："我要不要再调用一次 Tool？"**

因此，每一次重试，本质上都是一次新的 Agent Loop。
```mermaid
sequenceDiagram
    autonumber

    participant User
    participant Agent
    participant LLM
    participant Tool as get_weather()

    User->>Agent: invoke(messages)

    Agent->>LLM: 用户消息 + System Prompt

    LLM->>Tool: get_weather("杭州")
    Tool-->>LLM: TEMP_UNAVAILABLE

    Note over LLM: 根据 System Prompt 判断：<br/>属于临时错误，可继续重试

    LLM->>Tool: get_weather("杭州")
    Tool-->>LLM: TEMP_UNAVAILABLE

    Note over LLM: 第二次仍失败<br/>继续重试

    LLM->>Tool: get_weather("杭州")
    Tool-->>LLM: 杭州今天天气挺好

    LLM->>Agent: Final Answer

    Agent-->>User: 杭州今天天气挺好
```

### 常见问题
**问题1：Agent如何选择工具**
依据：工具的docstring、名称、参数定义（schema），选取与问题语义最匹配的工具

**问题2：Agent为什么没有调用工具**
原因：
- 工具的docstring不清晰
- 问题表述不清楚
- 模型认为不需要工具

**问题3：Agent选错工具**
原因：
- 多个工具的功能描述相似
- 工具太多导致混淆
如何解决：
- 只给必要的工具
- 工具描述要有明确区分
- 在system_prompt中说明工具使用场景

**问题4:Agent可以调用多少次工具**
默认没限制，直到得到最终答案。但可能会中断调用：
- 超时
- 达到token限制
- 模型决定停止
**问题5：如何限制工具调用次数**
LangChain 1.0 的 create_agent 默认使用 LangGraph，可以通过配置限制：
```
# 注意：这是高级用法，后续会详细学习
 config = { "recursion_limit": 5  # 最多 5 步 } 
 response = agent.invoke(input, config=config)
```


## 7.3 Agent的高级用法
### 7.3.1 设置name
name 在 Multi-Agent 场景中最常被提及，用于区分不同的 Agent。但它的作用并不局限于多 Agent 编排。在实际工程中，出现如下场景，通常都建议为 Agent 设置一个清晰且稳定的 name 。

|优先级|内容|作用|
|---|---|---|
|⭐⭐⭐⭐⭐|Instructions（系统提示）|决定 Agent 的行为和能力边界|
|⭐⭐⭐⭐☆|Description（职责描述）|帮助其他 Agent/LLM 选择它|
|⭐⭐⭐⭐☆|Name（名称）|提供清晰的语义标签，辅助任务路由|
|⭐⭐☆☆☆|其他元数据（标签、版本等）|工程管理用途|
### 7.3.2 系统提示词
使用 create_agent 创建 Agent 时，需传入 模型和工具、可选地传入 系统提示词。提示词为Agent 提供了任务背景、行为准则和操作指南
使用建议： 
- 明确说明 Agent 的角色 
- 定义输出格式 
- 说明何时使用工具
### 7.3.3 结构化输出
结构化输出是Agent的核心功能之一，它允许Agent以特定、可预测的格式返回数据，而不是传统的自然 语言响应。通过结构化输出，开发者可以直接获得Pydantic模型、JSON对象或数据类等结构化数 据，这些数据能够被应用程序直接使用，无需复杂的解析过程。

| 维度   | 模型的结构化输出               | Agent结构化输出                |
| ---- | ---------------------- | ------------------------- |
| 操作对象 | 作用于大模型                 | 作用于Agent                  |
| 解析时机 | 每次模型调用生成AIMessage时     | 仅在Agent决定“任务结束”并输出最终答案时解析 |
| 数据流转 | 模型->结构化对象              | 模型->工具->反思->...->结构化对象    |
| 绑定方式 | with_structured_output | 使用response_format参数       |
| 场景   | 单次、确定性的任务              | 多步、复杂推理的任务                |
|      |                        |                           |
#### 结构化输出的4种策略
LangChain的create_agent()函数自动处理结构化输出的全过程。用户只需通过“response_format”参 数设置期望的输出模式（Schema）。 
当模型生成结构化数据时，系统会自动捕获、验证并将结果存储在Agent状态的structured_response 键中。
```
def create_agent( 
... response_format: Union[ 
	ToolStrategy[StructuredResponseT], 
	ProviderStrategy[StructuredResponseT], 
	type[StructuredResponseT], 
	None, 
	]
```

##### ① ProviderStrategy
使用模型提供商的 原生结构化输出功能实现结构化输出。 
这里所说的“原生结构化输出”指的是大语言模型（LLM）提供商通过其API直接提供的、在模型响应 阶段就强制保证 输出格式符合预定规范的能力，这种能力能够在模型生成内容的源头确保结构化 准确性。 适用于支持原生结构化输出的模型，比如OpenAI、Anthropic Claude或xAI Grok等。

##### ② ToolStrategy 
对于不支持原生结构化输出的模型，LangChain采用“ToolStrategy”工具调用的方式实现结构化输出。 此策略兼容绝大多数 支持工具调用的现代模型，其核心原理是动态创建一个" 入参数对应着期望的数据结构。 当模型需要生成最终答案时，系统会引导模型 虚拟工具"，该工具的输 "调用"这个虚拟工具 ，从而间接产生符合要求的结构化数据。


###### 自定义工具消息：tool_message_content参数
如果采用ToolStrategy策略处理结构化输出时，LangChain会在消息列表末尾追加一条 Tool_message，让整个链路完整。但实际上没有实际的工具执行，这是一条伪消息。

我们可以通过ToolStrategy的 tool_message_content参数定制其消息内容，将指定的内容写入对话历 史的提示信息，这样做的好处如下：
1. 在最终用户可见的对话流中，使用更自然的消息替代原始数据。
2. 用简短的确认信息替代可能很长的数据块，减少token消耗。

当不设置 tool_message_content时，模型收到的 ToolMessage里就包含了像 {'name': '张三', 'email': 'zhangsan@email.com'... ...} 这样的具体数据。
当设置了tool_message_content时，模型收到的 ToolMessage只是一个预定义的确认信息，如“ 格式化输出成功！”。这种方式节省了上下文窗口的令牌 消耗，并且让对话流对最终用户更友好。 说明： 无论 tool_message_content如何设置，成功提取的结构化数据最终都会正确存入 result["structured_response"] 返回，自定义消息仅影响对话历史中的一条记录。
```
{
    'messages': [
        ...
        ToolMessage(
            content="Returning structured response: name='小明' email='songhk@atguigu.com' phone='12345678912'",
            name='ContactInfo',
            id='6e1b976c-1e86-41ba-baf0-7a220b95fb83',
            tool_call_id='call_qdjCgRH5xh8IVsPRKG5DrDKa'
        )
    ],
    'structured_response': ContactInfo(name='小明', email='songhk@atguigu.com', phone='12345678912')
}
```

```
{   
    'messages': [
      ...
        ToolMessage(
            content='已成功抽取信息',
            name='ContactInfo',
            id='a3ea467d-b8d3-437d-b953-878caac30eaa',
            tool_call_id='call_-7453331798454435228'
        )
    ],
    'structured_response': ContactInfo(name='小明', email='songhk@atguigu.com', phone='12345678912')
}
```


##### ③ type / AutoStrategy
官方没有在参数列表或官方文档列出这种策略，但阅读源码可以看到。
当我们直接传入一个定义类型时，LangChain会自动包装为AutoStrategy，触发自动选择策略：如果 模型支持原生结构化输出（如OpenAI、Anthropic Claude或xAI Grok），则优先使用 ProviderStrategy；否则使用ToolStrategy。

##### ④ None 
默认配置，表示不以结构化输出，以自然语言响应用户问题

### 7.3.4 错误处理：handle_errors参数
受限于模型能力，大模型输出的内容可能并不符合格式要求，ToolStrategy提供了结构化过程错误处理策略，以下是主要的几种方式及其用途：
- handle_errors=True： LangChain默认方式 ， handle_errors 捕获所有异常，并使用LangChain 内置的、信息明确的错误消息模板提示模型重试，确保最终能得到符合预定格式的有效数据。适用于大多数希望自动处理错误的通用场景。 
- handle_errors=False：关闭自动重试机制，任何异常都会 直接抛出，会中断程序运行。 
- handle_errors="自定义字符串"：捕获所有异常，但使用开发者 预设的固定字符串作为错误消 息。适用于需要统一、友好的用户提示，或进行特定业务引导的场景。 
- handle_errors=ExceptionType：仅 捕获指定类型(如ValueError) 或元组中的异常类型并进行重 试， 其他异常直接抛出。适用于需要 精准控制，只对特定错误进行重试的场景。 
- handle_errors=callable：灵活性最高的方式，使用开发者 自定义的函数来处理异常，可根据不 同的异常类型返回差异化的提示信息。适用于需要复杂、精细化错误处理的场景。

### 7.3.5 流式输出
通过invoke调用Agent时，内部可能经历多次调用，长时间看不到调用情况，用户体验不好，可以通 过流式调用（渐进式显示输出）优化用户体验， 实时显示 Agent 运行过程中的更新。特别是在处理 LLM 延迟时尤其有效。
流式输出好处： 
- 大型语言模型生成完整响应通常需要几秒钟时间，对于长输出可能达到10-20 秒，用户期望即时反 馈， 流式传输让等待过程更加可控。 
- 相比非流式传输需要用户长时间等待完整响应，流式传输可以立即显示文字逐渐出现的效果， 幅降低用户的等待焦虑。

| 模式                                          | 输出                                                | 适用场景                               |
| ------------------------------------------- | ------------------------------------------------- | ---------------------------------- |
| `agent.stream(..., stream_mode="updates")`  | 每个节点（LLM、Tool、Agent）的状态更新，不输出 Token               | 调试 Agent 执行流程、观察工具调用、查看 Agent Loop |
| `agent.stream(..., stream_mode="messages")` | LLM 实时生成的 Token，同时包含 Tool Calling、Tool Result 等消息 | 聊天机器人、Web 对话、需要打字机效果的应用            |
| `agent.stream(..., stream_mode="values")`   | 每轮循环结束后的完整 State（messages、structured_response 等）  | 调试 State、查看每轮 Agent 执行后的完整上下文      |
| `agent.stream(..., stream_mode="custom")`   | 开发者通过 `get_stream_writer()` 自定义输出内容               | 长耗时 Tool、任务进度、日志、百分比等自定义信息         |
| `agent.astream(...)`                        | `stream()` 的异步版本                                  | FastAPI、WebSocket、异步 Agent 服务      |
| `agent.astream_events(...)`                 | 输出整个 Agent 生命周期事件（Start、End、LLM、Tool、Chain 等）     | 可观测性、Tracing、监控、日志分析、LangSmith 集成  |
如果从工程角度，我通常这样选择：
- **前端聊天（Chat UI）** → `messages`
- **调试 Agent Loop** → `updates`
- **查看最终 State / Structured Output** → `values`
- **显示工具执行进度** → `custom`
- **FastAPI / WebSocket 服务** → `astream`
- **监控、日志、LangSmith、可观测性** → `astream_events`