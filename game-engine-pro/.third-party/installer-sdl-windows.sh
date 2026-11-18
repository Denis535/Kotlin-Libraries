#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

if ! docker info >/dev/null 2>&1; then
    sudo systemctl start docker
fi

docker run \
--user $(id -u):$(id -g) \
--env HOME=/tmp \
--rm \
--mount type=bind,source="$PWD/workspace",target="/workspace" \
dockcross/windows-shared-x64 \
bash -eux /workspace/installer-sdl-windows.sh
