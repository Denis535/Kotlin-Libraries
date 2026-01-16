FROM ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt update && apt install -y \
    build-essential cmake ninja-build pkg-config \
    mingw-w64 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /SDL

ENTRYPOINT ["/usr/bin/env", "bash", "-e", "-c", \
    "BUILD_DIR='build/x86_64-w64-mingw32'; \
    INSTALL_DIR='dist/x86_64-w64-mingw32'; \
    export CC=x86_64-w64-mingw32-gcc; \
    export CXX=x86_64-w64-mingw32-g++; \
    export RC=x86_64-w64-mingw32-windres; \
    cmake \
        -S . \
        -B \"$BUILD_DIR\" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_C_STANDARD=11 \
        -DCMAKE_C_EXTENSIONS=ON \
        -DCMAKE_SYSTEM_NAME=Windows \
        -DCMAKE_FIND_ROOT_PATH=/usr/x86_64-w64-mingw32 \
        -DCMAKE_FIND_ROOT_PATH_MODE_LIBRARY=ONLY \
        -DCMAKE_FIND_ROOT_PATH_MODE_INCLUDE=ONLY \
        -DCMAKE_FIND_ROOT_PATH_MODE_PROGRAM=BOTH \
        \
        -DSDL_STATIC=ON \
        -DSDL_SHARED=OFF \
        -DSDL_ENABLE_PCH=OFF \
        -DSDL_TEST_LIBRARY=OFF \
        -DSDL_TESTS=OFF \
        -DSDL_INSTALL_TESTS=OFF \
        \
        -DSDL_VIDEO=ON \
        -DSDL_VIDEO_WINDOWS=ON \
        -DSDL_VIDEO_X11=OFF \
        -DSDL_VIDEO_WAYLAND=OFF \
        -DSDL_OPENGLES=OFF \
        -DSDL_RENDER_OPENGL=ON \
        -DSDL_RENDER_VULKAN=ON \
        \
        -DSDL_AUDIO=ON \
        -DSDL_AUDIO_WASAPI=ON \
        \
        -DSDL_JOYSTICK=ON \
        -DSDL_SENSOR=ON \
        -DSDL_HAPTIC=ON; \
    cmake --build \"$BUILD_DIR\" -- -j$(nproc); \
    cmake --install \"$BUILD_DIR\" --prefix \"$INSTALL_DIR\" \
    "]
