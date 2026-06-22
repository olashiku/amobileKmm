package com.exquisite.dripp.core.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun PulsatingCircles(text: String) {
    Column {
        val infiniteTransition = rememberInfiniteTransition(label = "PulsatingAnimation")
        
        val size by infiniteTransition.animateFloat(
            initialValue = 200f,
            targetValue = 190f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "SizeAnimation"
        )
        
        val smallCircle by infiniteTransition.animateFloat(
            initialValue = 150f,
            targetValue = 160f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "SmallCircleAnimation"
        )
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            SimpleCircleShape2(
                size = size.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
            )
            SimpleCircleShape2(
                size = smallCircle.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
            )
            SimpleCircleShape2(
                size = 130.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = text,
                        style = TextStyle().copy(color = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}