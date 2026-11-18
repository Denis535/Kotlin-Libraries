#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_webp_windows() {
    SRC_DIR=/workspace/src/webp
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/webp
    DIST_DIR=/workspace/dist/x86_64-w64-mingw32/webp

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DWEBP_ENABLE_SIMD=ON \
        -DWEBP_BUILD_LIBWEBPMUX=OFF \
        -DWEBP_BUILD_ANIM_UTILS=OFF \
        -DWEBP_BUILD_CWEBP=OFF \
        -DWEBP_BUILD_DWEBP=OFF \
        -DWEBP_BUILD_GIF2WEBP=OFF \
        -DWEBP_BUILD_IMG2WEBP=OFF \
        -DWEBP_BUILD_VWEBP=OFF \
        -DWEBP_BUILD_WEBPINFO=OFF \
        -DWEBP_BUILD_WEBPMUX=OFF \
        -DWEBP_BUILD_EXTRAS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_webp_linux() {
    SRC_DIR=/workspace/src/webp
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/webp
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/webp

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DWEBP_ENABLE_SIMD=ON \
        -DWEBP_BUILD_LIBWEBPMUX=OFF \
        -DWEBP_BUILD_ANIM_UTILS=OFF \
        -DWEBP_BUILD_CWEBP=OFF \
        -DWEBP_BUILD_DWEBP=OFF \
        -DWEBP_BUILD_GIF2WEBP=OFF \
        -DWEBP_BUILD_IMG2WEBP=OFF \
        -DWEBP_BUILD_VWEBP=OFF \
        -DWEBP_BUILD_WEBPINFO=OFF \
        -DWEBP_BUILD_WEBPMUX=OFF \
        -DWEBP_BUILD_EXTRAS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

TRIPLE="${CROSS_TRIPLE:-$(gcc -dumpmachine)}"
if [[ "$TRIPLE" == *mingw32* ]]; then
    install_webp_windows
elif [[ "$TRIPLE" == *linux* ]]; then
    install_webp_linux
else
    echo "Unsupported target: $TRIPLE" >&2
    exit 1
fi
