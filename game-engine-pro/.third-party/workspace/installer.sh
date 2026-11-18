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
        -DWEBP_BUILD_GIF2WEBP=OFF \
        -DWEBP_BUILD_IMG2WEBP=OFF \
        -DWEBP_BUILD_CWEBP=OFF \
        -DWEBP_BUILD_DWEBP=OFF \
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
        -DWEBP_BUILD_GIF2WEBP=OFF \
        -DWEBP_BUILD_IMG2WEBP=OFF \
        -DWEBP_BUILD_CWEBP=OFF \
        -DWEBP_BUILD_DWEBP=OFF \
        -DWEBP_BUILD_VWEBP=OFF \
        -DWEBP_BUILD_WEBPINFO=OFF \
        -DWEBP_BUILD_WEBPMUX=OFF \
        -DWEBP_BUILD_EXTRAS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_ogg_linux() {
    SRC_DIR=/workspace/src/ogg
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/ogg
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/ogg

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DBUILD_TESTING=OFF \
        -DINSTALL_DOCS=OFF \
        -DINSTALL_PKG_CONFIG_MODULE=OFF \
        -DINSTALL_CMAKE_PACKAGE_MODULE=OFF

    cmake --build "$BUILD_DIR" --parallel "$(nproc)"
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_ogg_windows() {
    SRC_DIR=/workspace/src/ogg
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/ogg
    DIST_DIR=/workspace/dist/x86_64-w64-mingw32/ogg

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DBUILD_TESTING=OFF \
        -DINSTALL_DOCS=OFF \
        -DINSTALL_PKG_CONFIG_MODULE=OFF \
        -DINSTALL_CMAKE_PACKAGE_MODULE=OFF

    cmake --build "$BUILD_DIR" --parallel "$(nproc)"
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_opus_linux() {
    SRC_DIR=/workspace/src/opus
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/opus
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/opus

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DOPUS_BUILD_SHARED_LIBRARY=OFF \
        -DOPUS_BUILD_PROGRAMS=OFF \
        -DOPUS_BUILD_TESTING=OFF \
        -DOPUS_INSTALL_PKG_CONFIG_MODULE=OFF \
        -DOPUS_INSTALL_CMAKE_CONFIG_MODULE=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_opus_windows() {
    SRC_DIR=/workspace/src/opus
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/opus
    DIST_DIR=/workspace/dist/x86_64-w64-mingw32/opus

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DOPUS_BUILD_SHARED_LIBRARY=OFF \
        -DOPUS_BUILD_PROGRAMS=OFF \
        -DOPUS_BUILD_TESTING=OFF \
        -DOPUS_INSTALL_PKG_CONFIG_MODULE=OFF \
        -DOPUS_INSTALL_CMAKE_CONFIG_MODULE=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

TRIPLE="${CROSS_TRIPLE:-$(gcc -dumpmachine)}"
if [[ "$TRIPLE" == *mingw32* ]]; then
    install_webp_windows
    install_ogg_windows
    install_opus_windows
elif [[ "$TRIPLE" == *linux* ]]; then
    install_webp_linux
    install_ogg_linux
    install_opus_linux
else
    echo "Unsupported target: $TRIPLE" >&2
    exit 1
fi
