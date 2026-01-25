sudo docker build \
    -t install-sdl-x86_64-w64-mingw32 \
    -f docker/install-sdl-x86_64-w64-mingw32.dockerfile .

sudo docker run \
    --rm \
    --mount type=bind,source="$PWD/SDL",target=/SDL \
    install-sdl-x86_64-w64-mingw32
