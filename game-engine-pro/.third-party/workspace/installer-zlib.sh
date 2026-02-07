#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_zlib_windows() {
    PROJ_DIR=/workspace/projects/zlib
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/zlib
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/zlib

    cmake -S "$PROJ_DIR"  -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DBUILD_SHARED_LIBS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_zlib_linux() {
    PROJ_DIR=/workspace/projects/zlib
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/zlib
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/zlib

    cmake -S "$PROJ_DIR"  -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DBUILD_SHARED_LIBS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
