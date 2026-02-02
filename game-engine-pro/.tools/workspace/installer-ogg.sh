#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_ogg_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/ogg
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/ogg
    cd /workspace/libs/libogg

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_ogg_linux() {
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/ogg
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/ogg
    cd /workspace/libs/libogg

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
