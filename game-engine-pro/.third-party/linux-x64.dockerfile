FROM ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive

# https://wiki.libsdl.org/SDL3/README-linux#build-dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        libdrm-dev libgbm-dev \
        libegl1-mesa-dev libgl1-mesa-dev libgles2-mesa-dev \
        libasound2-dev libaudio-dev libjack-dev \
        libpipewire-0.3-dev libpulse-dev libsndio-dev \
        libx11-dev libxext-dev \
        libxcursor-dev libxfixes-dev libxi-dev \
        libxrandr-dev libxss-dev libxtst-dev \
        libxkbcommon-dev \
        libwayland-dev libdecor-0-dev \
        libdbus-1-dev libibus-1.0-dev libudev-dev \
        libusb-1.0-0-dev gnome-desktop-testing \
        libfribidi-dev libthai-dev \
        build-essential git make autoconf automake libtool \
        cmake meson ninja-build pkg-config nasm \
        ca-certificates curl zip unzip && \
    rm -rf /var/lib/apt/lists/* \
