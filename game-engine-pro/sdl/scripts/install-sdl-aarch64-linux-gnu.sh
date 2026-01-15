sudo docker run \
    --rm \
    --privileged tonistiigi/binfmt \
    --install all

sudo docker build \
    -t install-sdl-aarch64-linux-gnu \
    -f docker/install-sdl-aarch64-linux-gnu.dockerfile . \
    --platform linux/arm64

sudo docker run \
    --rm \
    --mount type=bind,source="$PWD/SDL",target=/SDL \
    --platform linux/arm64 \
    install-sdl-aarch64-linux-gnu \
