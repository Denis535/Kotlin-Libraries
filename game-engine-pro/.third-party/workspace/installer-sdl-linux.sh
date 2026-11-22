#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_sdl_linux() {
    local SRC_DIR=/workspace/src/SDL
    local BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL
    local DIST_DIR=/workspace/dist/x86_64-linux-gnu/SDL

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
        -DSDL_X11=ON \
        -DSDL_X11_SHARED=ON \
        -DSDL_WAYLAND=OFF \
        -DSDL_WAYLAND_SHARED=ON \
        -DSDL_OPENGL=ON \
        -DSDL_VULKAN=ON

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_sdl_image_linux() {
    local SRC_DIR=/workspace/src/SDL_image
    local BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_image
    local DIST_DIR=/workspace/dist/x86_64-linux-gnu/SDL_image

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLIMAGE_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_sdl_mixer_linux() {
    local SRC_DIR=/workspace/src/SDL_mixer
    local BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_mixer
    local DIST_DIR=/workspace/dist/x86_64-linux-gnu/SDL_mixer

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLMIXER_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_sdl_ttf_linux() {
    local SRC_DIR=/workspace/src/SDL_ttf
    local BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_ttf
    local DIST_DIR=/workspace/dist/x86_64-linux-gnu/SDL_ttf

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLTTF_VENDORED=ON \
       -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_sdl_linux
#install_sdl_image_linux
#install_sdl_mixer_linux
#install_sdl_ttf_linux
