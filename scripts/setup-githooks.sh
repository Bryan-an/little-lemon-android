#!/usr/bin/env bash
set -euo pipefail

repo_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$repo_root"

git config core.hooksPath .githooks
chmod +x .githooks/pre-commit .githooks/pre-push .githooks/commit-msg

echo "Git hooks enabled (core.hooksPath=.githooks)."
echo "Hooks installed:"
echo "  - pre-commit: ./gradlew spotlessCheck"
echo "  - pre-push:   ./gradlew check"
echo "  - commit-msg: Conventional Commits-ish validation"


