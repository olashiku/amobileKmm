package com.exquisite.a_mobile_kmm.feature.auth.presenter.onboard

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

data class OnboardingModel(
    val image: DrawableResource,
    val title: String,
    val description: String
)

fun getOnboardingData(): List<OnboardingModel> {
    return listOf(
        OnboardingModel(
            Res.drawable.slider1,
            "Book your cleaning services",
            "Get expert cleaning for homes and businesses. Fast, Reliable and Spotless."
        ),
        OnboardingModel(
            Res.drawable.slider2,
            "Request for your mobile Toilet",
            "Get Clean, convenient toilets delivered to your events or sites when and where you need them."
        )
        ,
        OnboardingModel(
            Res.drawable.slider3,
            "Shop for your cleaning products",
            "Shop quality cleaning product for healthier, sparkling space everyday."
        )

    )
}