#!/usr/bin/env bash
set -e

SDL_NAME="SDL"
SDL_VERSION="3.4.0"
SDL_FULLNAME="${SDL_NAME}-${SDL_VERSION}"
SDL_URL="https://github.com/libsdl-org/SDL/archive/refs/tags/release-${SDL_VERSION}.tar.gz"

mkdir -p "workspace/libs"
curl -fL "${SDL_URL}" -o "workspace/libs/${SDL_FULLNAME}.tar.gz"
tar -xzf "workspace/libs/${SDL_FULLNAME}.tar.gz" -C "workspace/libs"
mv "workspace/libs/${SDL_NAME}-release-${SDL_VERSION}" "workspace/libs/${SDL_NAME}"
rm "workspace/libs/${SDL_FULLNAME}.tar.gz"
