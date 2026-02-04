FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV KONAN_HOME=/opt/kotlin-native-prebuilt-linux-x86_64-2.3.0
ENV PATH="$KONAN_HOME/bin:$PATH"

# https://wiki.libsdl.org/SDL3/README-linux#build-dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
build-essential git make \
pkg-config cmake ninja-build gnome-desktop-testing libasound2-dev libpulse-dev \
libaudio-dev libfribidi-dev libjack-dev libsndio-dev libx11-dev libxext-dev \
libxrandr-dev libxcursor-dev libxfixes-dev libxi-dev libxss-dev libxtst-dev \
libxkbcommon-dev libdrm-dev libgbm-dev libgl1-mesa-dev libgles2-mesa-dev \
libegl1-mesa-dev libdbus-1-dev libibus-1.0-dev libudev-dev libthai-dev \
libpipewire-0.3-dev libwayland-dev libdecor-0-dev liburing-dev \
curl ca-certificates \
nasm \
&& rm -rf /var/lib/apt/lists/*

RUN curl -L https://github.com/JetBrains/kotlin/releases/download/v2.3.0/kotlin-native-prebuilt-linux-x86_64-2.3.0.tar.gz \
| tar -xz -C /opt
