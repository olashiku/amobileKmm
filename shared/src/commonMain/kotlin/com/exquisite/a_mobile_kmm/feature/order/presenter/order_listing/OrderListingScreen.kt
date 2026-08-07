package com.exquisite.a_mobile_kmm.feature.order.presenter.order_listing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold11
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium11
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrder
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OrderListingScreen(
    onBackClick: (() -> Unit)? = null,
    onOrderClick: ((CustomerOrder) -> Unit)? = null,
    viewModel: OrderListingViewModel = koinViewModel<OrderListingViewModel>(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.orderListingState.collectAsState()

    // Load orders on first composition
    LaunchedEffect(Unit) {
        viewModel.loadCustomerOrders( pageNumber = 0, pageSize = 10)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEEF2F6))
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 15.dp)
                    .border(
                        width = 0.dp,
                        color = Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(0.dp)
                    )
            ) {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(0xFF1E293B),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Text(
                    text = "My Orders",
                    style = getPoppinsBold18(),
                    color = Color(0xFF1E293B),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE2E8F0))
            )

            // Content
            when (state) {
                is OrderListingState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFFF29100),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                is OrderListingState.Success -> {
                    val successState = state as OrderListingState.Success
                    val orders = successState.data.orders

                    if (orders.isEmpty()) {
                        EmptyOrdersState()
                    } else {
                        val listState = rememberLazyListState()

                        // Detect when user scrolls near the bottom
                        val shouldLoadMore = remember {
                            derivedStateOf {
                                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                                val totalItems = listState.layoutInfo.totalItemsCount

                                lastVisibleItem != null &&
                                lastVisibleItem.index >= totalItems - 3 && // Trigger 3 items before end
                                successState.hasMore &&
                                !successState.isLoadingMore
                            }
                        }

                        // Load next page when user scrolls near bottom
                        LaunchedEffect(shouldLoadMore.value, successState.hasMore) {
                            if (shouldLoadMore.value && successState.hasMore && !successState.isLoadingMore) {
                                viewModel.loadNextPage()
                            }
                        }

                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(orders) { order ->
                                OrderCard(
                                    order = order,
                                    onClick = {
                                        onOrderClick?.invoke(order)
                                    }
                                )
                            }

                            // Loading more indicator at bottom
                            if (successState.isLoadingMore) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            color = Color(0xFFF29100),
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is OrderListingState.Error -> {
                    ErrorState(
                        message = (state as OrderListingState.Error).message
                    )
                }

                OrderListingState.Idle -> {
                    // Initial state
                }
            }
        }
    }
}

@Composable
private fun OrderCard(
    order: CustomerOrder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val firstProduct = order.orderDetails.firstOrNull()
    val productImage = firstProduct?.product?.coverImageUrl ?: ""

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Product Image
        AsyncImage(
            model = productImage,
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF1F1F1))
        )

        // Order Info
        Column(
            modifier = Modifier
                .weight(1f)
                .height(80.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Order Header (ID + Status)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Ref: ${order.order.ref.takeLast(8)}",
                    style = getPoppinsSemiBold12(),
                    color = Color(0xFF64748B)
                )

                StatusBadge(status = order.order.status)
            }

            // Order Date
            Text(
                text = formatOrderDate(order.order.createdAt.formatToReadableDate()),
                style = getPoppinsMedium11(),
                color = Color(0xFF64748B)
            )

            // Footer (Payment Method + Price)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = getPaymentMethod(order),
                    style = getPoppinsMedium11(),
                    color = Color(0xFF64748B)
                )

                Text(
                    text = formatPrice(order.order.totalAmount ?: order.order.amount),
                    style = getPoppinsBold16(),
                    color = Color(0xFF1E293B)
                )
            }
        }

        // Chevron
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "View Details",
            tint = Color(0xFFCBD5E1),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(24.dp)
        )
    }
}

@Composable
private fun StatusBadge(status: String) {
    val (backgroundColor, textColor) = when (status.lowercase()) {
        "pending" -> Color(0xFFFFF7ED) to Color(0xFFC2410C)
        "confirmed" -> Color(0xFFF0FDF4) to Color(0xFF15803D)
        "delivered" -> Color(0xFFEFF6FF) to Color(0xFF1D4ED8)
        "pickup", "ready for pickup" -> Color(0xFFFEFCE8) to Color(0xFF854D0E)
        else -> Color(0xFFF1F5F9) to Color(0xFF475569)
    }

    Text(
        text = status.uppercase(),
        style = getPoppinsBold11(),
        color = textColor,
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        letterSpacing = 0.3.dp.value.dp.value.sp
    )
}

@Composable
private fun EmptyOrdersState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "No Orders Yet",
                style = getPoppinsBold18(),
                color = Color(0xFF1E293B)
            )
            Text(
                text = "Your order history will appear here",
                style = getPoppinsSemiBold14(),
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Oops!",
                style = getPoppinsBold18(),
                color = Color(0xFF1E293B)
            )
            Text(
                text = message,
                style = getPoppinsSemiBold14(),
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center
            )
        }
    }
}

// Helper functions
private fun formatOrderDate(dateString: String): String {
    return try {
        "Ordered on ${dateString.take(10)}"
    } catch (_: Exception) {
        "Ordered on $dateString"
    }
}

private fun getPaymentMethod(order: CustomerOrder): String {
    // This could be enhanced based on actual payment data in the model
    val itemCount = order.orderDetails.sumOf{it.quantity}
    return  "$itemCount ${if (itemCount == 1) "item" else "items"}"
}

private fun formatPrice(amount: Double): String {
    return "₦${amount.formatBalance()}"
}
