package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import coil3.compose.AsyncImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBooking
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBookingsModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookingScreen(
    toBookingDetails:(String) -> Unit = {},
    viewModel: BookingViewModel = koinViewModel<BookingViewModel>(), modifier: Modifier = Modifier
) {

    val state = viewModel.bookingState.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    var isLoading by remember { mutableStateOf(false) }
    var customerBookingsModel by remember { mutableStateOf(CustomerBookingsModel(emptyList())) }

    when (val result = state.value) {
        is BookingState.Success -> {
            isLoading = false
            customerBookingsModel = result.data
        }

        is BookingState.Error -> {
            isLoading = false
        }

        is BookingState.Loading -> {
            isLoading = true
        }

        is BookingState.Idle -> {}
    }

    LaunchedEffect(Unit) {
        viewModel.loadCustomerBookings()
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleIndex != null && lastVisibleIndex >= totalItems - 2 && !isLoading) {
                    viewModel.loadMoreBookings()
                }
            }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 27.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "My Bookings", color = Color(0xff252525),
                        style = getPoppinsSemiBold18()
                    )
                }
            }

            item { Spacer(modifier = modifier.height(15.dp)) }

            if (isLoading && customerBookingsModel.bookings.isEmpty()) {
                items(5) {
                    BookingItemSkeleton()
                }
            } else {
                items(customerBookingsModel.bookings) { item ->
                    BookingItems(item,toBookingDetails)
                }

                if (isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFFF09103),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingItems(customerBooking: CustomerBooking, toBookingDetails:(String) -> Unit = {}, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable{
                toBookingDetails(NavigationUtils.encodeObject(customerBooking))
            }
        ,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFEF9F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Header: Service Status Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = getServiceStatusColor(customerBooking.serviceStatus),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = formatStatus(customerBooking.serviceStatus),
                        color = Color.White,
                       style = getPoppinsMedium12().copy(
                        letterSpacing = 0.5.sp
                    ))
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Booking Type Title
            Text(
                text = formatBookingType(customerBooking.bookingType),
                style = getPoppinsSemiBold18(),
                color = Color(0xFF252525)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Booking Description
            Text(
                text = customerBooking.bookingDescription,
                style = getPoppinsRegular14(),
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Assigned Agent Section (if available)
            customerBooking.assignedAgent?.let { agent ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Agent Profile Picture
                    if (agent.profilePictureUrl != null) {
                        AsyncImage(
                            model = agent.profilePictureUrl,
                            contentDescription = "Agent Profile",
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFFE8E4DD), CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFFE8E4DD), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "No Profile",
                                tint = Color(0xFF999999),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Assigned Agent",
                            style = getPoppinsMedium12(),
                            color = Color(0xFF999999)
                        )
                        Text(
                            text = "${agent.firstName} ${agent.lastName}",
                            style = getPoppinsSemiBold16(),
                            color = Color(0xFF252525)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            HorizontalDivider(
                color = Color(0xFFE8E4DD),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Payment Status & Amount
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = if (customerBooking.paymentStatus == "PAYMENT_COMPLETED")
                                    Color(0xFFF09103) else Color(0xFFE8E4DD),
                                shape = CircleShape
                            )
                    ) {
                        if (customerBooking.paymentStatus == "PAYMENT_COMPLETED") {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Paid",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = formatStatus(customerBooking.paymentStatus),
                            style = getPoppinsMedium12(),
                            color = Color(0xFF666666)
                        )
                    }
                }

                customerBooking.amountPaid?.let { amount ->
                    Text(
                        text = "₦${formatAmount(amount)}",
                        style = getPoppinsSemiBold18(),
                        color = Color(0xFFF09103)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date Created
            Text(
                text = "Created: ${customerBooking.createdAt.formatToReadableDate()}",
                style = getPoppinsMedium12(),
                color = Color(0xFF999999)
            )
        }
    }
}

private fun formatBookingType(type: String): String {
    return type.replace("_", " ")
        .lowercase()
        .split(" ")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}

private fun formatStatus(status: String?): String {
    return status?.replace("_", " ")
        ?.lowercase()
        ?.split(" ")
        ?.joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
        ?: "N/A"
}

private fun getServiceStatusColor(status: String?): Color {
    return when (status) {
        "REQUEST_PENDING", "REQUEST_RECEIVED" -> Color(0xFF64748B) // Gray - Pending
        "REQUEST_CONFIRMED" -> Color(0xFF3498DB) // Blue - Confirmed
        "AGENT_DEPLOYED" -> Color(0xFFF29100) // Orange - In Progress
        "JOB_COMPLETED" -> Color(0xFF10B981) // Green - Completed
        "CANCELLED" -> Color(0xFFF44336) // Red - Cancelled
        else -> Color(0xFF64748B) // Default gray
    }
}

private fun formatAmount(amount: Double): String {
    val formatted = amount.toLong().toString()
    return formatted.reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}

@Composable
fun BookingItemSkeleton(modifier: Modifier = Modifier) {
    Column {
        Box(
            modifier = modifier
                .width(150.dp)
                .height(24.dp)
                .background(color = Color(0xFFE8E4DD), shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = modifier.height(15.dp))

        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xffFEF9F2)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(28.dp)
                        .background(color = Color(0xFFE8E4DD), shape = RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .background(color = Color(0xFFE8E4DD), shape = RoundedCornerShape(4.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(16.dp)
                        .background(color = Color(0xFFE8E4DD), shape = RoundedCornerShape(4.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    color = Color(0xFFE8E4DD),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(color = Color(0xFFE8E4DD), shape = CircleShape)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(16.dp)
                            .background(color = Color(0xFFE8E4DD), shape = RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}



