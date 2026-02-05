#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_bgfx_windows() {
    PROJ_DIR=/workspace/libs/bgfx
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/bgfx
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/bgfx
}

install_bgfx_linux() {
    PROJ_DIR=/workspace/libs/bgfx
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/bgfx
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/bgfx
}
