#!/usr/bin/env bash
set -e

SDL_NAME="SDL"
SDL_VERSION="3.4.0"
SDL_FULLNAME="${SDL_NAME}-${SDL_VERSION}"
SDL_URL="https://github.com/libsdl-org/SDL/archive/refs/tags/release-${SDL_VERSION}.tar.gz"

mkdir -p workspace
curl -fL "${SDL_URL}" -o "workspace/${SDL_FULLNAME}.tar.gz"
tar -xzf "workspace/${SDL_FULLNAME}.tar.gz" -C "workspace"
mv "workspace/${SDL_NAME}-release-${SDL_VERSION}" "workspace/${SDL_NAME}"
rm "workspace/${SDL_FULLNAME}.tar.gz"
