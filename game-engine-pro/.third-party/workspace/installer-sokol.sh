#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_sokol_windows() {
    PROJ_DIR=/workspace/libs/sokol
    BUILD_DIR=/workspace/build/x86_64-w64-mingw32/sokol
    INSTALL_DIR=/workspace/dist/x86_64-w64-mingw32/sokol

    rm -rf "$INSTALL_DIR"
    mkdir -p "$INSTALL_DIR/include"
    mkdir -p "$INSTALL_DIR/share"
    cd "$PROJ_DIR" || exit 1

    find . -maxdepth 1 -type f -name '*.h' \
        -exec install -Dm644 {} "$INSTALL_DIR/include/{}" \;
    install -Dm644 "$PROJ_DIR/LICENSE" "$INSTALL_DIR/share/LICENSE"
}

install_sokol_linux() {
    PROJ_DIR=/workspace/libs/sokol
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/sokol
    INSTALL_DIR=/workspace/dist/x86_64-linux-gnu/sokol

    rm -rf "$INSTALL_DIR"
    mkdir -p "$INSTALL_DIR/include"
    mkdir -p "$INSTALL_DIR/share"
    cd "$PROJ_DIR" || exit 1

    find . -maxdepth 1 -type f -name '*.h' \
        -exec install -Dm644 {} "$INSTALL_DIR/include/{}" \;
    install -Dm644 "$PROJ_DIR/LICENSE" "$INSTALL_DIR/share/LICENSE"
}
