package com.exquisite.a_mobile_kmm.core.nav


import amobilekmm.shared.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import amobilekmm.shared.generated.resources.*

sealed class DashboardBottomNav(
    val route: String,
    val label: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource
) {
    object Home :
        DashboardBottomNav("home", "Home", Res.drawable.selected_home, Res.drawable.unselected_home)

    object Booking : DashboardBottomNav(
        "booking",
        "Booking",
        Res.drawable.selected_booking,
        Res.drawable.unselected_booking
    )

    object Cart :
        DashboardBottomNav("cart", "Cart", Res.drawable.selected_cart, Res.drawable.unselected_cart)

    object Training : DashboardBottomNav(
        "academy",
        "Academy",
        Res.drawable.selected_acedemy,
        Res.drawable.unselected_acedemy
    )

    object Profile : DashboardBottomNav(
        "profile",
        "Profile",
        Res.drawable.selected_profile,
        Res.drawable.unselected_profile
    )
}