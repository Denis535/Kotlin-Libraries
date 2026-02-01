#!/usr/bin/env bash
set -e

WINDOWS_SCRIPT=$(cat <<'EOF'
BUILD_DIR=../../build/x86_64-w64-mingw32/ktx
INSTALL_DIR=../../dist/x86_64-w64-mingw32/ktx

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
-DCMAKE_BUILD_TYPE=Release \
-DBUILD_SHARED_LIBS=ON \
-DKTX_FEATURE_TOOLS=OFF \
-DKTX_FEATURE_TESTS=OFF

cmake --build "$BUILD_DIR" -- -j$(nproc)
cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
EOF
)

LINUX_SCRIPT=$(cat <<'EOF'
BUILD_DIR=../../build/x86_64-linux-gnu/ktx
INSTALL_DIR=../../dist/x86_64-linux-gnu/ktx

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
-DCMAKE_BUILD_TYPE=Release \
-DBUILD_SHARED_LIBS=ON \
-DKTX_FEATURE_TOOLS=OFF \
-DKTX_FEATURE_TESTS=OFF

cmake --build "$BUILD_DIR" -- -j$(nproc)
cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
EOF
)

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/KTX-Software" \
  dockcross/windows-shared-x64 \
  bash -euxc "$WINDOWS_SCRIPT"

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/KTX-Software" \
  dockcross/linux-x64 \
  bash -euxc "$LINUX_SCRIPT"
