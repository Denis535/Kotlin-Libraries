FROM ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive

ENV ANDROID_PLATFORM=android-28
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV ANDROID_NDK_VERSION=27.3.13750724
ENV ANDROID_NDK_ROOT=${ANDROID_SDK_ROOT}/ndk/${ANDROID_NDK_VERSION}

ENV PATH=${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_NDK_ROOT}:${PATH}

RUN apt update && apt install -y \
    build-essential \
    git \
    cmake \
    ninja-build \
    wget \
    unzip \
    openjdk-17-jdk \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools \
    && wget https://dl.google.com/android/repository/commandlinetools-linux-13114758_latest.zip -O cmdline-tools.zip \
    && unzip cmdline-tools.zip -d ${ANDROID_SDK_ROOT}/cmdline-tools \
    && mv ${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/latest \
    && rm cmdline-tools.zip

RUN yes | sdkmanager --sdk_root=${ANDROID_SDK_ROOT} \
    "platforms;${ANDROID_PLATFORM}" \
    "ndk;${ANDROID_NDK_VERSION}"

WORKDIR /SDL

ENTRYPOINT ["/usr/bin/env", "bash", "-e", "-c", \
    "BUILD_DIR='build/aarch64-linux-android'; \
    INSTALL_DIR='dist/aarch64-linux-android'; \
    cmake \
        -S . \
        -B \"$BUILD_DIR\" \
        -G Ninja \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_INSTALL_PREFIX=\"$INSTALL_DIR\" \
        -DCMAKE_TOOLCHAIN_FILE=${ANDROID_NDK_ROOT}/build/cmake/android.toolchain.cmake \
        -DANDROID_PLATFORM=${ANDROID_PLATFORM} \
        -DANDROID_ABI=arm64-v8a \
        -DANDROID_STL=c++_shared \
        \
        -DSDL_STATIC=ON \
        -DSDL_SHARED=OFF \
        -DSDL_ENABLE_PCH=OFF \
        -DSDL_TEST_LIBRARY=OFF \
        -DSDL_TESTS=OFF \
        -DSDL_INSTALL_TESTS=OFF \
        \
        -DSDL_VIDEO_WINDOWS=OFF \
        -DSDL_VIDEO_X11=OFF \
        -DSDL_VIDEO_WAYLAND=OFF \
        \
        -DSDL_VIDEO=ON \
        -DSDL_AUDIO=ON \
        -DSDL_JOYSTICK=ON \
        -DSDL_HAPTIC=ON \
        -DSDL_SENSOR=ON \
        -DSDL_RENDER_OPENGL=OFF \
        -DSDL_RENDER_VULKAN=ON \
        \
        -DSDL_OPENGLES=ON \
        -DSDL_AUDIO_OPENSL=ON \
        -DSDL_AUDIO_AAUDIO=ON \
        -DSDL_JOYSTICK_ANDROID=ON \
        -DSDL_SENSOR_ANDROID=ON; \
    cmake --build \"$BUILD_DIR\" -- -j$(nproc); \
    cmake --install \"$BUILD_DIR\" \
    "]
