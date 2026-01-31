#!/usr/bin/env bash
set -e

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/KTX-Software" \
  dockcross/windows-shared-x64 \
  bash -euxc '
BUILD_DIR=../../build/x86_64-w64-mingw32/libktx
INSTALL_DIR=../../dist/x86_64-w64-mingw32/libktx

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
-DCMAKE_BUILD_TYPE=Release \
-DBUILD_SHARED_LIBS=ON \
-DKTX_FEATURE_TOOLS=OFF \
-DKTX_FEATURE_TESTS=OFF

cmake --build $BUILD_DIR -- -j$(nproc)
cmake --install $BUILD_DIR --prefix $INSTALL_DIR
'

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/libs/KTX-Software" \
  linux-x64 \
  bash -euxc '
BUILD_DIR=../../build/x86_64-linux-gnu/libktx
INSTALL_DIR=../../dist/x86_64-linux-gnu/libktx

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
-DCMAKE_BUILD_TYPE=Release \
-DBUILD_SHARED_LIBS=ON \
-DKTX_FEATURE_TOOLS=OFF \
-DKTX_FEATURE_TESTS=OFF

cmake --build $BUILD_DIR -- -j$(nproc)
cmake --install $BUILD_DIR --prefix $INSTALL_DIR
'
