package com.exquisite.a_mobile_kmm.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exquisite.a_mobile_kmm.feature.auth.presenter.splash.SplashScreen

@Preview(
    name = "Splash Screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
