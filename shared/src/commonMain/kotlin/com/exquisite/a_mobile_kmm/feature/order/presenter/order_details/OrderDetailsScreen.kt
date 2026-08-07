package com.exquisite.a_mobile_kmm.feature.order.presenter.order_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold11
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrder
import com.exquisite.a_mobile_kmm.feature.order.domain.model.OrderDetail

@Composable
fun OrderDetailsScreen(
    orderId: Int = -1,
    order: CustomerOrder? = null,  // For backwards compatibility
    onBackClick: (() -> Unit)? = null,
    viewModel: com.exquisite.a_mobile_kmm.feature.order.presenter.order_listing.OrderListingViewModel = org.koin.compose.viewmodel.koinViewModel(),
    modifier: Modifier = Modifier
) {
    println("OrderDetailsScreen: orderId=$orderId")

    // Get orders from viewModel and find the specific order by ID
    val orderListingState by viewModel.orderListingState.collectAsState()

    val sampleOrder = when {
        order != null -> order
        orderId != -1 && orderListingState is com.exquisite.a_mobile_kmm.feature.order.presenter.order_listing.OrderListingState.Success -> {
            val orders = (orderListingState as com.exquisite.a_mobile_kmm.feature.order.presenter.order_listing.OrderListingState.Success).data.orders
            orders.find { it.order.id == orderId } ?: createSampleOrder()
        }
        else -> createSampleOrder()
    }

    println("OrderDetailsScreen: Order loaded successfully")

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
                    text = "Order Details",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Order Info Card
                OrderInfoCard(order = sampleOrder)

                // Order Items Section
                Text(
                    text = "Order Items",
                    style = getPoppinsBold14(),
                    color = Color(0xFF1E293B)
                )

                OrderItemsCard(order = sampleOrder)

                // Shipping Section
                Text(
                    text = "Shipping & Delivery",
                    style = getPoppinsBold14(),
                    color = Color(0xFF1E293B)
                )

                ShippingCard(
                    order = sampleOrder,
                    onCopyTracking = {
                        // TODO: Copy to clipboard
                    }
                )

                // Track Button
                Button(
                    onClick = {
                        sampleOrder.shipping?.trackingUrl?.let { trackingUrl ->
                            // Open in system browser instead of WebView
                            com.exquisite.a_mobile_kmm.core.utils.openInBrowser(trackingUrl)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF29100)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Track Your Shipment",
                        style = getPoppinsBold16(),
                        color = Color.White
                    )
                }

                // Important Info
                ImportantInfoSection()

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun OrderInfoCard(order: CustomerOrder) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        // Status Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Order #${order.order.ref.split("-").last()}",
                style = getPoppinsBold13(),
                color = Color(0xFF1E293B)
            )

            StatusBadge(status = order.order.status)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Date
        Text(
            text = formatOrderDateTime(order.order.createdAt),
            style = getPoppinsMedium12(),
            color = Color(0xFF64748B)
        )
    }
}

@Composable
private fun OrderItemsCard(order: CustomerOrder) {
    println("OrderItemsCard: Rendering ${order.orderDetails.size} items")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        // Items List
        println("OrderItemsCard: About to render items list")
        order.orderDetails.forEachIndexed { index, orderDetail ->
            println("OrderItemsCard: Rendering item $index")
            OrderItemRow(orderDetail = orderDetail)

            if (index < order.orderDetails.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        println("OrderItemsCard: Items rendered, now rendering summary")

        // Summary Table
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            // Dashed Divider
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtotal
            SummaryRow(
                label = "Subtotal",
                value = "₦${(order.order.amount.formatBalance())}"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Shipping (calculated as difference if totalAmount exists)
            val shipping = kotlin.math.max(0.0, (order.order.totalAmount ?: order.order.amount) - order.order.amount - order.order.taxAmount)
            SummaryRow(
                label = "Shipping",
                value = "₦${shipping.formatBalance()}"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tax
            SummaryRow(
                label = "Tax",
                value = "₦${order.order.taxAmount.formatBalance()}"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Divider before total
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Amount",
                    style = getPoppinsBold16(),
                    color = Color(0xFFF29100)
                )

                Text(
                    text = "₦${(order.order.totalAmount ?: order.order.amount).formatBalance()}",
                    style = getPoppinsBold16(),
                    color = Color(0xFFF29100)
                )
            }
        }
    }
}

@Composable
private fun OrderItemRow(orderDetail: OrderDetail) {
    println("OrderItemRow: Rendering item ${orderDetail.product.name}")

    // Safe price calculation outside composable
    val totalPrice = try {
        (orderDetail.quantity * orderDetail.amount).formatBalance()
    } catch (e: Exception) {
        println("OrderItemRow: Error formatting price: ${e.message}")
        "0.00"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Product Image - with error handling
        if (orderDetail.product.coverImageUrl.isNotBlank()) {
            AsyncImage(
                model = orderDetail.product.coverImageUrl,
                contentDescription = orderDetail.product.name,
                contentScale = ContentScale.Crop,
                onError = {
                    println("OrderItemRow: Error loading image for ${orderDetail.product.name}")
                },
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF1F1F1))
            )
        } else {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF1F1F1))
            )
        }

        // Item Details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = orderDetail.product.name,
                style = getPoppinsSemiBold13(),
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Quantity: ${orderDetail.quantity}",
                style = getPoppinsMedium12(),
                color = Color(0xFF64748B)
            )
        }

        // Price
        Text(
            text = "₦$totalPrice",
            style = getPoppinsBold13(),
            color = Color(0xFF1E293B)
        )
    }
}

