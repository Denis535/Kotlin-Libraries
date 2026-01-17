sudo docker build \
    -t install-sdl-x86_64-linux-gnu \
    -f docker/install-sdl-x86_64-linux-gnu.dockerfile .

sudo docker run \
    --rm \
    --mount type=bind,source="$PWD/SDL",target=/SDL \
    install-sdl-x86_64-linux-gnu
