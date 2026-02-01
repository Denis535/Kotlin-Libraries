#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL
    cd /workspace/libs/SDL

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DSDL_STATIC=ON \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDL_TESTS=OFF \
        -DSDL_TEST_LIBRARY=OFF \
        -DSDL_INSTALL_TESTS=OFF \
        -DSDL_VIDEO=ON \
        -DSDL_VIDEO_WINDOWS=ON \
        -DSDL_VIDEO_X11=OFF \
        -DSDL_VIDEO_WAYLAND=OFF \
        -DSDL_RENDER_OPENGL=ON \
        -DSDL_RENDER_VULKAN=ON \
        -DSDL_AUDIO=ON \
        -DSDL_AUDIO_WASAPI=ON \
        -DSDL_JOYSTICK=ON \
        -DSDL_SENSOR=ON \
        -DSDL_HAPTIC=ON

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_linux() {
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL
    cd /workspace/libs/SDL

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_TOOLCHAIN_FILE="" \
        -DCMAKE_C_COMPILER=/usr/bin/gcc \
        -DCMAKE_CXX_COMPILER=/usr/bin/g++ \
        -DCMAKE_C_FLAGS="-U__GLIBC_USE_ISOC23 -D_GNU_SOURCE" \
        -DSDL_STATIC=ON \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDL_TESTS=OFF \
        -DSDL_TEST_LIBRARY=OFF \
        -DSDL_INSTALL_TESTS=OFF \
        -DSDL_VIDEO=ON \
        -DSDL_VIDEO_WINDOWS=OFF \
        -DSDL_VIDEO_X11=ON \
        -DSDL_VIDEO_WAYLAND=ON \
        -DSDL_RENDER_OPENGL=ON \
        -DSDL_RENDER_VULKAN=ON \
        -DSDL_AUDIO=ON \
        -DSDL_AUDIO_JACK=ON \
        -DSDL_JOYSTICK=ON \
        -DSDL_SENSOR=ON \
        -DSDL_HAPTIC=ON

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
