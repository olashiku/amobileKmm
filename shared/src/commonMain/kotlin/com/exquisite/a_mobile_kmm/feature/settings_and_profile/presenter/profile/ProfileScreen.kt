package com.exquisite.a_mobile_kmm.feature.settings_and_profile.presenter.profile

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.avatar_line
import amobilekmm.shared.generated.resources.next_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.screen_components.AvatarIcon
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.domain.model.ProfileMenuModel
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.domain.model.getProfileMenuModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val profileListing = getProfileMenuModel()
    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
    ) {

        Column(modifier = modifier.padding(horizontal = 19.dp)) {
            Spacer(modifier = modifier.height(25.dp))
            Text(
                text = "Profile",
                color = Color(0xFF252525),
                style = getPoppinsSemiBold18(),
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(25.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxWidth()
            ) {
                AvatarIcon(100.dp, vectorResource(Res.drawable.avatar_line))
            }
            Spacer(modifier = modifier.height(25.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = modifier.fillMaxWidth()
            ) {

                profileListing.forEachIndexed { count, item ->
                    ProfileItem(item)
                }
            }
        }
    }
}

@Composable
fun ProfileItem(model: ProfileMenuModel, modifier: Modifier = Modifier) {
    val textColor = if (model.isLogOut) Color(0xFFFF0000) else Color(0xFF252525)
    Column {
        Row(modifier = modifier.padding(5.dp)) {
            Image(painter = painterResource(model.icon), "")
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = model.title,
                color = textColor,
                style = getPoppinsRegular16(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(Res.drawable.next_icon), "")
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (!model.isLogOut) {
            HorizontalDivider(modifier = modifier.height(0.5.dp), color = Color(0xFFFBFBFB))
        }
    }
}
