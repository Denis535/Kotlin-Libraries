#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

docker build -t linux-x64 -f linux-x64.dockerfile .

docker run \
--user $(id -u):$(id -g) \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
linux-x64 bash -euxc '
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

download_git_repository "/workspace/projects/zlib"      "https://github.com/madler/zlib.git"               "v1.3.1.2"
download_git_repository "/workspace/projects/png"       "https://github.com/pnggroup/libpng.git"           "v1.6.4"
download_git_repository "/workspace/projects/ktx"       "https://github.com/KhronosGroup/KTX-Software.git" "v4.4.2"
download_git_repository "/workspace/projects/ogg"       "https://github.com/xiph/ogg.git"                  "v1.3.6"
download_git_repository "/workspace/projects/opus"      "https://github.com/xiph/opus.git"                 "v1.6.1"

download_git_repository "/workspace/projects/freetype"  "https://github.com/freetype/freetype.git"         "VER-2-14-1"
download_git_repository "/workspace/projects/assimp"    "https://github.com/assimp/assimp.git"             "v6.0.4"

download_git_repository "/workspace/projects/sokol"     "https://github.com/floooh/sokol.git"
download_git_repository "/workspace/projects/portaudio" "https://github.com/PortAudio/portaudio.git"       "v19.7.0"
download_git_repository "/workspace/projects/miniaudio" "https://github.com/mackron/miniaudio.git"         "0.11.24"

download_git_repository "/workspace/projects/SDL"       "https://github.com/libsdl-org/SDL.git"            "release-3.4.0"
download_git_repository "/workspace/projects/SDL_image" "https://github.com/libsdl-org/SDL_image.git"      "release-3.4.0"
download_git_repository "/workspace/projects/SDL_ttf"   "https://github.com/libsdl-org/SDL_ttf.git"        "release-3.2.2"
download_git_repository "/workspace/projects/SDL_mixer" "https://github.com/libsdl-org/SDL_mixer.git"      "prerelease-3.1.2"
'
