#!/usr/bin/env bash

scripts=(
  "./install-sdl-x86_64-w64-mingw32.sh"
  "./install-sdl-x86_64-linux-gnu.sh"
  "./install-sdl-x86_64-linux-android.sh"
  "./install-sdl-aarch64-linux-gnu.sh"
  "./install-sdl-aarch64-linux-android.sh"
)

for script in "${scripts[@]}"; do
  echo "Script: $script"
  chmod +x "$script"
  sudo "$script"
done
