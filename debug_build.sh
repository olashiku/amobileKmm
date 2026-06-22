#!/bin/bash
# Debug script to see what's happening

echo "=== Environment Check ==="
echo "Current directory: $(pwd)"
echo "Gradle exists: $(ls -la gradlew 2>&1)"
echo ""

echo "=== Testing Gradle Build ==="
cd /Users/oluwayemisioguntayo/AndroidStudioProjects/AMobileKmm
./gradlew :shared:embedAndSignAppleFrameworkForXcode --no-configuration-cache

echo ""
echo "=== Build Complete ==="
echo "Exit code: $?"
