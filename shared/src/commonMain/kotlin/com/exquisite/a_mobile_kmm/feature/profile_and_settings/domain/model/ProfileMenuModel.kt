package com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model

import amobilekmm.shared.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import  amobilekmm.shared.generated.resources.*


data class ProfileMenuModel(
    val icon: DrawableResource,
    val title:String,
    val label:String,
    val isLogOut:Boolean
) {
}

fun getProfileMenuModel():List<ProfileMenuModel>{
    return listOf(
        ProfileMenuModel(Res.drawable.user_icon,"Edit Profile","edit_profile",false),
        ProfileMenuModel(Res.drawable.my_orders,"My Order","my_order",false),
        ProfileMenuModel(Res.drawable.my_wallet,"My Wallet","my_wallet",false),
        ProfileMenuModel(Res.drawable.address_book,"Address Book","address_book",false),
        ProfileMenuModel(Res.drawable.contact_us,"Contact Us","contact_us",false),
        ProfileMenuModel(Res.drawable.change_password,"Password Manager","password_manager",false),
        ProfileMenuModel(Res.drawable.logout_icon,"Logout","logout",true),
    )
}