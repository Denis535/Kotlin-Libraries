#!/usr/bin/env bash

docker build -t linux-x64 -f linux-x64.dockerfile .

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/SDL" \
  dockcross/windows-shared-x64 \
  bash -euxc '
BUILD_DIR=../build/x86_64-w64-mingw32
INSTALL_DIR=../dist/x86_64-w64-mingw32/SDL

export DEBIAN_FRONTEND=noninteractive
export CC=x86_64-w64-mingw32-gcc
export CXX=x86_64-w64-mingw32-g++
export RC=x86_64-w64-mingw32-windres

cmake -S . -B "$BUILD_DIR" \
-DCMAKE_BUILD_TYPE=Release \
-DSDL_STATIC=OFF \
-DSDL_SHARED=ON \
-DSDL_ENABLE_PCH=OFF \
-DSDL_TEST_LIBRARY=OFF \
-DSDL_TESTS=OFF \
-DSDL_INSTALL_TESTS=OFF \
-DSDL_VIDEO=ON \
-DSDL_VIDEO_WINDOWS=ON \
-DSDL_VIDEO_X11=OFF \
-DSDL_VIDEO_WAYLAND=OFF \
-DSDL_RENDER_OPENGL=ON \
-DSDL_RENDER_VULKAN=ON \
-DSDL_AUDIO=ON \
-DSDL_AUDIO_WASAPI=ON \
-DSDL_JOYSTICK=ON \
-DSDL_SENSOR=ON \
-DSDL_HAPTIC=ON

cmake --build $BUILD_DIR -- -j$(nproc)
cmake --install $BUILD_DIR --prefix $INSTALL_DIR
'

docker run \
  --rm \
  --mount type=bind,source="$PWD/workspace",target="/workspace" \
  -w "/workspace/SDL" \
  linux-x64 \
  bash -euxc '
BUILD_DIR=../build/x86_64-linux-gnu/SDL
INSTALL_DIR=../dist/x86_64-linux-gnu/SDL

export DEBIAN_FRONTEND=noninteractive

cmake -S . -B "$BUILD_DIR" \
-DCMAKE_BUILD_TYPE=Release \
-DCMAKE_C_FLAGS="-U__GLIBC_USE_ISOC23 -D_GNU_SOURCE" \
-DCMAKE_C_COMPILER=/usr/bin/gcc \
-DCMAKE_CXX_COMPILER=/usr/bin/g++ \
-DCMAKE_TOOLCHAIN_FILE="" \
-DSDL_STATIC=OFF \
-DSDL_SHARED=ON \
-DSDL_ENABLE_PCH=OFF \
-DSDL_TEST_LIBRARY=OFF \
-DSDL_TESTS=OFF \
-DSDL_INSTALL_TESTS=OFF \
-DSDL_VIDEO=ON \
-DSDL_VIDEO_WINDOWS=OFF \
-DSDL_VIDEO_X11=ON \
-DSDL_VIDEO_WAYLAND=ON \
-DSDL_RENDER_OPENGL=ON \
-DSDL_RENDER_VULKAN=ON \
-DSDL_AUDIO=ON \
-DSDL_AUDIO_JACK=ON \
-DSDL_JOYSTICK=ON \
-DSDL_SENSOR=ON \
-DSDL_HAPTIC=ON

cmake --build $BUILD_DIR -- -j$(nproc)
cmake --install $BUILD_DIR --prefix $INSTALL_DIR
'
