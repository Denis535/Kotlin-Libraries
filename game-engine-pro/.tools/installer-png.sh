#!/usr/bin/env bash
set -e

WINDOWS_SCRIPT=$(cat <<'EOF'
BUILD_DIR=../../build/x86_64-w64-mingw32/libpng
INSTALL_DIR=../../dist/x86_64-w64-mingw32/libpng

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
-DCMAKE_BUILD_TYPE=Release \
-DPNG_STATIC=OFF \
-DPNG_SHARED=ON \
-DPNG_TESTS=OFF \
-DPNG_DEBUG=OFF \
-DZLIB_INCLUDE_DIR=/workspace/dist/x86_64-w64-mingw32/zlib/include \
-DZLIB_LIBRARY=/workspace/dist/x86_64-w64-mingw32/zlib/lib/libz.dll.a

cmake --build "$BUILD_DIR" -- -j$(nproc)
cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
EOF
)

LINUX_SCRIPT=$(cat <<'EOF'
BUILD_DIR=../../build/x86_64-linux-gnu/libpng
INSTALL_DIR=../../dist/x86_64-linux-gnu/libpng

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
-DCMAKE_BUILD_TYPE=Release \
-DPNG_STATIC=OFF \
-DPNG_SHARED=ON \
-DPNG_TESTS=OFF \
-DPNG_DEBUG=OFF \
-DZLIB_INCLUDE_DIR=/workspace/dist/x86_64-linux-gnu/zlib/include \
-DZLIB_LIBRARY=/workspace/dist/x86_64-linux-gnu/zlib/lib/libz.a

cmake --build "$BUILD_DIR" -- -j$(nproc)
cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
EOF
)

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/lpng" \
  dockcross/windows-shared-x64 \
  bash -euxc "$WINDOWS_SCRIPT"

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/lpng" \
  dockcross/linux-x64 \
  bash -euxc "$LINUX_SCRIPT"
