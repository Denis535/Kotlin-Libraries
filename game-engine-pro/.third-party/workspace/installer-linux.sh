#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

install_webp_linux() {
    SRC_DIR=/workspace/src/webp
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/webp
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/webp

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DWEBP_ENABLE_SIMD=ON \
        -DWEBP_BUILD_LIBWEBPMUX=OFF \
        -DWEBP_BUILD_ANIM_UTILS=OFF \
        -DWEBP_BUILD_GIF2WEBP=OFF \
        -DWEBP_BUILD_IMG2WEBP=OFF \
        -DWEBP_BUILD_CWEBP=OFF \
        -DWEBP_BUILD_DWEBP=OFF \
        -DWEBP_BUILD_VWEBP=OFF \
        -DWEBP_BUILD_WEBPINFO=OFF \
        -DWEBP_BUILD_WEBPMUX=OFF \
        -DWEBP_BUILD_EXTRAS=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_nestegg_linux() {
    SRC_DIR=/workspace/src/nestegg
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/nestegg
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/nestegg

    cd "$SRC_DIR" || return 1
    ./autogen.sh || return 1
    mkdir -p "$BUILD_DIR" "$DIST_DIR"
    cd "$BUILD_DIR" || return 1

    "$SRC_DIR/configure" \
        --prefix="$DIST_DIR" \
        --enable-static \
        --disable-shared || return 1

    make --jobs="$(nproc)" || return 1
    make install
}

install_ogg_linux() {
    SRC_DIR=/workspace/src/ogg
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/ogg
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/ogg

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DBUILD_TESTING=OFF \
        -DINSTALL_DOCS=OFF

    cmake --build "$BUILD_DIR" --parallel "$(nproc)"
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_opus_linux() {
    SRC_DIR=/workspace/src/opus
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/opus
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/opus

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DOPUS_BUILD_SHARED_LIBRARY=OFF \
        -DOPUS_BUILD_PROGRAMS=OFF \
        -DOPUS_BUILD_TESTING=OFF

    cmake --build "$BUILD_DIR" -- -j$(nproc)
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_opusfile_linux() {
    SRC_DIR=/workspace/src/opusfile
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/opusfile
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/opusfile
    OGG_DIR=/workspace/dist/x86_64-linux-gnu/ogg
    OPUS_DIR=/workspace/dist/x86_64-linux-gnu/opus

    cd "$SRC_DIR" || return 1
    ./autogen.sh || return 1
    mkdir -p "$BUILD_DIR" "$DIST_DIR"
    cd "$BUILD_DIR" || return 1

    DEPS_CFLAGS="-I$OGG_DIR/include -I$OPUS_DIR/include/opus" \
    DEPS_LIBS="-L$OGG_DIR/lib -L$OPUS_DIR/lib -lopus -logg" \
    "$SRC_DIR/configure" \
        --prefix="$DIST_DIR" \
        --enable-static \
        --disable-shared \
        --disable-http \
        --disable-examples \
        --disable-doc || return 1

    make --jobs="$(nproc)" || return 1
    make install
}

install_harfbuzz_linux() {
    SRC_DIR=/workspace/src/harfbuzz
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/harfbuzz
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/harfbuzz

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DHB_HAVE_FREETYPE=OFF \
        -DHB_HAVE_GRAPHITE2=OFF \
        -DHB_HAVE_GLIB=OFF \
        -DHB_HAVE_ICU=OFF \
        -DHB_HAVE_CAIRO=OFF \
        -DHB_BUILD_UTILS=OFF \
        -DHB_BUILD_SUBSET=OFF \
        -DHB_BUILD_RASTER=OFF \
        -DHB_BUILD_VECTOR=OFF \
        -DHB_BUILD_GPU=OFF

    cmake --build "$BUILD_DIR" --parallel "$(nproc)"
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_freetype_linux() {
    SRC_DIR=/workspace/src/freetype
    BUILD_DIR=/workspace/build/x86_64-linux-gnu/freetype
    DIST_DIR=/workspace/dist/x86_64-linux-gnu/freetype
    HB_DIST_DIR=/workspace/dist/x86_64-linux-gnu/harfbuzz

    cmake -S "$SRC_DIR" -B "$BUILD_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
        -DBUILD_SHARED_LIBS=OFF \
        -DHarfBuzz_INCLUDE_DIR="$HB_DIST_DIR/include/harfbuzz" \
        -DHarfBuzz_LIBRARY="$HB_DIST_DIR/lib/libharfbuzz.a" \
        -DFT_DISABLE_HARFBUZZ=OFF \
        -DFT_REQUIRE_HARFBUZZ=ON \
        -DFT_DYNAMIC_HARFBUZZ=OFF \
        -DFT_DISABLE_BZIP2=ON \
        -DFT_DISABLE_PNG=ON \
        -DFT_DISABLE_BROTLI=ON \
        -DFT_DISABLE_HVF=ON

    cmake --build "$BUILD_DIR" --parallel "$(nproc)"
    cmake --install "$BUILD_DIR" --prefix "$DIST_DIR"
}

install_webp_linux
install_ogg_linux
install_opus_linux
install_opusfile_linux
install_harfbuzz_linux
install_freetype_linux
