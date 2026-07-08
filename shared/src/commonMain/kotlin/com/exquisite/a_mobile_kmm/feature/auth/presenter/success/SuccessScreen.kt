package com.exquisite.a_mobile_kmm.feature.auth.presenter.success

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.success_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun SuccessScreen(title:String,message:String,modifier: Modifier = Modifier,popToLogin:() ->Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(start =20.dp,end =20.dp).align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.success_icon),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = modifier.height(8.dp))

            Text(
                text = title, style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF1A1A1A), textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(16.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = modifier.align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            PrimaryButton("Get Started", {
                popToLogin.invoke()
            })
        }
    }
}