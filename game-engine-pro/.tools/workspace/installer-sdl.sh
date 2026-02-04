#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_sdl_windows() {
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL
    cd /workspace/libs/SDL

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DSDL_STATIC=ON \
        -DSDL_SHARED=OFF \
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
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL
    cd /workspace/libs/SDL

    cmake -S . -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DSDL_STATIC=ON \
        -DSDL_SHARED=OFF \
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

#install_sdl_image_windows() {
#    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_image
#    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_image
#    cd /workspace/libs/SDL_image
#
#    cmake -S . -B "$BUILD_DIR" \
#        -DCMAKE_BUILD_TYPE=Release \
#        -DBUILD_SHARED_LIBS=OFF \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3" \
#        -DSDLIMAGE_VENDORED=ON
#
#    cmake --build "$BUILD_DIR" -- -j$(nproc)
#    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
#}
#
#install_sdl_image_linux() {
#    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_image
#    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL_image
#    cd /workspace/libs/SDL_image
#
#    cmake -S . -B "$BUILD_DIR" \
#        -DCMAKE_BUILD_TYPE=Release \
#        -DBUILD_SHARED_LIBS=OFF \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3" \
#        -DSDLIMAGE_VENDORED=ON
#
#    cmake --build "$BUILD_DIR" -- -j$(nproc)
#    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
#}

#install_sdl_ttf_windows() {
#    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_ttf
#    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_ttf
#    cd /workspace/libs/SDL_ttf
#
#    cmake -S . -B "$BUILD_DIR" \
#        -DCMAKE_BUILD_TYPE=Release \
#        -DBUILD_SHARED_LIBS=OFF \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3" \
#        -DSDLIMAGE_VENDORED=ON
#
#    cmake --build "$BUILD_DIR" -- -j$(nproc)
#    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
#}
#
#install_sdl_ttf_linux() {
#    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_ttf
#    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL_ttf
#    cd /workspace/libs/SDL_ttf
#
#    cmake -S . -B "$BUILD_DIR" \
#        -DCMAKE_BUILD_TYPE=Release \
#        -DBUILD_SHARED_LIBS=OFF \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3" \
#        -DSDLIMAGE_VENDORED=ON
#
#    cmake --build "$BUILD_DIR" -- -j$(nproc)
#    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
#}

#install_sdl_mixer_windows() {
#    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/SDL_mixer
#    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/SDL_mixer
#    cd /workspace/libs/SDL_mixer
#
#    cmake -S . -B "$BUILD_DIR" \
#        -DCMAKE_BUILD_TYPE=Release \
#        -DBUILD_SHARED_LIBS=OFF \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3" \
#        -DSDLIMAGE_VENDORED=ON
#
#    cmake --build "$BUILD_DIR" -- -j$(nproc)
#    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
#}
#
#install_sdl_mixer_linux() {
#    BUILD_DIR=/workspace/build/x86_64-linux-gnu/SDL_mixer
#    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/SDL_mixer
#    cd /workspace/libs/SDL_mixer
#
#    cmake -S . -B "$BUILD_DIR" \
#        -DCMAKE_BUILD_TYPE=Release \
#        -DBUILD_SHARED_LIBS=OFF \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3" \
#        -DSDLIMAGE_VENDORED=ON
#
#    cmake --build "$BUILD_DIR" -- -j$(nproc)
#    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
#}
