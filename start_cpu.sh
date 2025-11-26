#!/bin/bash
# Start JScience in CPU mode
# Usage: ./start_cpu.sh

if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed."
    exit 1
fi

java Launcher.java CPU
