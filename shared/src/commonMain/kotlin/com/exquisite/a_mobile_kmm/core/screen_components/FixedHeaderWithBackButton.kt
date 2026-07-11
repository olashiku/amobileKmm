package com.exquisite.a_mobile_kmm.core.screen_components

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import org.jetbrains.compose.resources.painterResource

@Composable
fun FixedHeaderWithBackButton(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 5.dp, end = 5.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(Res.drawable.back_arrow),
                contentDescription = "Back",
            )
        }
        Text(
            text = title,
            style = getPoppinsSemiBold18(),
            color = Color(0xFF252525),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
