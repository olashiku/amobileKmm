package com.exquisite.a_mobile_kmm.feature.settings_and_profile.presenter.profile

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.avatar_line
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.domain.model.ProfileMenuModel
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.domain.model.getProfileMenuModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userName: String = "Alex Johnson",
    userEmail: String = "alex.johnson@example.com",
    onMenuItemClick: (ProfileMenuModel) -> Unit = {}
) {
    val menuItems = getProfileMenuModel()
    val accountItems = menuItems.filter { it.title in listOf("Edit Profile", "Password Manager") }
    val activityItems = menuItems.filter { it.title in listOf("My Order", "My Wallet", "Address Book", "Contact Us") }
    val logoutItem = menuItems.find { it.isLogOut }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FC))
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 40.dp)
    ) {
        // Header Section with Gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color(0xFFF8F9FC)
                        )
                    )
                )
                .padding(top = 60.dp, start = 24.dp, end = 24.dp, bottom = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar Circle
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE2E8F0))
                        .border(4.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Avatar",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // User Info
                Text(
                    text = userName,
                    style = getPoppinsBold18().copy(fontSize = 22.sp),
                    color = Color(0xFF1A1D23)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = userEmail,
                    style = getPoppinsSemiBold14(),
                    color = Color(0xFF717680)
                )
            }
        }

        // Account Settings Group
        MenuGroupTitle("Account Settings")
        MenuGroup {
            accountItems.forEachIndexed { index, item ->
                MenuItemFromModel(
                    model = item,
                    iconBackgroundColor = if (index == 0) Color(0xFFEEF2FF) else Color(0xFFFFF7ED),
                    iconTintColor = if (index == 0) Color(0xFF4F46E5) else Color(0xFFF97316),
                    onClick = { onMenuItemClick(item) }
                )
                if (index < accountItems.size - 1) {
                    HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // App Activity Group
        MenuGroupTitle("App Activity")
        MenuGroup {
            activityItems.forEachIndexed { index, item ->
                val colors = getMenuItemColors(index)
                MenuItemFromModel(
                    model = item,
                    iconBackgroundColor = colors.first,
                    iconTintColor = colors.second,
                    onClick = { onMenuItemClick(item) }
                )
                if (index < activityItems.size - 1) {
                    HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Logout Button
        logoutItem?.let { logout ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFFF1F2))
                    .clickable { onMenuItemClick(logout) }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "→",
                        style = getPoppinsBold14().copy(fontSize = 18.sp),
                        color = Color(0xFFEF4444)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = logout.title,
                        style = getPoppinsBold14().copy(fontSize = 16.sp),
                        color = Color(0xFFEF4444)
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuGroupTitle(title: String) {
    Text(
        text = title.uppercase(),
        style = getPoppinsBold14().copy(
            fontSize = 12.sp,
            letterSpacing = 0.5.sp
        ),
        color = Color(0xFF717680),
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun MenuGroup(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
private fun MenuItemFromModel(
    model: ProfileMenuModel,
    iconBackgroundColor: Color,
    iconTintColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Box
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(model.icon),
                contentDescription = model.title,
                tint = iconTintColor,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        // Label
        Text(
            text = model.title,
            style = getPoppinsSemiBold14().copy(fontSize = 15.sp),
            color = Color(0xFF1A1D23),
            modifier = Modifier.weight(1f)
        )

        // Chevron
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Navigate",
            tint = Color(0xFFCBD5E1),
            modifier = Modifier.size(20.dp)
        )
    }
}

private fun getMenuItemColors(index: Int): Pair<Color, Color> {
    val colors = listOf(
        Color(0xFFF0FDF4) to Color(0xFF22C55E), // Green
        Color(0xFFFFF7ED) to Color(0xFFF97316), // Orange
        Color(0xFFFAF5FF) to Color(0xFFA855F7), // Purple
        Color(0xFFEEF2FF) to Color(0xFF4F46E5)  // Blue
    )
    return colors[index % colors.size]
}
