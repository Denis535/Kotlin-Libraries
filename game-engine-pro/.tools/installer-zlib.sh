#!/usr/bin/env bash
set -e

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/zlib" \
  dockcross/windows-shared-x64 \
  bash -euxc '
BUILD_DIR=../../build/x86_64-w64-mingw32/zlib
INSTALL_DIR=../../dist/x86_64-w64-mingw32/zlib

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
  -DCMAKE_BUILD_TYPE=Release \
  -DBUILD_SHARED_LIBS=ON \
  -DZLIB_BUILD_EXAMPLES=OFF

cmake --build "$BUILD_DIR" -- -j$(nproc)
cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
'

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/zlib" \
  linux-x64 \
  bash -euxc '
BUILD_DIR=../../build/x86_64-linux-gnu/zlib
INSTALL_DIR=../../dist/x86_64-linux-gnu/zlib

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
  -DCMAKE_BUILD_TYPE=Release \
  -DBUILD_SHARED_LIBS=ON \
  -DZLIB_BUILD_EXAMPLES=OFF

cmake --build "$BUILD_DIR" -- -j$(nproc)
cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
'
