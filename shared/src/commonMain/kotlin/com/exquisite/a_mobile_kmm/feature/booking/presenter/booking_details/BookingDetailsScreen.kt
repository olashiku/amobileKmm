package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking_details

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.success_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screenUtils.formatTime
import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import com.exquisite.a_mobile_kmm.core.screen_components.GenericAlertModal
import com.exquisite.a_mobile_kmm.core.screen_components.ModalButton
import com.exquisite.a_mobile_kmm.core.screen_components.ModalType
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.core.utils.dialNumber
import com.exquisite.a_mobile_kmm.core.utils.sendMessage
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsExtraBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium10
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBooking
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.ToiletBookingModel
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookingDetailsScreen(
    customerBooking: CustomerBooking,
    goBack: () -> Unit = {},
    viewModel: BookingDetailsViewModel = koinViewModel<BookingDetailsViewModel>(),
    modifier: Modifier = Modifier
) {
    val colorsPalette = LocalColorsPalette.current
    val state = viewModel.bookingDetailsState.collectAsStateWithLifecycle()
    val rateReviewState = viewModel.rateReviewState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var showSuccessModal by remember {mutableStateOf(false)}
    var showRatingAndReview by remember {mutableStateOf(false)}


    LaunchedEffect(Unit) {
        when (customerBooking.bookingType) {
            "TOILET" -> viewModel.loadToiletBooking(customerBooking.bookingId)
            "SEPTIC_REQUEST" -> viewModel.loadSepticBooking(customerBooking.bookingId)
            "PEST_CONTROL" -> viewModel.loadPestControlBooking(customerBooking.bookingId)
            "BASIC_CLEANING", "DEEP_CLEANING" -> viewModel.loadCleaningBooking(customerBooking.bookingId)
        }
    }

     when(val result = rateReviewState.value){
         is RateReviewState.Idle -> {}
         is RateReviewState.Loading -> {
             LoadingDialog(true)

         }
         is RateReviewState.RateReviewSuccess -> {
             showSuccessModal = true
             GenericAlertModal(
                 modalType = ModalType.Success(iconRes = Res.drawable.success_icon),
                 title = "Thank You!",
                 message = "Your feedback means so much to us",
                 primaryButton = ModalButton(
                     text = "Continue to bookings",
                     backgroundColor = Color(0xFF10B981), // Green
                     action = {
                         showSuccessModal = false
                         goBack.invoke()
                     }
                 )
             )
         }
         is RateReviewState.Error -> {
             snackBar.showError(result.message)
         }
     }

    Scaffold(
        bottomBar = {
            if(customerBooking.serviceStatus.equals("AGENT_DEPLOYED")){
                Button(
                    onClick = { showRatingAndReview = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF09103))
                ) {
                    Text("Write a review", style = getPoppinsSemiBold16(), modifier = Modifier.padding(vertical = 2.dp))
                }
            }

        }
    ) { paddingValues ->
        when (val result = state.value) {
            is BookingDetailsState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorsPalette.captionColor)
                }
            }

            is BookingDetailsState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    Text(result.message, style = getPoppinsMedium12(), color = Color.Red)
                }
            }

            is BookingDetailsState.ToiletBookingSuccess -> {
                ToiletBookingDetailsContent(
                    customerBooking = customerBooking,
                    toiletBooking = result.data,
                    viewModel = viewModel,
                    goBack = goBack,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is BookingDetailsState.CleaningBookingSuccess -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    Text("Cleaning Booking Details", style = getPoppinsMedium12())
                }
            }

            is BookingDetailsState.SepticBookingSuccess -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    Text("Septic Booking Details", style = getPoppinsMedium12())
                }
            }

            is BookingDetailsState.PestControlBookingSuccess -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    Text("Pest Control Booking Details", style = getPoppinsMedium12())
                }
            }
            else ->{}

        }
    }

    // Rating and Review Modal
    if(showRatingAndReview){
        ShowRatingAndReview(
            customerBooking = customerBooking,
            viewModel = viewModel,
            onDismiss = {
                showRatingAndReview = false
            }
        )
    }
}

