#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_png_windows() {
    PROJ_DIR=/workspace/libs/png
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/png
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/png

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DBUILD_SHARED_LIBS=OFF \
      -DZLIB_INCLUDE_DIR=/workspace/dist/x86_64-w64-mingw32/zlib/include \
      -DZLIB_LIBRARY=/workspace/dist/x86_64-w64-mingw32/zlib/lib/libz.dll.a

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_png_linux() {
    PROJ_DIR=/workspace/libs/png
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/png
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/png

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DBUILD_SHARED_LIBS=OFF \
      -DZLIB_INCLUDE_DIR=/workspace/dist/x86_64-linux-gnu/zlib/include \
      -DZLIB_LIBRARY=/workspace/dist/x86_64-linux-gnu/zlib/lib/libz.so

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
