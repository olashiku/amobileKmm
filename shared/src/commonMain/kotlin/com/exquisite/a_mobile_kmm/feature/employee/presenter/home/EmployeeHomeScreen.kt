package com.exquisite.a_mobile_kmm.feature.employee.presenter.home

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.avatar_line
import amobilekmm.shared.generated.resources.notification_icon
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.getTimeBasedGreeting
import com.exquisite.a_mobile_kmm.core.screen_components.AvatarIcon
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentServiceCounts
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.EmployeeHomeModel
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.getEmployeeMenuItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmployeeHomeScreen(
    goToBooking: (String) -> Unit,
    viewModel: EmployeeHomeScreenViewModel = koinViewModel<EmployeeHomeScreenViewModel>()
) {
    val customerNameState = viewModel.customerName.collectAsStateWithLifecycle()
    val profilePictureState = viewModel.profilePicture.collectAsStateWithLifecycle()
    val serviceCountsState = viewModel.serviceCountsState.collectAsStateWithLifecycle()
    val menuItems = getEmployeeMenuItem()

    LaunchedEffect(Unit){
        viewModel. getCustomerName()
        viewModel. getProfilePicture()
        viewModel.fetchServiceCounts()
    }

    // Calculate grid height dynamically
    val itemHeight = 180.dp
    val verticalSpacing = 8.dp
    val columns = 2
    val rows = kotlin.math.ceil(menuItems.size / columns.toDouble()).toInt()
    val gridHeight = (itemHeight * rows) + (verticalSpacing * (rows - 1))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(0.dp)) // Top padding
        }

        item {
            // Header Section
            HeaderSection(
                profilePicture = profilePictureState.value,
                customerName = customerNameState.value,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            // Section Title
            SectionTitle(
                title = "Manage Tasks",
                subtitle = "Select a department to update status"
            )
        }

        item {
            // Handle different states with exhaustive when
            when (val state = serviceCountsState.value) {
                is ServiceCountsUiState.Initial -> {
                    // Show skeleton loader
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.height(gridHeight),
                        userScrollEnabled = false
                    ) {
                        items(menuItems.size) {
                            SkeletonMenuItem()
                        }
                    }
                }
                is ServiceCountsUiState.Loading -> {
                    // Show skeleton loader
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.height(gridHeight),
                        userScrollEnabled = false
                    ) {
                        items(menuItems.size) {
                            SkeletonMenuItem()
                        }
                    }
                }
                is ServiceCountsUiState.Success -> {
                    // Show menu grid with actual counts
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.height(gridHeight),
                        userScrollEnabled = false
                    ) {
                        items(menuItems) { item ->
                            MenuItem(
                                employeeHomeModel = item,
                                count = getCountForItem(item.tag, state.data),
                                goToBooking = goToBooking
                            )
                        }
                    }
                }
                is ServiceCountsUiState.Error -> {
                    // Show error with retry
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(gridHeight),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.message,
                            style = getPoppinsRegular13(),
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap to retry",
                            style = getPoppinsSemiBold13(),
                            color = Color(0xFF0F172A),
                            modifier = Modifier.clickable { viewModel.retryFetchServiceCounts() }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(0.dp)) // Bottom padding
        }
    }
}

private fun getCountForItem(itemName: String, counts: AgentServiceCounts): Int {
    return when (itemName.lowercase()) {
        "mobile_toilet" -> counts.toiletCount
        "pest_control" -> counts.pestControlCount
        "cleaning" -> counts.basicCleaningCount + counts.deepCleaningCount
        "septic_request" -> counts.septicRequestCount
        else -> 0
    }
}

@Composable
private fun HeaderSection(
    profilePicture: String,
    customerName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // Profile Picture
            if (profilePicture.isEmpty()) {
                AvatarIcon(50.dp, vectorResource(Res.drawable.avatar_line))
            } else {
                AsyncImage(
                    model = profilePicture,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(
                            1.dp,
                            LocalColorsPalette.current.borderColor,
                            CircleShape
                        )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Greeting & Name
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = getTimeBasedGreeting(),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF64748B)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = customerName,
                    style = getPoppinsSemiBold18(),
                    color = Color(0xFF0F172A)
                )
            }
        }

        // Notification Icon
        Image(
            painter = painterResource(Res.drawable.notification_icon),
            contentDescription = "Notifications",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun SectionTitle(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = getPoppinsBold18(),
            color = Color(0xFF0F172A)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            style = getPoppinsRegular13(),
            color = Color(0xFF64748B)
        )
    }
}

@Composable
fun MenuItem(
    employeeHomeModel: EmployeeHomeModel,
    count: Int = 0,
    goToBooking: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { goToBooking(employeeHomeModel.tag) }
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Notification Badge - Only show if count > 0
            if (count > 0) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = 8.dp)
                        .background(Color.Red, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = count.toString(),
                        color = Color.White,
                        style = getPoppinsSemiBold12()
                    )
                }
            }

            // Content (Icon + Text)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(employeeHomeModel.icon),
                    contentDescription = employeeHomeModel.name
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = employeeHomeModel.name,
                    style = getPoppinsSemiBold13(),
                    color = Color(0xFF0F172A),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}