@Composable
private fun ToiletBookingDetailsContent(
    customerBooking: CustomerBooking,
    toiletBooking: ToiletBookingModel,
    viewModel: BookingDetailsViewModel,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorsPalette = LocalColorsPalette.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // Hero Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFFF09103))
                .padding(vertical = 25.dp, horizontal = 20.dp)
        ) {
            // Back button
            androidx.compose.material3.IconButton(
                onClick = goBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text("‹", style = getPoppinsSemiBold16().copy(fontSize = 24.sp), color = Color.White)
            }

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Toilet Booking Details",
                    style = getPoppinsSemiBold16(),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Agent Avatar
                Box(
                    modifier = Modifier
                        .size(85.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(4.dp, Color.White, RoundedCornerShape(24.dp))
                ) {
                    customerBooking.assignedAgent?.profilePictureUrl?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "Agent Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                customerBooking.assignedAgent?.let { agent ->
                    Text(
                        "${agent.firstName} ${agent.lastName}",
                        style = getPoppinsSemiBold14(),
                        color = Color.White
                    )
                    Text(
                        "Toilet Specialist",
                        style = getPoppinsMedium12(),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                } ?: run {
                    Text(
                        "No Agent Assigned",
                        style = getPoppinsMedium12(),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Status Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatusBadge(
                    label = "Payment",
                    value = customerBooking.paymentStatus ?: "Pending",
                    color = if (customerBooking.paymentStatus == "PAYMENT_COMPLETED") Color(0xFF27AE60) else Color(0XFFF09103),
                    modifier = Modifier.weight(1f)
                )
                StatusBadge(
                    label = "Service",
                    value = customerBooking.serviceStatus ?: "Pending",
                    color = colorsPalette.captionColor,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Agent Card - Only show if agent is assigned
            customerBooking.assignedAgent?.let { agent ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Need to reach ${agent.firstName}?",
                            style = getPoppinsMedium14(),
                            color = Color(0xFF1E293B)
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                            // Call button
                            androidx.compose.material3.IconButton(
                                onClick = { dialNumber(agent.phone) },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                            ) {
                                Text("📞", style = getPoppinsMedium14())
                            }

                            // Message button
                            androidx.compose.material3.IconButton(
                                onClick = { sendMessage(agent.phone) },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFFECFDF5), RoundedCornerShape(8.dp))
                            ) {
                                Text("💬", style = getPoppinsMedium14())
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Event & Company Info
            InfoCard(title = "Event & Company Info") {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    InfoItem("Company", toiletBooking.companyName, Modifier.weight(1f))
                    InfoItem("Duration", "${toiletBooking.toiletEstimate.numberOfDays} Day Event", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(
                    "Event Date",
                    "${toiletBooking.startDate.formatToReadableDate()} • ${toiletBooking.startTime} - ${toiletBooking.endTime}"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Toilet Specifications
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBF5)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CardTitle("Toilet Specifications")
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        InfoItem("Standard Toilets", "${toiletBooking.numberOfStandardToilet} Units", Modifier.weight(1f))
                        InfoItem("VIP Toilets", "${toiletBooking.numberOfVipToilet} Units", Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        InfoItem("Total Guests", "${toiletBooking.toiletEstimate.totalNumberOfGuests} People", Modifier.weight(1f))
                        InfoItem("Type",  if(toiletBooking.typeOfEvent.isEmpty()) "Unspecified" else toiletBooking.typeOfEvent, Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Location Photos - Combine both lists and filter valid URLs
            val imageUrls = (toiletBooking.pictureOfEventLocation + toiletBooking.pictureOfToiletPlacement)
                .filter { url -> url.length > 5 } // Simple check for valid URL

            if (imageUrls.isNotEmpty()) {
                InfoCard(title = "Location Photos") {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(imageUrls) { imageUrl ->
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                                    .background(Color(0xFFF3F3F5))
                            ) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Location Photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Payment Summary
            InfoCard(title = "Payment Summary") {
                PriceRow("Base Amount", "₦${(toiletBooking.toiletEstimate.totalAmount).formatBalance()}")
                Spacer(modifier = Modifier.height(8.dp))
                PriceRow("Overnight Fee", "₦${toiletBooking.toiletEstimate.overnight.formatBalance()}")
                Spacer(modifier = Modifier.height(8.dp))
                PriceRow("Discount Given", "- ₦${toiletBooking.toiletEstimate.discountGiven.formatBalance()}", Color(0xFF27AE60))
                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider(color = Color(0xFFE2E8F0), modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Paid", style = getPoppinsBold18(), color = Color(0xFFF09103))
                    Text("₦${customerBooking.amountPaid?.formatBalance()}", style = getPoppinsBold18(), color  = Color(0xFFF09103))
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun StatusBadge(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label.uppercase(), style = getPoppinsExtraBold12(), color = Color(0xFF64748B))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value.replace("_"," "), style = getPoppinsBold12(), color = color)
        }
    }
}

@Composable
private fun InfoCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardTitle(title)
            Spacer(modifier = Modifier.height(15.dp))
            content()
        }
    }
}

@Composable
private fun CardTitle(title: String) {
    Text(
        title.uppercase(),
        style = getPoppinsExtraBold12().copy(letterSpacing = 0.8.sp),
        color = Color(0xFF64748B),
        modifier = Modifier.padding(bottom = 8.dp)
    )
    HorizontalDivider(color = Color(0xFFF1F5F9))
}

@Composable
private fun InfoItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label.uppercase(), style = getPoppinsMedium10(), color = Color(0xFF64748B))
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = getPoppinsBold14(), color = Color(0xFF1E293B))
    }
}

@Composable
private fun PriceRow(label: String, value: String, valueColor: Color = Color(0xFF64748B)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = getPoppinsMedium13(), color = Color(0xFF64748B))
        Text(value, style = getPoppinsMedium13(), color = valueColor)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowRatingAndReview(
    customerBooking: CustomerBooking,
    viewModel: BookingDetailsViewModel,
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }
    val colorsPalette = LocalColorsPalette.current

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Rate Your Experience",
                style = getPoppinsBold18(),
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "How was your service?",
                style = getPoppinsMedium14(),
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Star Rating
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    val starNumber = index + 1
                    androidx.compose.material3.IconButton(
                        onClick = { rating = starNumber },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Text(
                            text = if (starNumber <= rating) "⭐" else "☆",
                            style = getPoppinsSemiBold16().copy(fontSize = 32.sp),
                            color = if (starNumber <= rating) Color(0xFFFBBF24) else Color(0xFFE2E8F0)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Comment TextField
            androidx.compose.material3.OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Share your thoughts (optional)", style = getPoppinsMedium12()) },
                placeholder = { Text("Tell us about your experience...", style = getPoppinsMedium12()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = colorsPalette.captionColor,
                    unfocusedIndicatorColor = Color(0xFFE2E8F0),
                    cursorColor = colorsPalette.captionColor
                ),
                textStyle = getPoppinsMedium14(),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    if (rating > 0) {
                        scope.launch {
                            viewModel.rateAndReview(
                                serviceType = customerBooking.bookingType,
                                comment = comment.ifBlank { "No comment" },
                                rate = rating,
                                bookingId = customerBooking.bookingId
                            )
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                },
                enabled = rating > 0,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF09103),
                    disabledContainerColor = Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Submit Review",
                    style = getPoppinsSemiBold16(),
                    color = if (rating > 0) Color.White else Color(0xFF94A3B8)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
