#!/usr/bin/env bash
set -e
export DEBIAN_FRONTEND=noninteractive

docker build -t linux-x64 -f linux-x64.dockerfile .

docker run \
--user $(id -u):$(id -g) \
--rm \
--mount type=bind,source="$PWD/workspace",target="/workspace" \
linux-x64 \
bash -eux /workspace/downloader.sh
