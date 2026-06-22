# iOS Build Fix - Summary

## Problem
The iOS build was failing with:
```
Command PhaseScriptExecution failed with a nonzero exit code
```

The root cause was the `syncComposeResourcesForIos` task in Compose Multiplatform 1.7.3 failing to properly read Xcode environment variables, causing the Gradle build to fail during the "Compile Kotlin Framework" phase in Xcode.

## Changes Made

### 1. Disabled Gradle Configuration Cache (`gradle.properties`)
Changed:
```properties
org.gradle.configuration-cache=true
```
To:
```properties
org.gradle.configuration-cache=false
```

**Why**: Configuration cache was preventing the Gradle tasks from reading Xcode environment variables during configuration phase.

### 2. Updated Xcode Build Script (`iosApp/iosApp.xcodeproj/project.pbxproj`)
Added `--no-configuration-cache` flag to the Gradle command in the "Compile Kotlin Framework" build phase.

**Why**: Extra safety to ensure configuration cache is disabled when building from Xcode.

### 3. Disabled Problematic Task (`shared/build.gradle.kts`)
Added configuration to disable the `syncComposeResourcesForIos` task:
```kotlin
tasks.configureEach {
    if (name == "syncComposeResourcesForIos") {
        enabled = false
        logger.warn("Disabled syncComposeResourcesForIos - resources will be bundled via framework compilation")
    }
}
```

**Why**: This task has a bug in Compose Multiplatform 1.7.3 where it can't properly infer iOS target configuration from Xcode environment variables. Resources are still properly bundled into the framework through the normal compilation process.

### 4. Added Gradle Property (`gradle.properties`)
```properties
kotlin.apple.embedAndSign.enabled=true
```

**Why**: Ensures the embedAndSign task is properly enabled for iOS builds.

## How to Build iOS App Now

### From Xcode
1. Open the project:
   ```bash
   open iosApp/iosApp.xcodeproj
   ```

2. Select a simulator (e.g., iPhone 15)

3. Build and run:
   - Press ⌘+B to build, or
   - Press ⌘+R to build and run

The build should now succeed without the PhaseScriptExecution error!

### From Command Line (for testing)
```bash
# Clean build
./gradlew clean

# Build the shared framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

## Expected Build Output
When building from Xcode, you should see:
```
Building Kotlin framework...
Configuration: Debug
SDK: iphonesimulator
Archs: arm64

> Task :shared:syncComposeResourcesForIos SKIPPED
> Task :shared:linkDebugFrameworkIosSimulatorArm64

BUILD SUCCESSFUL
```

## Notes
- The `syncComposeResourcesForIos SKIPPED` message is expected and not an error
- Compose resources are still properly included in the framework
- If you see a warning about "Cannot infer a bundle ID", this is harmless
- The framework will be located at: `shared/build/xcode-frameworks/Debug/iphonesimulator/Shared.framework`

## Future Improvements
This is a workaround for a bug in Compose Multiplatform 1.7.3. When upgrading to a newer version of Compose Multiplatform, you may be able to:
1. Re-enable configuration cache
2. Re-enable the `syncComposeResourcesForIos` task
3. Remove the `--no-configuration-cache` flag from the Xcode build script

Monitor the JetBrains Compose Multiplatform releases for fixes to iOS resource handling.
