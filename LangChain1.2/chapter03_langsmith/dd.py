# ⚠️ 重启 kernel 后第一个跑的 cell —— load_dotenv 必须在任何 langchain import 之前
import os
from dotenv import load_dotenv
load_dotenv(override=True)
print("LANGSMITH_TRACING =", os.getenv("LANGSMITH_TRACING"))  # 应输出 true
