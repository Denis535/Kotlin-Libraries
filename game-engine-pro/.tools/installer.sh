#!/usr/bin/env bash
set -e

docker build -t linux-x64 -f linux-x64.dockerfile .

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "
source /workspace/installer-zlib.sh
source /workspace/installer-freetype.sh
source /workspace/installer-miniaudio.sh
install_zlib_windows
install_freetype_windows
install_miniaudio_windows
"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
linux-x64 bash -euxc "
source /workspace/installer-zlib.sh
source /workspace/installer-freetype.sh
source /workspace/installer-ogg.sh
source /workspace/installer-miniaudio.sh
install_zlib_linux
install_freetype_linux
install_miniaudio_linux
"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "
source /workspace/installer-sdl.sh
install_sdl_windows
"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
linux-x64 bash -euxc "
source /workspace/installer-sdl.sh
install_sdl_linux
"
