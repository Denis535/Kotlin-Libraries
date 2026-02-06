#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_sokol_windows() {
    PROJ_DIR=/workspace/libs/sokol
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/sokol
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/sokol
}

install_sokol_linux() {
    PROJ_DIR=/workspace/libs/sokol
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/sokol
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/sokol
}