@Composable
private fun ShippingCard(
    order: CustomerOrder,
    onCopyTracking: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Delivery Address
        Column {
            Text(
                text = "DELIVERY ADDRESS",
                style = getPoppinsBold11(),
                color = Color(0xFF64748B),
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = order.order.address.address,
                style = getPoppinsSemiBold13(),
                color = Color(0xFF1E293B),
                lineHeight = 18.sp
            )
        }

        // Tracking ID
        if (order.shipping != null) {
            Column {
                Text(
                    text = "TRACKING ID",
                    style = getPoppinsBold11(),
                    color = Color(0xFF64748B),
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF8FAFC))
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = order.shipping.trackingUrl?.split("/")?.lastOrNull() ?: "",
                        style = getPoppinsBold13().copy(
                            fontFamily = FontFamily.Monospace
                        ),
                        color = Color(0xFFF29100)
                    )
                }
            }
        }
    }
}

@Composable
private fun ImportantInfoSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF1F5F9))
            .padding(16.dp)
    ) {
        Text(
            text = "IMPORTANT INFORMATION",
            style = getPoppinsBold12(),
            color = Color(0xFF64748B),
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        InfoListItem("You'll receive a notification when your order ships.")
        Spacer(modifier = Modifier.height(8.dp))
        InfoListItem("Ensure someone is available to receive the delivery.")
        Spacer(modifier = Modifier.height(8.dp))
        InfoListItem("Changes allowed within 2 hours of purchase.")
    }
}

@Composable
private fun InfoListItem(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "•",
            style = getPoppinsMedium12(),
            color = Color(0xFFF29100),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = text,
            style = getPoppinsMedium12(),
            color = Color(0xFF1E293B),
            lineHeight = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = getPoppinsMedium13(),
            color = Color(0xFF64748B)
        )

        Text(
            text = value,
            style = getPoppinsMedium13(),
            color = Color(0xFF64748B)
        )
    }
}

@Composable
private fun StatusBadge(status: String) {
    val backgroundColor = when (status.lowercase()) {
        "pending" -> Color(0xFFFFF7ED)
        "confirmed", "processing" -> Color(0xFFF0FDF4)
        "delivered" -> Color(0xFFEFF6FF)
        "pickup", "ready for pickup" -> Color(0xFFFEFCE8)
        else -> Color(0xFFF1F5F9)
    }

    val textColor = when (status.lowercase()) {
        "pending" -> Color(0xFFC2410C)
        "confirmed", "processing" -> Color(0xFF15803D)
        "delivered" -> Color(0xFF1D4ED8)
        "pickup", "ready for pickup" -> Color(0xFF854D0E)
        else -> Color(0xFF475569)
    }

    Text(
        text = status.uppercase(),
        style = getPoppinsBold11(),
        color = textColor,
        letterSpacing = 0.3.sp,
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

// Helper functions
private fun formatOrderDateTime(dateString: String): String {
    return try {
        "Placed on ${dateString.take(10)} • ${dateString.substring(11, 16)}"
    } catch (e: Exception) {
        "Placed on $dateString"
    }
}



// Sample order for demonstration
private fun createSampleOrder(): CustomerOrder {
    return CustomerOrder(
        order = com.exquisite.a_mobile_kmm.feature.order.domain.model.Order(
            id = 1,
            status = "Processing",
            ref = "ORD-56EE2",
            amount = 10244.00,
            taxAmount = 768.30,
            totalAmount = 13782.30,
            address = com.exquisite.a_mobile_kmm.feature.order.domain.model.Address(
                id = 1,
                address = "Thomas\n14 Asajon way, Lekki, Nigeria",
                phone = null,
                addressCode = 0,
                createdAt = "",
                updatedAt = ""
            ),
            createdAt = "2025-08-21 14:30:00",
            updatedAt = ""
        ),
        orderDetails = listOf(
            OrderDetail(
                id = 1,
                product = com.exquisite.a_mobile_kmm.feature.order.domain.model.OrderProduct(
                    id = 1,
                    sku = "WC-001",
                    name = "Wood Cleaner",
                    description = "Professional wood cleaner",
                    price = 5000.00,
                    quantity = 2,
                    status = true,
                    weight = 1.0,
                    isEnabled = true,
                    coverImageUrl = "",
                    categoryName = "Cleaning",
                    createdAt = "",
                    updateAt = ""
                ),
                quantity = 2,
                amount = 10000.00,
                ref = "ITEM-001"
            ),
            OrderDetail(
                id = 2,
                product = com.exquisite.a_mobile_kmm.feature.order.domain.model.OrderProduct(
                    id = 2,
                    sku = "DG-001",
                    name = "Degreaser",
                    description = "Industrial degreaser",
                    price = 122.00,
                    quantity = 2,
                    status = true,
                    weight = 0.5,
                    isEnabled = true,
                    coverImageUrl = "",
                    categoryName = "Cleaning",
                    createdAt = "",
                    updateAt = ""
                ),
                quantity = 2,
                amount = 244.00,
                ref = "ITEM-002"
            )
        ),
        shipping = com.exquisite.a_mobile_kmm.feature.order.domain.model.Shipping(
            id = 1,
            ref = "SB-48D3B3DA9C4D",
            requestToken = "",
            courierId = "",
            shippingAmount = "2770.00",
            serviceCode = "",
            status = "",
            trackingUrl = "https://tracking.example.com/SB-48D3B3DA9C4D",
            createdAt = "",
            updatedAt = ""
        )
    )
}
