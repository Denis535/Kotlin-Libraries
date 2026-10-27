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

download_git_repository "/workspace/projects/SDL"       "https://github.com/libsdl-org/SDL.git"       "release-3.4.18"
download_git_repository "/workspace/projects/SDL_image" "https://github.com/libsdl-org/SDL_image.git" "release-3.4.8"
download_git_repository "/workspace/projects/SDL_ttf"   "https://github.com/libsdl-org/SDL_ttf.git"   "release-3.2.2"
download_git_repository "/workspace/projects/SDL_mixer" "https://github.com/libsdl-org/SDL_mixer.git" "release-3.2.4"
