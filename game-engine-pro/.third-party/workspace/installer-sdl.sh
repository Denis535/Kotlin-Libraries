#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_sdl_windows() {
    PROJ_DIR=/workspace/projects/SDL
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
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
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_sdl_linux() {
    PROJ_DIR=/workspace/projects/SDL
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
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
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_sdl_image_windows() {
    PROJ_DIR=/workspace/projects/SDL_image
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_image
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_image

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLIMAGE_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_sdl_image_linux() {
    PROJ_DIR=/workspace/projects/SDL_image
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_image
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL_image

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLIMAGE_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_sdl_ttf_windows() {
    PROJ_DIR=/workspace/projects/SDL_ttf
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_ttf
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_ttf

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLTTF_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_sdl_ttf_linux() {
    PROJ_DIR=/workspace/projects/SDL_ttf
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_ttf
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL_ttf

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLTTF_VENDORED=ON \
       -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_sdl_mixer_windows() {
    PROJ_DIR=/workspace/projects/SDL_mixer
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_mixer
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_mixer

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLMIXER_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}

install_sdl_mixer_linux() {
    PROJ_DIR=/workspace/projects/SDL_mixer
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_mixer
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL_mixer

    cmake -S "$PROJ_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DSDL_STATIC=OFF \
        -DSDL_SHARED=ON \
        -DSDL_ENABLE_PCH=OFF \
        -DSDLMIXER_VENDORED=ON \
        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3"

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
}
