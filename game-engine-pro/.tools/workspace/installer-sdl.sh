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
        -DSDL_X11_SHARED=OFF \
        -DSDL_WAYLAND=OFF \
        -DSDL_OPENGL=ON \
        -DSDL_VULKAN=ON \
        -DSDL_POSIX_SPAWN=OFF # отключаем BSD-функции

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
#        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3" \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL_TESTS=OFF \
#        -DSDL_TEST_LIBRARY=OFF \
#        -DSDL_INSTALL_TESTS=OFF \
#        -DSDLIMAGE_VENDORED=ON \
#        -DSDLIMAGE_TESTS=OFF \
#        -DSDLIMAGE_SAMPLES=OFF
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
#        -DCMAKE_C_FLAGS="-U__GLIBC_USE_ISOC23 -D_GNU_SOURCE" \
#        -DBUILD_SHARED_LIBS=OFF \
#        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3" \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL_TESTS=OFF \
#        -DSDL_TEST_LIBRARY=OFF \
#        -DSDL_INSTALL_TESTS=OFF \
#        -DSDLIMAGE_VENDORED=ON \
#        -DSDLIMAGE_TESTS=OFF \
#        -DSDLIMAGE_SAMPLES=OFF
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
#        -DSDL_TESTS=OFF \
#        -DSDL_TEST_LIBRARY=OFF \
#        -DSDL_INSTALL_TESTS=OFF \
#        -DSDL3_DIR="/workspace/dist/x86_64-w64-mingw32/SDL/lib/cmake/SDL3" \
#        -DSDLTTF_VENDORED=OFF \
#        -DFREETYPE_INCLUDE_DIRS="/workspace/dist/x86_64-w64-mingw32/freetype/include" \
#        -DFREETYPE_LIBRARIES="/workspace/dist/x86_64-w64-mingw32/freetype/lib/libfreetype.a"
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
#        -DCMAKE_C_FLAGS="-U__GLIBC_USE_ISOC23 -D_GNU_SOURCE" \
#        -DBUILD_SHARED_LIBS=OFF \
#        -DSDL_STATIC=ON \
#        -DSDL_SHARED=OFF \
#        -DSDL_ENABLE_PCH=OFF \
#        -DSDL_TESTS=OFF \
#        -DSDL_TEST_LIBRARY=OFF \
#        -DSDL_INSTALL_TESTS=OFF
#        -DSDL3_DIR="/workspace/dist/x86_64-linux-gnu/SDL/lib/cmake/SDL3" \
#        -DSDLTTF_VENDORED=OFF \
#        -DFREETYPE_INCLUDE_DIRS="/workspace/dist/x86_64-linux-gnu/freetype/include"
#        -DFREETYPE_LIBRARIES="/workspace/dist/x86_64-linux-gnu/freetype/lib/libfreetype.a"
#
#    cmake --build "$BUILD_DIR" -- -j$(nproc)
#    cmake --install "$BUILD_DIR" --prefix "$INSTALL_DIR"
#}
