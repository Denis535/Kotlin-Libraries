sudo docker build \
    -t install-sdl-aarch64-linux-android \
    -f docker/install-sdl-aarch64-linux-android.dockerfile .

sudo docker run --rm \
    --mount type=bind,source="$PWD/SDL",target=/SDL \
    install-sdl-aarch64-linux-android
