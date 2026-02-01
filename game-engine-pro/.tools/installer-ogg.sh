#!/usr/bin/env bash
set -e

WINDOWS_SCRIPT=$(cat <<'EOF'
BUILD_DIR=../../build/x86_64-w64-mingw32/ogg
INSTALL_DIR=../../dist/x86_64-w64-mingw32/ogg

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
  -DCMAKE_BUILD_TYPE=Release \
  -DBUILD_SHARED_LIBS=ON

cmake --build "$BUILD_DIR" -- -j$(nproc)
cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
EOF
)

LINUX_SCRIPT=$(cat <<'EOF'
BUILD_DIR=../../build/x86_64-linux-gnu/ogg
INSTALL_DIR=../../dist/x86_64-linux-gnu/ogg

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
  -DCMAKE_BUILD_TYPE=Release \
  -DBUILD_SHARED_LIBS=ON

cmake --build "$BUILD_DIR" -- -j$(nproc)
cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
EOF
)

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/libogg" \
  dockcross/windows-shared-x64 \
  bash -euxc "$WINDOWS_SCRIPT"

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/libogg" \
  dockcross/linux-x64 \
  bash -euxc "$LINUX_SCRIPT"
