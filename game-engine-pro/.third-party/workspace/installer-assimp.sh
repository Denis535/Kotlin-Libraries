#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_assimp_windows() {
    PROJ_DIR=/workspace/projects/assimp
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/assimp
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/assimp

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DCMAKE_CXX_FLAGS="-Wno-error=maybe-uninitialized" \
      -DBUILD_SHARED_LIBS=OFF \
      -DASSIMP_BUILD_TESTS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_assimp_linux() {
    PROJ_DIR=/workspace/projects/assimp
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/assimp
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/assimp

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DCMAKE_CXX_FLAGS="-Wno-error=maybe-uninitialized" \
      -DBUILD_SHARED_LIBS=OFF \
      -DASSIMP_BUILD_TESTS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
