#!/bin/bash

LATEST_VERSION=$(curl -s https://api.github.com/repos/currenjin/wharf/releases/latest | grep "tag_name" | cut -d '"' -f 4)

if [[ "$OSTYPE" == "darwin"* ]]; then
    BINARY_NAME="wharf-darwin"
else
    BINARY_NAME="wharf-linux"
fi

DOWNLOAD_URL="https://github.com/currenjin/wharf/releases/download/${LATEST_VERSION}/${BINARY_NAME}"

INSTALL_DIR="/usr/local/bin"
mkdir -p "$INSTALL_DIR"

echo "♻️ Downloading wharf..."
curl -L "$DOWNLOAD_URL" -o "$INSTALL_DIR/wharf"
chmod +x "$INSTALL_DIR/wharf"

echo "✅ Wharf has been installed to $INSTALL_DIR/wharf"
echo "🎉 You can now use 'wharf' command from anywhere."