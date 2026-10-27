#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

docker build -t linux-x64 -f linux-x64.dockerfile .

docker run \
--user $(id -u):$(id -g) \
--env HOME=/tmp \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-shared-x64 bash -euxc '
source /workspace/installer-sdl.sh

install_sdl_windows
#install_sdl_image_windows
#install_sdl_ttf_windows
#install_sdl_mixer_windows
'

docker run \
--user $(id -u):$(id -g) \
--env HOME=/tmp \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
linux-x64 bash -euxc '
source /workspace/installer-sdl.sh

install_sdl_linux
#install_sdl_image_linux
#install_sdl_ttf_linux
#install_sdl_mixer_linux
'
