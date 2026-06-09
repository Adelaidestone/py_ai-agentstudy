#!/usr/bin/env bash
# ==============================================================================
# restart.sh — 一键重启 helloai
#
# 等价于: ./scripts/stop.sh && ./scripts/start.sh
# 所有参数会同时透传给 stop.sh 和 start.sh 中能识别的那一个：
#   --port=9090       两边都识别
#   --force           只对 stop.sh 生效
#   --clean / --debug 只对 start.sh 生效
#
# 用法:
#   ./scripts/restart.sh
#   ./scripts/restart.sh --port=9090 --clean
#   ./scripts/restart.sh --force --debug
# ==============================================================================

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# 分流参数：stop 只关心 --port/--force/--grace/--dry-run，其它都给 start
STOP_ARGS=()
START_ARGS=()
for arg in "$@"; do
  case "$arg" in
    --port=*)     STOP_ARGS+=("$arg"); START_ARGS+=("$arg") ;;
    --force|--grace=*|--dry-run) STOP_ARGS+=("$arg") ;;
    *)            START_ARGS+=("$arg") ;;
  esac
done

# 注意：bash 在 set -u 下，空数组 "${arr[@]}" 会报 unbound；
# 用 ${arr[@]+"${arr[@]}"} 仅在数组非空时才展开。
"$SCRIPT_DIR/stop.sh"  ${STOP_ARGS[@]+"${STOP_ARGS[@]}"}
exec "$SCRIPT_DIR/start.sh" ${START_ARGS[@]+"${START_ARGS[@]}"}
