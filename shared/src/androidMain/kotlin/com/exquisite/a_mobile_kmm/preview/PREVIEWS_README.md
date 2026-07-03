# Compose Multiplatform Previews Guide

## Overview
This directory contains Android Studio preview functions for Compose Multiplatform screens.

## Important Rules

### ✅ DO:
1. **Place ALL @Preview functions in `androidMain`** (this directory)
2. **Keep actual @Composable screens in `commonMain`**
3. Use `androidx.compose.ui.tooling.preview.Preview` annotation
4. Create separate preview files named `*Preview.kt`

### ❌ DON'T:
1. **NEVER add @Preview in `commonMain`** - Android Studio cannot render them
2. Don't use `@Display` annotation (doesn't exist)
3. Don't hardcode dependency versions - use version catalog

## File Structure
```
shared/src/
├── commonMain/kotlin/          # Shared composables (NO @Preview here)
│   └── feature/
│       └── MyScreen.kt         # @Composable fun MyScreen()
│
└── androidMain/kotlin/         # Android-specific code + Previews
    └── preview/
        └── MyScreenPreview.kt  # @Preview fun MyScreenPreview()
```

## Example Preview

```kotlin
package com.exquisite.a_mobile_kmm.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exquisite.a_mobile_kmm.feature.MyScreen

@Preview(
    name = "My Screen Light",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MyScreenPreview() {
    MyScreen()
}

// Multiple configurations
@Preview(name = "Small Phone", widthDp = 360, heightDp = 640)
@Preview(name = "Tablet", widthDp = 1024, heightDp = 768)
@Composable
fun MyScreenMultiPreview() {
    MyScreen()
}
```

## Dependencies Configuration

### shared/build.gradle.kts
```kotlin
sourceSets {
    androidMain.dependencies {
        implementation(compose.preview)
        implementation(compose.uiTooling)
    }
    commonMain.dependencies {
        implementation(compose.preview)  // For @Composable annotation
    }
}
```

### androidApp/build.gradle.kts
```kotlin
dependencies {
    debugImplementation(libs.compose.uiTooling)
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.6")
}
```

## Troubleshooting

### Preview not showing?
1. ✅ Verify preview is in `androidMain`, not `commonMain`
2. ✅ Rebuild project: Build → Rebuild Project
3. ✅ Sync Gradle: File → Sync Project with Gradle Files
4. ✅ Invalidate caches: File → Invalidate Caches / Restart

### Resources not loading in preview?
- Use `@PreviewParameter` or mock data instead of resources
- Resources may not be available in preview context

### ViewModel/Koin errors in preview?
- Don't inject ViewModels in preview functions
- Create preview-specific composables without dependencies

## Version Info
- Compose Multiplatform: 1.7.0
- Kotlin: 2.0.21
- Preview annotations: `androidx.compose.ui.tooling.preview.Preview`
