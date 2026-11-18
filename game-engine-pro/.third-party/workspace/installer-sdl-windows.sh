#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_sdl_windows() {
    SRC_DIR=/workspace/src/SDL
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL
    DIST_DIR=/workspace/dist/x86_64-w64-mingw32/SDL

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDL_VIDEO=ON \
        -DSDL_AUDIO=ON \
        -DSDL_JOYSTICK=ON \
        -DSDL_SENSOR=ON \
        -DSDL_HAPTIC=ON \
        -DSDL_OPENGL=ON \
        -DSDL_VULKAN=ON

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_sdl_image_windows() {
    SRC_DIR=/workspace/src/SDL_image
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_image
    DIST_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_image

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLIMAGE_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_sdl_ttf_windows() {
    SRC_DIR=/workspace/src/SDL_ttf
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_ttf
    DIST_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_ttf

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLTTF_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_sdl_mixer_windows() {
    SRC_DIR=/workspace/src/SDL_mixer
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_mixer
    DIST_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_mixer

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLMIXER_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_sdl_windows
#install_sdl_image_windows
#install_sdl_ttf_windows
#install_sdl_mixer_windows
