package com.exquisite.a_mobile_kmm.feature.employee.presenter.profile

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.logout_icon
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold20
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.ProfileMenuModel
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.getProfileMenuModel
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.profile.ProfileViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeProfileScreen(onMenuItemClick: (String) -> Unit = {},
                             modifier: Modifier = Modifier,
                             viewModel: ProfileViewModel = koinViewModel<ProfileViewModel>()) {
    val menuItems = getProfileMenuModel()
    val accountItems = menuItems.filter { it.title in listOf("Edit Profile", "Password Manager") }
    val logoutItem = menuItems.find { it.isLogOut }
    var showLogoutBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    val customerName = viewModel.customerName.collectAsStateWithLifecycle().value
    val image = viewModel.profilePicture.collectAsStateWithLifecycle().value
    val customerEmail = viewModel.customerEmail.collectAsStateWithLifecycle().value


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

                    if (image.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Avatar",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(40.dp)
                        )
                    } else {
                        AsyncImage(
                            model = image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // User Info
                Text(
                    text = customerName,
                    style = getPoppinsBold18().copy(fontSize = 22.sp),
                    color = Color(0xFF1A1D23)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = customerEmail,
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
                    onClick = { onMenuItemClick(item.label) }
                )
                if (index < accountItems.size - 1) {
                    HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Logout Button
        logoutItem?.let { logout ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFFF1F2))
                    .clickable {
                        showLogoutBottomSheet = true
                    }
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

    // Logout confirmation bottom sheet
    if (showLogoutBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showLogoutBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Image(
                    painter = painterResource(Res.drawable.logout_icon),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Logout Confirmation",
                    style = getPoppinsBold20().copy( fontSize = 22.sp),
                    color = Color(0xFf000000)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                Text(
                    text = "Are you sure you want to logout?",
                    style = MaterialTheme.typography.titleMedium,
                    color =Color(0xFF444447),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Yes Button
                Button(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            showLogoutBottomSheet = false
                            onMenuItemClick("logout")
                        }
                    },
                    shape = RoundedCornerShape(25.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Yes, Logout",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // No Button
                Button(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            showLogoutBottomSheet = false
                        }
                    },
                    shape = RoundedCornerShape(25.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFF2D2D2D),
                            shape = RoundedCornerShape(25.dp)
                        )
                ) {
                    Text(
                        text = "No, Cancel",
                        color = Color(0xFF2D2D2D),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
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
