sudo docker build \
    -t install-sdl-x86_64-linux-android \
    -f docker/install-sdl-x86_64-linux-android.dockerfile .

sudo docker run \
    --rm \
    --mount type=bind,source="$PWD/SDL",target=/SDL \
    install-sdl-x86_64-linux-android
