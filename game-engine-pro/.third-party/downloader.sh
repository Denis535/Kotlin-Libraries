#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

download_zlib() {
  DIR="workspace/projects/zlib"
  REPO="https://github.com/madler/zlib.git"
  TAG="v1.3.1.2"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "$DIR"
  git -C "$DIR" -c advice.detachedHead=false checkout "$TAG"
}

download_png() {
  DIR="workspace/projects/png"
  REPO="https://github.com/pnggroup/libpng.git"
  TAG="v1.6.4"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "$DIR"
  git -C "$DIR" -c advice.detachedHead=false checkout "$TAG"
}

download_ktx() {
  DIR="workspace/projects/ktx"
  REPO="https://github.com/KhronosGroup/KTX-Software.git"
  TAG="v4.4.2"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "$DIR"
  git -C "$DIR" -c advice.detachedHead=false checkout "$TAG"
}

download_ogg() {
  DIR="workspace/projects/ogg"
  REPO="https://github.com/xiph/ogg.git"
  TAG="v1.3.6"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "$DIR"
  git -C "$DIR" -c advice.detachedHead=false checkout "$TAG"
}

download_opus() {
  DIR="workspace/projects/opus"
  REPO="https://github.com/xiph/opus.git"
  TAG="v1.6.1"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "$DIR"
  git -C "$DIR" -c advice.detachedHead=false checkout "$TAG"
}

download_freetype() {
  DIR="workspace/projects/freetype"
  REPO="https://github.com/freetype/freetype.git"
  TAG="VER-2-14-1"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "$DIR"
  git -C "$DIR" -c advice.detachedHead=false checkout "$TAG"
}

download_sokol() {
  DIR="workspace/projects/sokol"
  REPO="https://github.com/floooh/sokol.git"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false clone --depth 1 "$REPO" "$DIR"
  git -C "$DIR" -c advice.detachedHead=false checkout master
}

download_miniaudio() {
  DIR="workspace/projects/miniaudio"
  REPO="https://github.com/mackron/miniaudio.git"
  TAG="0.11.24"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false clone --branch "$TAG" --depth 1 "$REPO" "$DIR"
  git -C "$DIR" -c advice.detachedHead=false checkout "$TAG"
}

download_sdl() {
  DIR="workspace/projects/SDL"
  REPO="https://github.com/libsdl-org/SDL.git"
  VERSION="3.4.0"
  TAG="release-${VERSION}"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false \
    clone \
    --branch "$TAG" \
    --depth 1 \
    "$REPO" \
    "$DIR"
}

download_sdl_image() {
  DIR="workspace/projects/SDL_image"
  REPO="https://github.com/libsdl-org/SDL_image.git"
  VERSION="3.4.0"
  TAG="release-${VERSION}"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false \
    clone \
    --branch "$TAG" \
    --depth 1 \
    "$REPO" \
    "$DIR"
}

download_sdl_ttf() {
  DIR="workspace/projects/SDL_ttf"
  REPO="https://github.com/libsdl-org/SDL_ttf.git"
  VERSION="3.2.2"
  TAG="release-${VERSION}"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false \
    clone \
    --branch "$TAG" \
    --depth 1 \
    "$REPO" \
    "$DIR"
}

download_sdl_mixer() {
  DIR="workspace/projects/SDL_mixer"
  REPO="https://github.com/libsdl-org/SDL_mixer.git"
  VERSION="2.8.1"
  TAG="release-${VERSION}"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  git -c advice.detachedHead=false \
    clone \
    --branch "$TAG" \
    --depth 1 \
    "$REPO" \
    "$DIR"
}

download_zlib
download_png
download_ktx
download_ogg
download_opus
download_freetype
download_sokol
download_miniaudio

download_sdl
download_sdl_image
download_sdl_ttf
download_sdl_mixer
