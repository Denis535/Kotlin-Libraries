#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

download_git_repository() {
  local DIR="$1"
  local REPO="$2"
  local TAG="${3:-}"

  if [ -d "$DIR" ]; then
    return
  fi

  mkdir -p "$DIR"

  if [ -n "$TAG" ]; then
    git -c advice.detachedHead=false clone \
      --branch "$TAG" \
      --depth 1 \
      "$REPO" "$DIR"
  else
    git clone --depth 1 "$REPO" "$DIR"
  fi
}

download_git_repository "/workspace/src/SDL"       "https://github.com/libsdl-org/SDL.git"       "release-3.4.18"
download_git_repository "/workspace/src/SDL_image" "https://github.com/libsdl-org/SDL_image.git" "release-3.4.8"
download_git_repository "/workspace/src/SDL_ttf"   "https://github.com/libsdl-org/SDL_ttf.git"   "release-3.2.2"
download_git_repository "/workspace/src/SDL_mixer" "https://github.com/libsdl-org/SDL_mixer.git" "release-3.2.4"

download_git_repository "/workspace/src/webp"      "https://github.com/webmproject/libwebp.git"  "v1.6.0"
download_git_repository "/workspace/src/ogg"       "https://github.com/xiph/ogg.git"             "v1.3.6"
download_git_repository "/workspace/src/opus"      "https://github.com/xiph/opus.git"            "v1.6.1"
download_git_repository "/workspace/src/opusfile"  "https://github.com/xiph/opusfile.git"        "v0.12"

#download_git_repository "/workspace/src/wuffs"    "https://github.com/google/wuffs.git"         "v0.3.6"
#download_git_repository "/workspace/src/stb"      "https://github.com/nothings/stb.git"
#download_git_repository "/workspace/src/dr_libs"  "https://github.com/mackron/dr_libs.git"
