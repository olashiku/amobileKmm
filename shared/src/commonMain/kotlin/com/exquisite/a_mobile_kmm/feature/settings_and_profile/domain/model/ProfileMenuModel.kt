package com.exquisite.a_mobile_kmm.feature.settings_and_profile.domain.model

import amobilekmm.shared.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import  amobilekmm.shared.generated.resources.*

data class ProfileMenuModel(
    val icon: DrawableResource,
    val title:String,
    val isLogOut:Boolean
) {
}

fun getProfileMenuModel():List<ProfileMenuModel>{
    return listOf(
        ProfileMenuModel(Res.drawable.user_icon,"Edit Profile",false),
        ProfileMenuModel(Res.drawable.my_orders,"My Order",false),
        ProfileMenuModel(Res.drawable.my_wallet,"My Wallet",false),
        ProfileMenuModel(Res.drawable.address_book,"Address Book",false),
        ProfileMenuModel(Res.drawable.contact_us,"Contact Us",false),
        ProfileMenuModel(Res.drawable.change_password,"Password Manager",false),
        ProfileMenuModel(Res.drawable.logout_icon,"Logout",true),
    )
}