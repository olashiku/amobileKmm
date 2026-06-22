# Fix for "No such module 'Shared'" in Xcode

## The Problem
Xcode IDE caches module information and may not recognize the Shared framework even though it builds successfully from the command line.

## Complete Fix (Do ALL steps in order)

### Step 1: Quit Xcode Completely
- Quit Xcode (⌘+Q)
- Make sure no Xcode processes are running:
  ```bash
  killall Xcode 2>/dev/null || true
  ```

### Step 2: Clean All Build Artifacts
Run these commands from your project root:

```bash
# Navigate to project root
cd /Users/oluwayemisioguntayo/AndroidStudioProjects/AMobileKmm

# Clean Gradle builds
./gradlew clean

# Remove all Xcode caches
rm -rf ~/Library/Developer/Xcode/DerivedData/iosApp-*

# Remove module cache
rm -rf ~/Library/Developer/Xcode/DerivedData/ModuleCache.noindex

# Remove Xcode build folder
rm -rf iosApp/build

# Clean shared module
rm -rf shared/build
```

### Step 3: Reset Xcode Index
```bash
# Remove Xcode index
rm -rf ~/Library/Developer/Xcode/UserData/IDEEditorInteractionHistory*
rm -rf ~/Library/Developer/Xcode/UserData/IDEFindNavigatorScopes*
```

### Step 4: Open and Build in Xcode

1. Open Xcode:
   ```bash
   open iosApp/iosApp.xcodeproj
   ```

2. Wait for indexing to complete (watch the progress bar at the top)

3. Clean Build Folder in Xcode:
   - Press ⌘+Shift+K
   - Or: Product → Clean Build Folder

4. Build the project:
   - Press ⌘+B
   - Or: Product → Build

5. If you still see errors, try:
   - Close the project in Xcode
   - Delete the workspace data:
     ```bash
     rm -rf iosApp/iosApp.xcodeproj/project.xcworkspace/xcuserdata
     ```
   - Reopen and build again

## Verification

The build should succeed with output like:
```
PhaseScriptExecution Compile Kotlin Framework
...
** BUILD SUCCEEDED **
```

The framework will be located at:
```
~/Library/Developer/Xcode/DerivedData/iosApp-*/Build/Products/Debug-iphonesimulator/Shared.framework
```

## If It Still Fails

1. Check the build log in Xcode (View → Navigators → Show Report Navigator)
2. Look for the "Compile Kotlin Framework" phase
3. Check what error it shows

## Running the App

After successful build:
1. Select a simulator (e.g., iPhone 15)
2. Press ⌘+R to run

The app should launch showing your Compose Multiplatform UI.
