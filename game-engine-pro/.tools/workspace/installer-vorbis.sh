#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/vorbis
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/vorbis
    cd /workspace/libs/libvorbis

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=ON \
        -DOGG_INCLUDE_DIR=/workspace/dist/x86_64-w64-mingw32/ogg/include \
        -DOGG_LIBRARY=/workspace/dist/x86_64-w64-mingw32/ogg/lib/libogg.dll.a

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_linux() {
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/vorbis
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/vorbis
    cd /workspace/libs/libvorbis

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DBUILD_SHARED_LIBS=ON \
        -DOGG_INCLUDE_DIR=/workspace/dist/x86_64-linux-gnu/ogg/include \
        -DOGG_LIBRARY=/workspace/dist/x86_64-linux-gnu/ogg/lib/libogg.so

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
