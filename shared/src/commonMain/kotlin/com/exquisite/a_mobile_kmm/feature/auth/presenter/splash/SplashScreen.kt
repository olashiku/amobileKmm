package com.exquisite.a_mobile_kmm.feature.auth.presenter.splash

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.a_hygene
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(Res.drawable.a_hygene), contentDescription = "logo")
    }
}

@Preview
@Composable
fun Display() {
    SplashScreen()
}
