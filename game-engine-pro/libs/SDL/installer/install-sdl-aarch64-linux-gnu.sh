sudo docker run \
    --rm \
    --privileged tonistiigi/binfmt \
    --install all

sudo docker build \
    --platform linux/arm64 \
    -t install-sdl-aarch64-linux-gnu \
    -f docker/install-sdl-aarch64-linux-gnu.dockerfile .

sudo docker run \
    --rm \
    --platform linux/arm64 \
    --mount type=bind,source="$PWD/SDL",target=/SDL \
    install-sdl-aarch64-linux-gnu
