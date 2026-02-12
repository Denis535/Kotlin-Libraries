#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

download_repo() {
  local DIR="$1"
  local REPO="$2"
  local TAG="$3"

  rm -rf "$DIR"
  mkdir -p "$DIR"

  if [ -n "$TAG" ]; then
    git -c advice.detachedHead=false \
      clone \
      --branch "$TAG" \
      --depth 1 \
      "$REPO" \
      "$DIR"
  else
    git clone \
      --depth 1 \
      "$REPO" \
      "$DIR"
  fi
}

download_repo "workspace/projects/zlib"      "https://github.com/madler/zlib.git"               "v1.3.1.2"
download_repo "workspace/projects/png"       "https://github.com/pnggroup/libpng.git"           "v1.6.4"
download_repo "workspace/projects/ktx"       "https://github.com/KhronosGroup/KTX-Software.git" "v4.4.2"
download_repo "workspace/projects/freetype"  "https://github.com/freetype/freetype.git"         "VER-2-14-1"
download_repo "workspace/projects/ogg"       "https://github.com/xiph/ogg.git"                  "v1.3.6"
download_repo "workspace/projects/opus"      "https://github.com/xiph/opus.git"                 "v1.6.1"

download_repo "workspace/projects/sokol"     "https://github.com/floooh/sokol.git"
download_repo "workspace/projects/miniaudio" "https://github.com/mackron/miniaudio.git"         "0.11.24"

download_repo "workspace/projects/SDL"       "https://github.com/libsdl-org/SDL.git"            "release-3.4.0"
download_repo "workspace/projects/SDL_image" "https://github.com/libsdl-org/SDL_image.git"      "release-3.4.0"
download_repo "workspace/projects/SDL_ttf"   "https://github.com/libsdl-org/SDL_ttf.git"        "release-3.2.2"
download_repo "workspace/projects/SDL_mixer" "https://github.com/libsdl-org/SDL_mixer.git"      "prerelease-3.1.2"
