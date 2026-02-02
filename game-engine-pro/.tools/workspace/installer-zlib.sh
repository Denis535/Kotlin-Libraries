#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_zlib_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/zlib
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/zlib
    cd /workspace/libs/zlib

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=OFF \
        -DZLIB_BUILD_EXAMPLES=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_zlib_linux() {
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/zlib
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/zlib
    cd /workspace/libs/zlib

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=OFF \
        -DZLIB_BUILD_EXAMPLES=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
