#!/bin/bash

# Script to capture iOS simulator logs for debugging

echo "🔍 Capturing iOS logs for AMobile app..."
echo "=================================="
echo ""

# Get the booted simulator ID
SIMULATOR_ID=$(xcrun simctl list devices | grep "Booted" | grep -oE '\([A-F0-9-]+\)' | tr -d '()')

if [ -z "$SIMULATOR_ID" ]; then
    echo "❌ No booted simulator found. Please start the iOS simulator first."
    exit 1
fi

echo "✅ Found booted simulator: $SIMULATOR_ID"
echo ""

# Stream logs in real-time, filtering for our app
echo "📱 Streaming logs (press Ctrl+C to stop)..."
echo "=================================="
echo ""

xcrun simctl spawn booted log stream --predicate 'processImagePath contains "iosApp"' --style compact

