package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.toilet_1
import amobilekmm.shared.generated.resources.toilet_2
import amobilekmm.shared.generated.resources.toilet_3
import amobilekmm.shared.generated.resources.toilet_4
import org.jetbrains.compose.resources.DrawableResource

data class ToiletOnboardingData (
    val image: DrawableResource,
    val title:String,
    val description:String
)


fun getToiletOnboardingData():List<ToiletOnboardingData>{
  return  listOf(
        ToiletOnboardingData(Res.drawable.toilet_1,"Clean, Modern\n Mobile Toilets","Rent premium mobile toilets for your parties\n and events. Hygienic, fast and reliable."),
        ToiletOnboardingData(Res.drawable.toilet_2,"Toilet types for\n every occasions","Select the best toilet for your event with\n flexible options."),
        ToiletOnboardingData(Res.drawable.toilet_3,"Reliable delivery\n and set up","We deliver and set up on time, every time."),
        ToiletOnboardingData(Res.drawable.toilet_4,"Keep your guests\n comfortable","Clean and well maintained units your\n guests will appreciate"),
        )
}