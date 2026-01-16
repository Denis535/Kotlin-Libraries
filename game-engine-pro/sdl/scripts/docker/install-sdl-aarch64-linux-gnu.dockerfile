FROM arm64v8/ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive

# https://wiki.libsdl.org/SDL3/README-linux#build-dependencies
RUN apt update && apt install -y \
    build-essential git make \
    pkg-config cmake ninja-build gnome-desktop-testing libasound2-dev libpulse-dev \
    libaudio-dev libfribidi-dev libjack-dev libsndio-dev libx11-dev libxext-dev \
    libxrandr-dev libxcursor-dev libxfixes-dev libxi-dev libxss-dev libxtst-dev \
    libxkbcommon-dev libdrm-dev libgbm-dev libgl1-mesa-dev libgles2-mesa-dev \
    libegl1-mesa-dev libdbus-1-dev libibus-1.0-dev libudev-dev libthai-dev \
    libpipewire-0.3-dev libwayland-dev libdecor-0-dev liburing-dev \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /SDL

ENTRYPOINT ["/usr/bin/env", "bash", "-e", "-c", \
    "BUILD_DIR='build/aarch64-linux-gnu'; \
    INSTALL_DIR='dist/aarch64-linux-gnu'; \
    cmake \
        -S . \
        -B \"$BUILD_DIR\" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_C_STANDARD=11 \
        -DCMAKE_C_EXTENSIONS=ON \
        \
        -DSDL_STATIC=ON \
        -DSDL_SHARED=OFF \
        -DSDL_ENABLE_PCH=OFF \
        -DSDL_TEST_LIBRARY=OFF \
        -DSDL_TESTS=OFF \
        -DSDL_INSTALL_TESTS=OFF \
        \
        -DSDL_VIDEO=ON \
        -DSDL_VIDEO_WINDOWS=OFF \
        -DSDL_VIDEO_X11=ON \
        -DSDL_VIDEO_WAYLAND=OFF \
        -DSDL_OPENGLES=OFF \
        -DSDL_RENDER_OPENGL=ON \
        -DSDL_RENDER_VULKAN=ON \
        \
        -DSDL_AUDIO=ON \
        -DSDL_AUDIO_JACK=ON \
        \
        -DSDL_JOYSTICK=ON \
        -DSDL_SENSOR=ON \
        -DSDL_HAPTIC=ON; \
    cmake --build \"$BUILD_DIR\" -- -j$(nproc); \
    cmake --install \"$BUILD_DIR\" --prefix \"$INSTALL_DIR\"; \
    "]
