#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_miniaudio_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/miniaudio
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/miniaudio
    cd /workspace/libs/miniaudio

    cmake -S . -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
      -DBUILD_SHARED_LIBS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_miniaudio_linux() {
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/miniaudio
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/miniaudio
    cd /workspace/libs/miniaudio

    cmake -S . -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
      -DBUILD_SHARED_LIBS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
