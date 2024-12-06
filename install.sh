#!/bin/bash

LATEST_VERSION=$(curl -s https://api.github.com/repos/currenjin/wharf/releases/latest | grep "tag_name" | cut -d '"' -f 4)

if [[ "$OSTYPE" == "darwin"* ]]; then
    BINARY_NAME="wharf-darwin"
else
    BINARY_NAME="wharf-linux"
fi

DOWNLOAD_URL="https://github.com/currenjin/wharf/releases/download/${LATEST_VERSION}/${BINARY_NAME}"

INSTALL_DIR="/usr/local/bin"

echo "⚙️ Installing wharf..."
echo "📥 Downloading latest version..."

if [[ $EUID -ne 0 ]]; then
    echo "🔒 This script needs sudo privileges to install to $INSTALL_DIR"
    sudo mkdir -p "$INSTALL_DIR"
    sudo curl -L "$DOWNLOAD_URL" -o "$INSTALL_DIR/wharf"
    sudo chmod +x "$INSTALL_DIR/wharf"
else
    mkdir -p "$INSTALL_DIR"
    curl -L "$DOWNLOAD_URL" -o "$INSTALL_DIR/wharf"
    chmod +x "$INSTALL_DIR/wharf"
fi

echo "✅ Wharf has been installed to $INSTALL_DIR/wharf"
echo "🎉 You can now use 'wharf' command from anywhere."