package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.academy
import amobilekmm.shared.generated.resources.cleaning_icon
import amobilekmm.shared.generated.resources.janitorial_service
import amobilekmm.shared.generated.resources.mobile_toilet
import amobilekmm.shared.generated.resources.pest_control
import amobilekmm.shared.generated.resources.septic
import org.jetbrains.compose.resources.DrawableResource

data class DashboardModel(
    val title:String,
    val image: DrawableResource,
    val label:String = ""
)

fun getDashboardModel():List<DashboardModel>{
    return listOf(
        DashboardModel("Cleaning", Res.drawable.cleaning_icon,"cleaning"),
        DashboardModel("Mobile\nToilet", Res.drawable.mobile_toilet,"mobile_toilet"),
        DashboardModel("Pests\nControl", Res.drawable.pest_control,"pest_control"),
        DashboardModel("Academy", Res.drawable.academy,"academy"),
        DashboardModel("Janitorial\nService", Res.drawable.janitorial_service,"janitorial_service"),
        DashboardModel("Septic\nRequest", Res.drawable.septic,"septic"),

        )
}
