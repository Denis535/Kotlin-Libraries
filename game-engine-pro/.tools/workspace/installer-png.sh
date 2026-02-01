#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/png
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/png
    cd /workspace/libs/lpng

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=OFF \
        -DPNG_STATIC=ON \
        -DPNG_SHARED=OFF \
        -DPNG_TESTS=OFF \
        -DPNG_DEBUG=OFF \
        -DZLIB_INCLUDE_DIR=/workspace/dist/x86_64-w64-mingw32/zlib/include \
        -DZLIB_LIBRARY=/workspace/dist/x86_64-w64-mingw32/zlib/lib/libz.dll.a

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_linux() {
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/png
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/png
    cd /workspace/libs/lpng

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=OFF \
        -DPNG_STATIC=ON \
        -DPNG_SHARED=OFF \
        -DPNG_TESTS=OFF \
        -DPNG_DEBUG=OFF \
        -DZLIB_INCLUDE_DIR=/workspace/dist/x86_64-linux-gnu/zlib/include \
        -DZLIB_LIBRARY=/workspace/dist/x86_64-linux-gnu/zlib/lib/libz.a

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
