#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_cgltf_windows() {
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/cgltf
    cd /workspace/libs/cgltf
    mkdir -p "$INSTALL_DIR/include/"
    cp cgltf.h "$INSTALL_DIR/include/"
}

install_cgltf_linux() {
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/cgltf
    cd /workspace/libs/cgltf
    mkdir -p "$INSTALL_DIR/include/"
    cp cgltf.h "$INSTALL_DIR/include/"
}
