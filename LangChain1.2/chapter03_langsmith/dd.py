import os, time
from dotenv import load_dotenv
load_dotenv(override=True)

from langchain_core.tracers.langchain import LangChainTracer
from langchain_community.chat_models.fake import FakeMessagesListChatModel
from langchain_core.messages import AIMessage

tracer = LangChainTracer(project_name="langchainstudy")
fake = FakeMessagesListChatModel(responses=[AIMessage(content="显式追踪测试")])
fake.invoke("test", config={"callbacks": [tracer]})

time.sleep(15)   # 替代 wait_for_all_tracers,给异步上传留够时间
print("跑完,去网站 langchainstudy 刷新")
