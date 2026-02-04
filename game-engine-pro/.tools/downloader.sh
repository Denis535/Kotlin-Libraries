#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

download_sdl() {
  NAME="SDL"
  VERSION="3.4.0"
  FULLNAME="${NAME}-${VERSION}"
  URL="https://github.com/libsdl-org/SDL/archive/refs/tags/release-${VERSION}.tar.gz"

  mkdir -p "workspace/libs"
  rm -rf "workspace/libs/SDL"
  curl -fL "${URL}" -o "workspace/libs/${FULLNAME}.tar.gz"
  tar -xzf "workspace/libs/${FULLNAME}.tar.gz" -C "workspace/libs"
  mv "workspace/libs/${NAME}-release-${VERSION}" "workspace/libs/${NAME}"
  rm "workspace/libs/${FULLNAME}.tar.gz"
}

download_sdl_image() {
  NAME="SDL_image"
  VERSION="3.4.0"
  FULLNAME="${NAME}-${VERSION}"
  URL="https://github.com/libsdl-org/SDL_image/archive/refs/tags/release-${VERSION}.tar.gz"

  mkdir -p "workspace/libs"
  rm -rf "workspace/libs/SDL_image"
  curl -fL "${URL}" -o "workspace/libs/${FULLNAME}.tar.gz"
  tar -xzf "workspace/libs/${FULLNAME}.tar.gz" -C "workspace/libs"
  mv "workspace/libs/${NAME}-release-${VERSION}" "workspace/libs/${NAME}"
  rm "workspace/libs/${FULLNAME}.tar.gz"
}

download_sdl_ttf() {
  NAME="SDL_ttf"
  VERSION="3.2.2"
  FULLNAME="${NAME}-${VERSION}"
  URL="https://github.com/libsdl-org/SDL_ttf/archive/refs/tags/release-${VERSION}.tar.gz"

  mkdir -p "workspace/libs"
  rm -rf "workspace/libs/SDL_ttf"
  curl -fL "${URL}" -o "workspace/libs/${FULLNAME}.tar.gz"
  tar -xzf "workspace/libs/${FULLNAME}.tar.gz" -C "workspace/libs"
  mv "workspace/libs/${NAME}-release-${VERSION}" "workspace/libs/${NAME}"
  rm "workspace/libs/${FULLNAME}.tar.gz"
}

download_sdl_mixer() {
  NAME="SDL_mixer"
  VERSION="2.8.1"
  FULLNAME="${NAME}-${VERSION}"
  URL="https://github.com/libsdl-org/SDL_mixer/archive/refs/tags/release-${VERSION}.tar.gz"

  mkdir -p "workspace/libs"
  rm -rf "workspace/libs/SDL_mixer"
  curl -fL "${URL}" -o "workspace/libs/${FULLNAME}.tar.gz"
  tar -xzf "workspace/libs/${FULLNAME}.tar.gz" -C "workspace/libs"
  mv "workspace/libs/${NAME}-release-${VERSION}" "workspace/libs/${NAME}"
  rm "workspace/libs/${FULLNAME}.tar.gz"
}

download_zlib() {
  URL="https://github.com/madler/zlib/archive/refs/tags/v1.3.1.2.tar.gz"

  mkdir -p "workspace/libs"
  rm -rf "workspace/libs/zlib"
  curl -fL "${URL}" -o "workspace/libs/zlib.tar.gz"
  tar -xzf "workspace/libs/zlib.tar.gz" -C "workspace/libs"
  mv "workspace/libs/zlib-1.3.1.2" "workspace/libs/zlib"
  rm "workspace/libs/zlib.tar.gz"
}

download_freetype() {
  URL="https://github.com/freetype/freetype/archive/refs/tags/VER-2-14-1.tar.gz"

  mkdir -p "workspace/libs"
  rm -rf "workspace/libs/freetype"
  curl -fL "${URL}" -o "workspace/libs/freetype.tar.gz"
  tar -xzf "workspace/libs/freetype.tar.gz" -C "workspace/libs"
  mv "workspace/libs/freetype-VER-2-14-1" "workspace/libs/freetype"
  rm "workspace/libs/freetype.tar.gz"
}

download_bgfx() {
    mkdir -p "workspace/libs"

    rm -rf "workspace/libs/bx"
    git clone --depth 1 https://github.com/bkaradzic/bx.git "workspace/libs/bx"

    rm -rf "workspace/libs/bimg"
    git clone --depth 1 https://github.com/bkaradzic/bimg.git "workspace/libs/bimg"

    rm -rf "workspace/libs/bgfx"
    git clone --depth 1 https://github.com/bkaradzic/bgfx.git "workspace/libs/bgfx"
}

download_miniaudio() {
    REPO="https://github.com/mackron/miniaudio.git"
    TAG="0.11.24"

    mkdir -p "workspace/libs"
    rm -rf "workspace/libs/miniaudio"
    git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "workspace/libs/miniaudio"
}

download_sdl
download_sdl_image
download_sdl_ttf
download_sdl_mixer

download_zlib
download_freetype
download_bgfx
download_miniaudio
