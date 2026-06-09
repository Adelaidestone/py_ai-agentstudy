#!/usr/bin/env bash
# ==============================================================================
# stop.sh — 一键停止 helloai (Spring Boot)
#
# 做的事:
#   1. 通过两种方式定位进程，避免漏杀:
#        a. 占用端口的进程 (默认 8080，可用 --port 覆盖)
#        b. 命令行匹配 spring-boot:run + 项目关键字 (helloai / HelloaiApplication)
#      取两者并集，去重。
#   2. 先 SIGTERM 优雅关闭，最多等 GRACE 秒；超时再 SIGKILL 强杀。
#   3. 打印每个进程的 PID / 命令简写，便于回溯。
#
# 用法:
#   ./scripts/stop.sh                  # 停默认 8080 端口的 helloai
#   ./scripts/stop.sh --port=9090      # 停指定端口
#   ./scripts/stop.sh --force          # 直接 SIGKILL，不等优雅
#   ./scripts/stop.sh --dry-run        # 只列出会被杀的进程，不动手
#   ./scripts/stop.sh --grace=30       # 优雅关闭超时（秒），默认 15
#   ./scripts/stop.sh --help
# ==============================================================================

set -euo pipefail

# ---------- 路径定位 ----------
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

# ---------- 颜色 ----------
if [ -t 1 ]; then
  C_BLUE='\033[1;34m'; C_GREEN='\033[1;32m'; C_YELLOW='\033[1;33m'
  C_RED='\033[1;31m'; C_DIM='\033[2m'; C_RESET='\033[0m'
else
  C_BLUE=''; C_GREEN=''; C_YELLOW=''; C_RED=''; C_DIM=''; C_RESET=''
fi

log()   { printf "${C_BLUE}[stop]${C_RESET} %s\n" "$*"; }
ok()    { printf "${C_GREEN}[stop]${C_RESET} %s\n" "$*"; }
warn()  { printf "${C_YELLOW}[stop]${C_RESET} %s\n" "$*"; }
fail()  { printf "${C_RED}[stop]${C_RESET} %s\n" "$*" >&2; exit 1; }

# ---------- 参数 ----------
PORT=8080
FORCE=0
DRY_RUN=0
GRACE=15
# 命令行匹配关键字（任一命中即视为本项目进程）
# 注意：mvn 父进程的命令行里只有 "spring-boot:run"，不含 helloai；
# 我们额外用 PROJECT_TAG 作为第二维过滤，避免误杀别的项目的 spring-boot 进程。
PATTERNS=(
  "spring-boot:run"
  "HelloaiApplication"
  "com\\.fletcher\\.helloai"
)
# 二次过滤用的项目辨识词。
# 不直接用 PROJECT_DIR：路径含中文时 ps/lsof 输出会被转义（M-eM-..../\xe5...），
# 字符串匹配会失败。用项目目录名（纯 ASCII）作为唯一标识。
PROJECT_TAG="$(basename "$PROJECT_DIR")"   # = "01-helloai"

show_help() { sed -n '2,22p' "${BASH_SOURCE[0]}" | sed 's/^# \{0,1\}//'; }

for arg in "$@"; do
  case "$arg" in
    --port=*)   PORT="${arg#*=}" ;;
    --grace=*)  GRACE="${arg#*=}" ;;
    --force)    FORCE=1 ;;
    --dry-run)  DRY_RUN=1 ;;
    -h|--help)  show_help; exit 0 ;;
    *) warn "未知参数: $arg (用 --help 查看用法)" ;;
  esac
done

# 判断给定 PID 是否属于本项目（命令行/CWD 含 PROJECT_TAG）
belongs_to_project() {
  local pid="$1"
  local cmd
  cmd=$(ps -o command= -p "$pid" 2>/dev/null || true)
  if [ -n "$cmd" ] && [[ "$cmd" == *"$PROJECT_TAG"* ]]; then
    return 0
  fi
  if command -v lsof >/dev/null 2>&1; then
    local cwd
    cwd=$(lsof -a -p "$pid" -d cwd -Fn 2>/dev/null | awk '/^n/{print substr($0,2)}' | head -n1)
    if [ -n "$cwd" ] && [[ "$cwd" == *"$PROJECT_TAG"* ]]; then
      return 0
    fi
  fi
  return 1
}

