#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

download_sdl() {
  NAME="SDL"
  VERSION="3.4.0"
  FULLNAME="${NAME}-${VERSION}"
  URL="https://github.com/libsdl-org/SDL/archive/refs/tags/release-${VERSION}.tar.gz"

  mkdir -p "workspace/libs"
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
  curl -fL "${URL}" -o "workspace/libs/${FULLNAME}.tar.gz"
  tar -xzf "workspace/libs/${FULLNAME}.tar.gz" -C "workspace/libs"
  mv "workspace/libs/${NAME}-release-${VERSION}" "workspace/libs/${NAME}"
  rm "workspace/libs/${FULLNAME}.tar.gz"
}

download_zlib() {
  URL="https://github.com/madler/zlib/archive/refs/tags/v1.3.1.2.tar.gz"

  mkdir -p "workspace/libs"
  curl -fL "${URL}" -o "workspace/libs/zlib.tar.gz"
  tar -xzf "workspace/libs/zlib.tar.gz" -C "workspace/libs"
  mv "workspace/libs/zlib-1.3.1.2" "workspace/libs/zlib"
  rm "workspace/libs/zlib.tar.gz"
}

download_freetype() {
  URL="https://github.com/freetype/freetype/archive/refs/tags/VER-2-14-1.tar.gz"

  mkdir -p "workspace/libs"
  curl -fL "${URL}" -o "workspace/libs/freetype.tar.gz"
  tar -xzf "workspace/libs/freetype.tar.gz" -C "workspace/libs"
  mv "workspace/libs/freetype-VER-2-14-1" "workspace/libs/freetype"
  rm "workspace/libs/freetype.tar.gz"
}

download_ktx() {
  URL="https://github.com/KhronosGroup/KTX-Software/archive/refs/tags/v4.4.2.tar.gz"

  mkdir -p "workspace/libs"
  curl -fL "${URL}" -o "workspace/libs/KTX-Software.tar.gz"
  tar -xzf "workspace/libs/KTX-Software.tar.gz" -C "workspace/libs"
  mv "workspace/libs/KTX-Software-4.4.2" "workspace/libs/KTX-Software"
  rm "workspace/libs/KTX-Software.tar.gz"
}

download_cgltf() {
  URL="https://github.com/jkuhlmann/cgltf/archive/refs/tags/v1.15.tar.gz"

  mkdir -p "workspace/libs"
  curl -fL "${URL}" -o "workspace/libs/cgltf.tar.gz"
  tar -xzf "workspace/libs/cgltf.tar.gz" -C "workspace/libs"
  mv "workspace/libs/cgltf-1.15" "workspace/libs/cgltf"
  rm "workspace/libs/cgltf.tar.gz"
}

download_miniaudio() {
  URL="https://github.com/mackron/miniaudio/archive/refs/tags/0.11.24.tar.gz"

  mkdir -p "workspace/libs"
  curl -fL "${URL}" -o "workspace/libs/miniaudio.tar.gz"
  tar -xzf "workspace/libs/miniaudio.tar.gz" -C "workspace/libs"
  mv "workspace/libs/miniaudio-0.11.24" "workspace/libs/miniaudio"
  rm "workspace/libs/miniaudio.tar.gz"
}

download_sdl
download_sdl_image
download_sdl_ttf
download_sdl_mixer

download_zlib
download_freetype
download_ktx
download_cgltf
download_miniaudio
