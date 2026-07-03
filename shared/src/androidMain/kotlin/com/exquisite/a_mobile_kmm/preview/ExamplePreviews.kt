package com.exquisite.a_mobile_kmm.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Example preview configurations for Compose Multiplatform
 * All previews MUST be in androidMain, not commonMain
 */

// Basic preview with background
@Preview(
    name = "Basic Preview",
    showBackground = true
)
@Composable
fun BasicPreview() {
    MaterialTheme {
        Text("Basic Preview Example")
    }
}

// Preview with system UI (status bar, navigation bar)
@Preview(
    name = "With System UI",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SystemUIPreview() {
    MaterialTheme {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Preview with System UI")
            }
        }
    }
}

// Multiple device configurations
@Preview(
    name = "Phone Portrait",
    widthDp = 360,
    heightDp = 640,
    showBackground = true
)
@Preview(
    name = "Phone Landscape",
    widthDp = 640,
    heightDp = 360,
    showBackground = true
)
@Preview(
    name = "Tablet",
    widthDp = 1024,
    heightDp = 768,
    showBackground = true
)
@Composable
fun MultiDevicePreview() {
    MaterialTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Multi-Device Preview")
            }
        }
    }
}

// Preview with custom dimensions
@Preview(
    name = "Custom Size",
    widthDp = 320,
    heightDp = 480,
    showBackground = true
)
@Composable
fun CustomSizePreview() {
    MaterialTheme {
        Text("Custom Size: 320x480dp")
    }
}
