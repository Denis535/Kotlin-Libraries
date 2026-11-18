#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

if ! docker info >/dev/null 2>&1; then
    sudo systemctl start docker
fi

docker build -t linux-x64 -f linux-x64.dockerfile .

docker run \
--user $(id -u):$(id -g) \
--env HOME=/tmp \
--rm \
--mount type=bind,source="$PWD/workspace",target="/workspace" \
linux-x64 \
bash -eux /workspace/installer-sdl-linux.sh
