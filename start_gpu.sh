#!/bin/bash
# Start JScience in GPU mode
# Usage: ./start_gpu.sh
# Note: Requires CUDA drivers installed.

if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed."
    exit 1
fi

java Launcher.java GPU
