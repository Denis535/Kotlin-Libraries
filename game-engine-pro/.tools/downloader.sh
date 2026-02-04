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
  REPO="https://github.com/madler/zlib.git"
  TAG="v1.3.1.2"

  mkdir -p "workspace/libs"
  rm -rf "workspace/libs/zlib"

  git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "workspace/libs/zlib"
  git -C "workspace/libs/zlib" -c advice.detachedHead=false checkout "$TAG"
}

download_freetype() {
    REPO="https://github.com/freetype/freetype.git"
    TAG="VER-2-14-1"

    mkdir -p "workspace/libs"
    rm -rf "workspace/libs/freetype"

    git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "workspace/libs/freetype"
    git -C "workspace/libs/freetype" -c advice.detachedHead=false checkout "$TAG"
}

download_bgfx() {
    mkdir -p "workspace/libs"

    rm -rf "workspace/libs/bx"
    git -c advice.detachedHead=false clone --depth 1 https://github.com/bkaradzic/bx.git "workspace/libs/bx"
    git -C "workspace/libs/bx" -c advice.detachedHead=false checkout master

    rm -rf "workspace/libs/bimg"
    git -c advice.detachedHead=false clone --depth 1 https://github.com/bkaradzic/bimg.git "workspace/libs/bimg"
    git -C "workspace/libs/bimg" -c advice.detachedHead=false checkout master

    rm -rf "workspace/libs/bgfx"
    git -c advice.detachedHead=false clone --depth 1 https://github.com/bkaradzic/bgfx.git "workspace/libs/bgfx"
    git -C "workspace/libs/bgfx" -c advice.detachedHead=false checkout master
}

download_miniaudio() {
    REPO="https://github.com/mackron/miniaudio.git"
    TAG="0.11.24"

    mkdir -p "workspace/libs"
    rm -rf "workspace/libs/miniaudio"

    git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "workspace/libs/miniaudio"
    git -C "workspace/libs/miniaudio" -c advice.detachedHead=false checkout "$TAG"
}

download_sdl
download_sdl_image
download_sdl_ttf
download_sdl_mixer

download_zlib
download_freetype
download_bgfx
download_miniaudio
