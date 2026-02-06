#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_opus_windows() {
    PROJ_DIR=/workspace/libs/opus
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/opus
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/opus

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DBUILD_SHARED_LIBS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_opus_linux() {
    PROJ_DIR=/workspace/libs/opus
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/opus
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/opus

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DBUILD_SHARED_LIBS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
