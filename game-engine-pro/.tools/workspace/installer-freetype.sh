#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_freetype_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/freetype
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/freetype
    cd /workspace/libs/freetype

    cmake -S . -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
      -DBUILD_SHARED_LIBS=OFF \
      -DFT_REQUIRE_ZLIB=ON \
      -DFT_REQUIRE_PNG=OFF \
      -DZLIB_INCLUDE_DIR=/workspace/dist/x86_64-w64-mingw32/zlib/include \
      -DZLIB_LIBRARY=/workspace/dist/x86_64-w64-mingw32/zlib/lib/libz.dll.a

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_freetype_linux() {
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/freetype
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/freetype
    cd /workspace/libs/freetype

    cmake -S . -B "$BUILD_DIR" \
      -DCMAKE_BUILD_TYPE=Release \
      -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
      -DBUILD_SHARED_LIBS=OFF \
      -DFT_REQUIRE_ZLIB=ON \
      -DFT_REQUIRE_PNG=OFF \
      -DZLIB_INCLUDE_DIR=/workspace/dist/x86_64-linux-gnu/zlib/include \
      -DZLIB_LIBRARY=/workspace/dist/x86_64-linux-gnu/zlib/lib/libz.so

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
