package com.exquisite.dripp.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Snackbar manager for showing different types of messages
 */
class SnackBar(
    private val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope
) {

    /**
     * Shows an error snackbar with red color
     */
    fun showError(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Long,
        onAction: (() -> Unit)? = null
    ) {
        show(
            message = message,
            type = SnackBarType.Error,
            actionLabel = actionLabel,
            duration = duration,
            onAction = onAction
        )
    }

    /**
     * Shows a success snackbar with green color
     */
    fun showSuccess(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onAction: (() -> Unit)? = null
    ) {
        show(
            message = message,
            type = SnackBarType.Success,
            actionLabel = actionLabel,
            duration = duration,
            onAction = onAction
        )
    }

    /**
     * Shows a warning snackbar with orange color
     */
    fun showWarning(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Long,
        onAction: (() -> Unit)? = null
    ) {
        show(
            message = message,
            type = SnackBarType.Warning,
            actionLabel = actionLabel,
            duration = duration,
            onAction = onAction
        )
    }

    /**
     * Shows an info snackbar with blue color
     */
    fun showInfo(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onAction: (() -> Unit)? = null
    ) {
        show(
            message = message,
            type = SnackBarType.Info,
            actionLabel = actionLabel,
            duration = duration,
            onAction = onAction
        )
    }

    /**
     * Generic show function for all snackbar types
     */
    private fun show(
        message: String,
        type: SnackBarType,
        actionLabel: String?,
        duration: SnackbarDuration,
        onAction: (() -> Unit)?
    ) {
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = "${type.prefix}$message",
                actionLabel = actionLabel,
                duration = duration,
                withDismissAction = actionLabel == null
            ).also { result ->
                if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                    onAction?.invoke()
                }
            }
        }
    }

    /**
     * Dismisses the current snackbar
     */
    fun dismiss() {
        snackbarHostState.currentSnackbarData?.dismiss()
    }
}

/**
 * Types of snackbars with their configurations
 */
enum class SnackBarType(
    val prefix: String,
    val icon: ImageVector?,
    val containerColor: Color,
    val contentColor: Color
) {
    Error(
        prefix = "[ERROR]",
        icon = null,  // Can add custom icon if needed
        containerColor = Color(0xFFD32F2F),  // Red
        contentColor = Color.White
    ),
    Success(
        prefix = "[SUCCESS]",
        icon = null,
        containerColor = Color(0xFF388E3C),  // Green
        contentColor = Color.White
    ),
    Warning(
        prefix = "[WARNING]",
        icon = null,  // Can add custom icon if needed
        containerColor = Color(0xFFF57C00),  // Orange
        contentColor = Color.White
    ),
    Info(
        prefix = "[INFO]",
        icon = Icons.Default.Info,
        containerColor = Color(0xFF1976D2),  // Blue
        contentColor = Color.White
    )
}

/**
 * Custom snackbar host with styled snackbars
 * Can be placed anywhere in your layout (doesn't require Scaffold)
 */
@Composable
fun CustomSnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier.padding(bottom = 16.dp),
        snackbar = { snackbarData ->
            CustomSnackbar(snackbarData = snackbarData)
        }
    )
}

/**
 * Custom styled snackbar composable
 */
@Composable
private fun CustomSnackbar(snackbarData: SnackbarData) {
    // Determine snackbar type from message prefix
    val type = when {
        snackbarData.visuals.message.startsWith(SnackBarType.Error.prefix) -> SnackBarType.Error
        snackbarData.visuals.message.startsWith(SnackBarType.Success.prefix) -> SnackBarType.Success
        snackbarData.visuals.message.startsWith(SnackBarType.Warning.prefix) -> SnackBarType.Warning
        snackbarData.visuals.message.startsWith(SnackBarType.Info.prefix) -> SnackBarType.Info
        else -> SnackBarType.Error
    }

    Snackbar(
        modifier = Modifier
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        containerColor = type.containerColor,
        contentColor = type.contentColor,
        dismissAction = if (snackbarData.visuals.actionLabel == null) {
            {
                IconButton(onClick = { snackbarData.dismiss() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = type.contentColor
                    )
                }
            }
        } else null,
        action = if (snackbarData.visuals.actionLabel != null) {
            {
                IconButton(onClick = { snackbarData.performAction() }) {
                    Text(
                        text = snackbarData.visuals.actionLabel ?: "",
                        color = type.contentColor
                    )
                }
            }
        } else null
    ) {
        Text(
            text = snackbarData.visuals.message.removePrefix(type.prefix),
            style = MaterialTheme.typography.bodyMedium,
            color = type.contentColor,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

/**
 * Remember and create a SnackBar instance
 */
@Composable
fun rememberSnackBar(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): Pair<SnackBar, SnackbarHostState> {
    val snackBar = remember(snackbarHostState, coroutineScope) {
        SnackBar(snackbarHostState, coroutineScope)
    }
    return snackBar to snackbarHostState
}