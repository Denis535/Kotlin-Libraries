#!/usr/bin/env bash
set -e

docker build -t linux-x64 -f linux-x64.dockerfile .

# zlib
docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "source /workspace/installer-zlib.sh && install_windows"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/linux-x64 bash -euxc "source /workspace/installer-zlib.sh && install_linux"

# png
docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "source /workspace/installer-png.sh && install_windows"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/linux-x64 bash -euxc "source /workspace/installer-png.sh && install_linux"

# ktx
docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "source /workspace/installer-ktx.sh && install_windows"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/linux-x64 bash -euxc "source /workspace/installer-ktx.sh && install_linux"

# ogg
docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "source /workspace/installer-ogg.sh && install_windows"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/linux-x64 bash -euxc "source /workspace/installer-ogg.sh && install_linux"

# vorbis
docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "source /workspace/installer-vorbis.sh && install_windows"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/linux-x64 bash -euxc "source /workspace/installer-vorbis.sh && install_linux"

# freetype
docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "source /workspace/installer-freetype.sh && install_windows"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/linux-x64 bash -euxc "source /workspace/installer-freetype.sh && install_linux"

# cgltf
docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-static-x64 bash -euxc "source /workspace/installer-cgltf.sh && install_windows"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/linux-x64 bash -euxc "source /workspace/installer-cgltf.sh && install_linux"

# sdl
docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-shared-x64 bash -euxc "source /workspace/installer-sdl.sh && install_windows"

docker run \
--rm --mount type=bind,source="$PWD/workspace",target="/workspace" \
linux-x64 bash -euxc "source /workspace/installer-sdl.sh && install_linux"
