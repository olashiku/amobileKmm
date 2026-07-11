package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form_two

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.HybridDatePicker
import com.exquisite.a_mobile_kmm.core.screen_components.OptionCard
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.TimeSlotGrid
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular11
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeepCleaningFormTwoScreen(
    goBack: () -> Unit = {}, goToCheckoutPage: () -> Unit,
    modifier: Modifier = Modifier
) {

    val availableQuickDates = remember {
        com.exquisite.a_mobile_kmm.core.screen_components.generateAvailableDates(11)
    }

    val times = listOf("9:00 AM", "10:00 AM", "11:30 AM", "1:00 PM", "2:30 PM", "4:00 PM")
    var selectedTime by remember { mutableStateOf<String?>(times[1]) }
    var selectedDate by remember { mutableStateOf<DateModel?>(availableQuickDates.firstOrNull()) }

    var showModalCalendar by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val isFutureDate = selectedDate != null && !availableQuickDates.any { it.fullDate == selectedDate?.fullDate }
    var checked by remember { mutableStateOf(false) }

    val (snackBar, snackBarHostState) = rememberSnackBar()
    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Booking Details",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .padding(bottom = 80.dp) // Add bottom padding for button
                    .verticalScroll(rememberScrollState())
            ) {


                Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp)) {

                    Text(
                        text = "Select Cleaning Date",
                        modifier = Modifier.padding(horizontal = 20.dp),
                        style = getPoppinsSemiBold14()
                    )
                    Spacer(modifier = modifier.height(12.dp))

                    // 3. Implement the Hybrid Picker
                    HybridDatePicker(
                        dates = availableQuickDates,
                        selectedDate = selectedDate,
                        onDateSelected = { selectedDate = it },
                        onOpenFullCalendar = { showModalCalendar = true }
                    )
                    // 4. Show a summary if a "Future Date" was picked that isn't in the slider
                    if (isFutureDate) {
                        SuggestionChip(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                            onClick = { showModalCalendar = true },
                            label = {
                                Text(
                                    text = "Selected: ${selectedDate?.fullDate} (Change)",
                                    style = getPoppinsMedium14()
                                )
                            },
                            icon = {
                                Icon(
                                    Icons.Default.Edit,
                                    null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }

                    Spacer(modifier = modifier.height(24.dp))
                    Text(
                        text = "Select Time Slot",
                        modifier = Modifier.padding(horizontal = 20.dp),
                        style = getPoppinsSemiBold14()
                    )
                    Spacer(modifier = modifier.height(12.dp))
                    TimeSlotGrid(times, selectedTime) { selectedTime = it }
                    Spacer(modifier = modifier.height(24.dp))

                    OptionCard(
                        title = "Post-Construction?",
                        subtitle = "Extra deep clean for new spaces",
                        checked = checked,
                        onCheckedChange = { checked = it },
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = modifier.height(24.dp))

                    PhotoUploadSection(
                        title = "Photos of space to be serviced",
                        ctaText = "Tap to Capture or Upload",
                        helperText = "Max 5 photos • High quality preferred",
                        onTap = {

                        },
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )



                }

                // 5. The Standard Material 3 Date Picker Dialog
                if (showModalCalendar) {
                    DatePickerDialog(
                        onDismissRequest = { showModalCalendar = false },
                        confirmButton = {
                            TextButton(onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    // Convert millis to LocalDate using kotlinx-datetime
                                    val instant = Instant.fromEpochMilliseconds(millis)
                                    val localDate =
                                        instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

                                    // Create DateModel from LocalDate
                                    selectedDate = DateModel(
                                        dayName = localDate.dayOfWeek.name.take(3).uppercase(),
                                        dayNumber = localDate.dayOfMonth.toString(),
                                        fullDate = "${localDate.year}-${
                                            localDate.monthNumber.toString().padStart(2, '0')
                                        }-${localDate.dayOfMonth.toString().padStart(2, '0')}"
                                    )
                                }
                                showModalCalendar = false
                            }) { Text("Select") }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
        }
        PrimaryButton("Proceed to Checkout", {
            if (selectedDate != null) {
                goToCheckoutPage.invoke()
            } else {
                snackBar.showWarning("Please select a cleaning date")
            }
        }, modifier = Modifier.align(BottomCenter).padding(20.dp))

        // Snackbar above the button
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(bottom = 90.dp)
        )
    }
}

@Composable
fun PhotoUploadSection(
    title: String,
    ctaText: String,
    helperText: String,
    onTap: () -> Unit,
    modifier: Modifier = Modifier,
    emoji: String = "\uD83D\uDCF8" // 📸
) {
    val labelColor = Color(0xFF1E293B)
    val helperColor = Color(0xFF9A9AA0)
    val dashColor = Color(0xFFC7CCD6)
    val fillColor = Color(0xFFF6F7FA)

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
          style = getPoppinsRegular14(),
            color = labelColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(fillColor, shape = RoundedCornerShape(20.dp))
                .drawBehind {
                    val stroke = Stroke(
                        width = 1.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
                    )
                    drawRoundRect(
                        color = dashColor,
                        style = stroke,
                        cornerRadius = CornerRadius(20.dp.toPx(), 20.dp.toPx())
                    )
                }
                .clickable { onTap() }
                .padding(vertical = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$emoji  $ctaText",
                   style = getPoppinsSemiBold13(),
                    color = labelColor
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = helperText,
                    style= getPoppinsRegular11(),
                    color = helperColor
                )
            }
        }
    }
}