# ---------- 收集 PID ----------
collect_pids() {
  local -a pids=()

  # 1) 端口持有者：无条件保留（端口本身就是强信号）
  if command -v lsof >/dev/null 2>&1; then
    while IFS= read -r pid; do
      [ -n "$pid" ] && pids+=("$pid")
    done < <(lsof -nP -iTCP:"$PORT" -sTCP:LISTEN -t 2>/dev/null || true)
  fi

  # 2) 命令行匹配：但必须二次确认属于本项目，避免误杀其他 spring-boot 工程
  for pat in "${PATTERNS[@]}"; do
    while IFS= read -r pid; do
      [ -z "$pid" ] && continue
      if belongs_to_project "$pid"; then
        pids+=("$pid")
      fi
    done < <(pgrep -f "$pat" 2>/dev/null || true)
  done

  # 排除当前脚本自身
  local self=$$
  local parent=$PPID
  local -a filtered=()
  if [ ${#pids[@]} -gt 0 ]; then
    for p in "${pids[@]}"; do
      [ "$p" = "$self" ] && continue
      [ "$p" = "$parent" ] && continue
      filtered+=("$p")
    done
  fi

  # 去重并按 PID 升序
  if [ ${#filtered[@]} -gt 0 ]; then
    printf '%s\n' "${filtered[@]}" | awk '!seen[$0]++' | sort -n
  fi
}

PIDS=()
while IFS= read -r pid; do
  [ -n "$pid" ] && PIDS+=("$pid")
done < <(collect_pids)

if [ ${#PIDS[@]} -eq 0 ]; then
  ok "未发现运行中的 helloai 进程（端口 $PORT 空闲，无匹配的 java/mvn）"
  exit 0
fi

# ---------- 打印目标进程 ----------
log "命中以下进程（端口=${PORT}，模式=${PATTERNS[*]}）："
printf "${C_DIM}  %-8s %-8s %s${C_RESET}\n" "PID" "PPID" "COMMAND"
for pid in "${PIDS[@]}"; do
  # ps 在 macOS / Linux 都支持这套字段
  if line=$(ps -o pid=,ppid=,command= -p "$pid" 2>/dev/null); then
    # 命令截断到 120 字符防止刷屏
    cmd=$(echo "$line" | awk '{$1=$2=""; sub(/^ +/,""); print}')
    cmd_short=$(echo "$cmd" | cut -c1-120)
    ppid=$(echo "$line" | awk '{print $2}')
    printf "  %-8s %-8s %s\n" "$pid" "$ppid" "$cmd_short"
  else
    printf "  %-8s %-8s %s\n" "$pid" "?" "(进程已消失)"
  fi
done

if [ "$DRY_RUN" = "1" ]; then
  warn "dry-run 模式，未发送任何信号"
  exit 0
fi

# ---------- 杀进程 ----------
SIG="TERM"
if [ "$FORCE" = "1" ]; then
  SIG="KILL"
  warn "--force 模式：直接 SIGKILL"
fi

log "发送 SIG$SIG ..."
for pid in "${PIDS[@]}"; do
  if kill -"$SIG" "$pid" 2>/dev/null; then
    printf "  ${C_DIM}sent SIG%s -> %s${C_RESET}\n" "$SIG" "$pid"
  else
    printf "  ${C_DIM}skip %s (已不存在或无权限)${C_RESET}\n" "$pid"
  fi
done

# SIGKILL 不需要等待
if [ "$SIG" = "KILL" ]; then
  sleep 1
fi

# ---------- 等待退出 / 超时强杀 ----------
if [ "$SIG" = "TERM" ]; then
  log "等待优雅退出（最多 ${GRACE}s）..."
  for i in $(seq 1 "$GRACE"); do
    alive=0
    for pid in "${PIDS[@]}"; do
      if kill -0 "$pid" 2>/dev/null; then
        alive=1
        break
      fi
    done
    if [ "$alive" = "0" ]; then
      ok "全部进程已优雅退出（${i}s）"
      break
    fi
    sleep 1
  done

  # 超时强杀残留
  remaining=()
  for pid in "${PIDS[@]}"; do
    if kill -0 "$pid" 2>/dev/null; then
      remaining+=("$pid")
    fi
  done
  if [ ${#remaining[@]} -gt 0 ]; then
    warn "超时未退出，SIGKILL: ${remaining[*]}"
    for pid in "${remaining[@]}"; do
      kill -KILL "$pid" 2>/dev/null || true
    done
    sleep 1
  fi
fi

# ---------- 最终确认 ----------
still_alive=()
for pid in "${PIDS[@]}"; do
  if kill -0 "$pid" 2>/dev/null; then
    still_alive+=("$pid")
  fi
done

if [ ${#still_alive[@]} -gt 0 ]; then
  fail "以下进程仍存活：${still_alive[*]}（建议手动 sudo kill -9）"
fi

# 端口释放确认
if command -v lsof >/dev/null 2>&1; then
  if lsof -nP -iTCP:"$PORT" -sTCP:LISTEN >/dev/null 2>&1; then
    warn "端口 $PORT 仍被占用，可能有其他进程在监听"
  else
    ok "端口 $PORT 已释放"
  fi
fi

ok "helloai 已停止"
