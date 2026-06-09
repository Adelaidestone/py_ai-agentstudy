#!/usr/bin/env bash
# ==============================================================================
# start.sh — 一键启动 helloai (Spring Boot)
#
# 做的事:
#   1. 加载 ~/.config/ai-keys/load-ai-env.sh，把所有 API key 导入到当前进程
#      （注意: 不能 source ~/.zshrc，因为 bash 解析不了 zsh 专属语法。
#        加载脚本本身是 POSIX 兼容写的，bash 也能正确执行。）
#   2. 校验关键变量是否存在（缺了直接给出可操作提示）
#   3. 切到项目根目录，执行 mvn spring-boot:run
#
# 用法:
#   ./scripts/start.sh                  # 默认 dev profile
#   ./scripts/start.sh --profile=prod   # 指定 Spring profile
#   ./scripts/start.sh --port=9090      # 覆盖端口
#   ./scripts/start.sh --debug          # 开启 JVM 5005 远程调试
#   ./scripts/start.sh --clean          # mvn clean 后再启动
#   ./scripts/start.sh --skip-tests     # 跳过测试（默认就跳过）
#   ./scripts/start.sh --help
# ==============================================================================

set -euo pipefail

# ---------- 路径定位 ----------
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
AI_ENV_LOADER="$HOME/.config/ai-keys/load-ai-env.sh"

# ---------- 颜色输出 ----------
if [ -t 1 ]; then
  C_BLUE='\033[1;34m'; C_GREEN='\033[1;32m'; C_YELLOW='\033[1;33m'
  C_RED='\033[1;31m'; C_DIM='\033[2m'; C_RESET='\033[0m'
else
  C_BLUE=''; C_GREEN=''; C_YELLOW=''; C_RED=''; C_DIM=''; C_RESET=''
fi

log()   { printf "${C_BLUE}[start]${C_RESET} %s\n" "$*"; }
ok()    { printf "${C_GREEN}[start]${C_RESET} %s\n" "$*"; }
warn()  { printf "${C_YELLOW}[start]${C_RESET} %s\n" "$*"; }
fail()  { printf "${C_RED}[start]${C_RESET} %s\n" "$*" >&2; exit 1; }

# ---------- 参数解析 ----------
PROFILE="dev"
PORT=""
DEBUG=0
CLEAN=0
SKIP_TESTS=1   # 默认跳过测试，启动更快

show_help() {
  sed -n '2,22p' "${BASH_SOURCE[0]}" | sed 's/^# \{0,1\}//'
}

for arg in "$@"; do
  case "$arg" in
    --profile=*)    PROFILE="${arg#*=}" ;;
    --port=*)       PORT="${arg#*=}" ;;
    --debug)        DEBUG=1 ;;
    --clean)        CLEAN=1 ;;
    --skip-tests)   SKIP_TESTS=1 ;;
    --with-tests)   SKIP_TESTS=0 ;;
    -h|--help)      show_help; exit 0 ;;
    *) warn "未知参数: $arg (用 --help 查看用法)" ;;
  esac
done

# ---------- 1. 加载 AI keys ----------
log "项目目录: ${C_DIM}$PROJECT_DIR${C_RESET}"

if [ -f "$AI_ENV_LOADER" ]; then
  log "加载 AI keys: ${C_DIM}$AI_ENV_LOADER${C_RESET}"
  # shellcheck disable=SC1090
  source "$AI_ENV_LOADER"
else
  warn "未找到 $AI_ENV_LOADER"
  warn "请先创建 ~/.config/ai-keys/keys.env （参考 keys.env.example）"
fi

# ---------- 2. 校验关键变量 ----------
REQUIRED_VARS=(DASHSCOPE_API_KEY ZILLIZ_TOKEN)
OPTIONAL_VARS=(MIMO_API_KEY MIMO_BASE_URL DEEPSEEK_API_KEY ZILLIZ_HOST)

missing=()
for v in "${REQUIRED_VARS[@]}"; do
  if [ -z "${!v:-}" ]; then
    missing+=("$v")
  fi
done

if [ ${#missing[@]} -gt 0 ]; then
  fail "缺少必要环境变量: ${missing[*]}
请编辑 ~/.config/ai-keys/keys.env 填入值后重试。"
fi

# 掩码打印（前 4 + 后 4）
mask() {
  local s="$1" len=${#1}
  if [ "$len" -le 8 ]; then echo "****"; else echo "${s:0:4}****${s: -4}"; fi
}

ok "环境变量校验通过:"
for v in "${REQUIRED_VARS[@]}" "${OPTIONAL_VARS[@]}"; do
  val="${!v:-}"
  if [ -n "$val" ]; then
    printf "     ${C_DIM}%-20s${C_RESET} = %s\n" "$v" "$(mask "$val")"
  fi
done

# ---------- 3. 检查 mvn / mvnw ----------
cd "$PROJECT_DIR"

if [ -x "./mvnw" ]; then
  MVN_CMD="./mvnw"
elif command -v mvn >/dev/null 2>&1; then
  MVN_CMD="mvn"
else
  fail "找不到 mvn 或 ./mvnw，请先安装 Maven 或在项目根添加 mvnw"
fi
log "Maven 命令: $MVN_CMD"

# ---------- 4. 组装 mvn 命令 ----------
MVN_ARGS=()

if [ "$CLEAN" = "1" ]; then
  log "执行 clean ..."
  "$MVN_CMD" -q clean
fi

MVN_ARGS+=("spring-boot:run")

# JVM 参数（profile / 端口 / debug）
JVM_OPTS=()
JVM_OPTS+=("-Dspring.profiles.active=$PROFILE")
[ -n "$PORT" ] && JVM_OPTS+=("-Dserver.port=$PORT")
if [ "$DEBUG" = "1" ]; then
  JVM_OPTS+=("-Xdebug")
  JVM_OPTS+=("-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005")
  warn "调试模式开启，监听端口 5005 (IDEA Remote JVM Debug 连这里)"
fi

# Spring Boot 3 推荐用 -Dspring-boot.run.jvmArguments 传递
JVM_ARGS_STR="${JVM_OPTS[*]}"
MVN_ARGS+=("-Dspring-boot.run.jvmArguments=$JVM_ARGS_STR")

if [ "$SKIP_TESTS" = "1" ]; then
  MVN_ARGS+=("-DskipTests")
fi

# ---------- 5. 启动 ----------
echo
ok "启动: $MVN_CMD ${MVN_ARGS[*]}"
echo "----------------------------------------"
# exec 替换进程，让 Ctrl+C 直接传给 java 进程
exec "$MVN_CMD" "${MVN_ARGS[@]}"
