#!/bin/sh
if [ "$(uname -m)" = "aarch64" ]; then
  exec /bin/dx-test.arm64
else
  exec /bin/dx-test.amd64
fi
