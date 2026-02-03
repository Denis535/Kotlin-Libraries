#!/usr/bin/env bash
set -e

docker build -t linux-x64 -f linux-x64.dockerfile .

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "
source /workspace/installer-zlib.sh
source /workspace/installer-png.sh
source /workspace/installer-ktx.sh
source /workspace/installer-freetype.sh
source /workspace/installer-ogg.sh
source /workspace/installer-vorbis.sh
source /workspace/installer-cgltf.sh
source /workspace/installer-miniaudio.sh
install_zlib_windows
install_ktx_windows
install_freetype_windows
install_cgltf_windows
install_miniaudio_windows
"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/linux-x64 bash -euxc "
source /workspace/installer-zlib.sh
source /workspace/installer-png.sh
source /workspace/installer-ktx.sh
source /workspace/installer-freetype.sh
source /workspace/installer-ogg.sh
source /workspace/installer-vorbis.sh
source /workspace/installer-cgltf.sh
source /workspace/installer-miniaudio.sh
install_zlib_linux
install_ktx_linux
install_freetype_linux
install_cgltf_linux
install_miniaudio_linux
"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-shared-x64 bash -euxc "
source /workspace/installer-sdl.sh
install_sdl_windows
#install_sdl_image_windows
#install_sdl_ttf_windows
"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
linux-x64 bash -euxc "
source /workspace/installer-sdl.sh
install_sdl_linux
#install_sdl_image_linux
#install_sdl_ttf_linux
"
