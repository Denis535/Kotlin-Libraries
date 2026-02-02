#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_ktx_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/ktx
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/ktx
    cd /workspace/libs/KTX-Software

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=OFF \
        -DKTX_FEATURE_TOOLS=OFF \
        -DKTX_FEATURE_TESTS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_ktx_linux() {
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/ktx
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/ktx
    cd /workspace/libs/KTX-Software

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=OFF \
        -DKTX_FEATURE_TOOLS=OFF \
        -DKTX_FEATURE_TESTS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
