package com.exquisite.a_mobile_kmm.feature.employee.presenter.booking_details

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.exquisite.a_mobile_kmm.core.camera.rememberCameraLauncher
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screenUtils.formatTime
import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import com.exquisite.a_mobile_kmm.core.screenUtils.generateImageFileName
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.screen_components.ImageGrid
import com.exquisite.a_mobile_kmm.core.screen_components.MediaSourceDialog
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
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
import com.exquisite.a_mobile_kmm.core.utils.dialNumber
import com.exquisite.a_mobile_kmm.core.utils.sendMessage
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CleaningBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBooking
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.PestControlBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.SepticBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.ToiletBookingModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form_two.PhotoUploadSection
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentBooking
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.BookingUpdateType
import com.exquisite.a_mobile_kmm.feature.employee.presenter.booking.UpdateBookingUiState
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs

@Composable
fun EmployeeBookingDetailsScreen(
    agentBooking: AgentBooking,
    goBack: () -> Unit = {},
    goToHomePage: () -> Unit = {},
    viewModel: EmployeeBookingDetailsViewModel = koinViewModel<EmployeeBookingDetailsViewModel>()
) {

    val colorsPalette = LocalColorsPalette.current
    val state = viewModel.bookingDetailsState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var showRatingAndReview by remember { mutableStateOf(false) }

    var imageByte by remember { mutableStateOf<ByteArray?>(null) }
    val scope = rememberCoroutineScope()

    val imageUploadState = viewModel.imageUploadState.collectAsStateWithLifecycle()
    val updateImagesState = viewModel.updateImagesState.collectAsStateWithLifecycle()
    val updateBookingState = viewModel.updateState.collectAsStateWithLifecycle()
    var showImageSourceDialog by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf<MutableList<String>>(mutableListOf()) }
    val showLoadingDialog = remember { mutableStateOf(false) }
    val showUpdateCustomerBookingDialog = remember { mutableStateOf(false) }

    val cameraLauncher = rememberCameraLauncher { imageData ->
        imageData?.let {
            imageByte = it
            viewModel.uploadImage(it, generateImageFileName(it))
        }
    }

    val imagePickerLaunch = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { imageData ->
                imageByte = imageData
                viewModel.uploadImage(imageData, generateImageFileName(imageData))
            }
        }
    )

    LaunchedEffect(agentBooking.bookingType) {
        when (agentBooking.bookingType) {
            "TOILET" -> viewModel.loadToiletBooking(agentBooking.bookingId)
            "SEPTIC_REQUEST" -> viewModel.loadSepticBooking(agentBooking.bookingId)
            "PEST_CONTROL" -> viewModel.loadPestControlBooking(agentBooking.bookingId)
            "BASIC_CLEANING", "DEEP_CLEANING" -> viewModel.loadCleaningBooking(agentBooking.bookingId)
        }
    }

    LaunchedEffect(imageUploadState.value) {
        when (val uploadResult = imageUploadState.value) {
            is ImageUploadState.Success -> {
                showLoadingDialog.value = false
                imageUrl.add(uploadResult.url)
            }

            is ImageUploadState.Error -> {
                showLoadingDialog.value = false
                snackBar.showError(uploadResult.message)
            }

            is ImageUploadState.Loading -> {
                showLoadingDialog.value = true
            }
            else -> {}
        }
    }

    if (showLoadingDialog.value) {
        LoadingDialog(true)
    } else {
        LoadingDialog(false)
    }

    when (val result = updateImagesState.value) {
        is UpdateBookingImagesState.Loading -> {
            LoadingDialog(true)
        }

        is UpdateBookingImagesState.Success -> {

            LaunchedEffect(Unit) {
                imageUrl = mutableListOf()
                when (agentBooking.bookingType) {
                    "BASIC_CLEANING", "DEEP_CLEANING" -> viewModel.loadCleaningBooking(agentBooking.bookingId)
                    else -> {}
                }
            }
        }

        is UpdateBookingImagesState.Error -> {
            snackBar.showError(result.message)
        }

        else -> {}
    }


    Scaffold(
        bottomBar = {
            if (agentBooking.serviceStatus.equals("AGENT_DEPLOYED")) {
                Button(
                    onClick = {
                        showUpdateCustomerBookingDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF09103))
                ) {
                    Text(
                        "Update booking",
                        style = getPoppinsSemiBold16(),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }

        }
    ) { paddingValues ->
        when (val result = state.value) {
            is BookingDetailsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = colorsPalette.captionColor)
                }
            }

            is BookingDetailsState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(result.message, style = getPoppinsMedium12(), color = Color.Red)
                }
            }

            is BookingDetailsState.ToiletBookingSuccess -> {
                ToiletBookingDetailsContent(
                    agentBooking = agentBooking,
                    toiletBooking = result.data,
                    viewModel = viewModel,
                    goBack = goBack,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is BookingDetailsState.CleaningBookingSuccess -> {

                if (result.data.serviceType.equals("BASIC_CLEANING")) {
                    CleaningBookingDetailsContent(
                        agentBooking = agentBooking,
                        cleaningBooking = result.data,
                        viewModel = viewModel,
                        goBack = goBack,
                        modifier = Modifier.padding(paddingValues)
                    )
                } else {
                    DeepCleaningBookingDetailsContent(
                        agentBooking = agentBooking,
                        cleaningBooking = result.data,
                        viewModel = viewModel,
                        goBack = goBack,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }

            is BookingDetailsState.SepticBookingSuccess -> {
                SepticBookingDetailsContent(
                    agentBooking = agentBooking,
                    septicBooking = result.data,
                    viewModel = viewModel,
                    goBack = goBack,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is BookingDetailsState.PestControlBookingSuccess -> {
                PestControlBookingDetailsContent(
                    customerBooking = agentBooking,
                    pestControlBooking = result.data,
                    viewModel = viewModel,
                    goBack = goBack,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            else -> {}
        }

        if (showImageSourceDialog) {
            MediaSourceDialog(
                onDismiss = { showImageSourceDialog = false },
                title = "Upload Picture",
                description = "Choose how you'd like to upload your picture:",
                showCamera = true,
                showGallery = true,
                showDocument = false,
                onCameraSelected = {
                    showImageSourceDialog = false
                    cameraLauncher.launch()
                },
                onGallerySelected = {
                    showImageSourceDialog = false
                    imagePickerLaunch.launch()
                }
            )
        }

        if (showUpdateCustomerBookingDialog.value) {
            UpdateBookingDialog(
                agentBooking = agentBooking,
                viewModel = viewModel,
                updateBookingState = updateBookingState.value,
                onDismiss = { showUpdateCustomerBookingDialog.value = false },
                onSuccess = {
                    showUpdateCustomerBookingDialog.value = false
                    goToHomePage.invoke()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UpdateBookingDialog(
    agentBooking: AgentBooking,
    viewModel: EmployeeBookingDetailsViewModel,
    updateBookingState: UpdateBookingUiState,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var agentRemark by remember { mutableStateOf("") }
    var selectedAction by remember { mutableStateOf<BookingUpdateType?>(null) }
    val (snackBar, snackBarHostState) = rememberSnackBar()

    // Check if agent has already clocked in/out
    val hasClockIn = agentBooking.agentClockInDateTime != null
    val hasClockOut = agentBooking.agentClockOutDateTime != null

    val availableActions = remember(agentBooking.serviceStatus, hasClockIn, hasClockOut) {
        if (agentBooking.serviceStatus == "AGENT_DEPLOYED") {
            buildList {
                if (!hasClockIn) {
                    add(BookingUpdateType.CLOCK_IN to "Clock In")
                }
                if (hasClockIn && !hasClockOut) {
                    add(BookingUpdateType.CLOCK_OUT to "Clock Out (Pause)")
                }
            }
        } else {
            emptyList()
        }
    }

    LaunchedEffect(updateBookingState) {
        when (val state = updateBookingState) {
            is UpdateBookingUiState.Success -> {
                snackBar.showSuccess(state.message)
                scope.launch {
                    sheetState.hide()
                    onSuccess()
                }
            }
            is UpdateBookingUiState.Error -> {
                snackBar.showError(state.message)
            }
            else -> {}
        }
    }

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Title
            Text(
                text = "Update Booking",
                style = getPoppinsBold18(),
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Select an action to update this booking",
                style = getPoppinsMedium14(),
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Current Status and Clock Times Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Current Status
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "CURRENT STATUS",
                                style = getPoppinsExtraBold12().copy(fontSize = 10.sp),
                                color = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                agentBooking.serviceStatus?.replace("_", " ") ?: "Unknown",
                                style = getPoppinsBold14(),
                                color = Color(0xFF1E293B)
                            )
                        }

                        // Status indicator dot
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(
                                    when (agentBooking.serviceStatus) {
                                        "REQUEST_CONFIRMED" -> Color(0xFF3498DB)
                                        "AGENT_DEPLOYED" -> Color(0xFFF09103)
                                        "JOB_IN_PROGRESS" -> Color(0xFF10B981)
                                        "JOB_COMPLETED" -> Color(0xFF8B5CF6)
                                        else -> Color(0xFF94A3B8)
                                    }
                                )
                        )
                    }

                    // Clock In/Out Times
                    if (hasClockIn || hasClockOut) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color(0xFFE2E8F0))
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Clock In Time
                            if (hasClockIn) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("🕐", style = getPoppinsMedium12())
                                        Text(
                                            "CLOCKED IN",
                                            style = getPoppinsExtraBold12().copy(fontSize = 9.sp),
                                            color = Color(0xFF10B981)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        agentBooking.agentClockInDateTime ?: "N/A",
                                        style = getPoppinsMedium12().copy(fontSize = 11.sp),
                                        color = Color(0xFF1E293B)
                                    )
                                }
                            }

                            // Clock Out Time
                            if (hasClockOut) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("⏸️", style = getPoppinsMedium12())
                                        Text(
                                            "CLOCKED OUT",
                                            style = getPoppinsExtraBold12().copy(fontSize = 9.sp),
                                            color = Color(0xFFF09103)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        agentBooking.agentClockOutDateTime ?: "N/A",
                                        style = getPoppinsMedium12().copy(fontSize = 11.sp),
                                        color = Color(0xFF1E293B)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            if (agentBooking.serviceStatus != "AGENT_DEPLOYED") {
                // Show message for non-AGENT_DEPLOYED statuses
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "⚠️",
                            style = getPoppinsBold18().copy(fontSize = 32.sp)
                        )
                        Text(
                            "Status updates only available for",
                            style = getPoppinsMedium14(),
                            color = Color(0xFF64748B),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "AGENT DEPLOYED",
                            style = getPoppinsBold14(),
                            color = Color(0xFFF09103),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Current: ${agentBooking.serviceStatus?.replace("_", " ")}",
                            style = getPoppinsMedium12(),
                            color = Color(0xFF94A3B8),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else if (availableActions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "✅",
                            style = getPoppinsBold18().copy(fontSize = 32.sp)
                        )
                        Text(
                            "All actions completed",
                            style = getPoppinsMedium14(),
                            color = Color(0xFF64748B),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "You have already clocked in and out",
                            style = getPoppinsMedium12(),
                            color = Color(0xFF94A3B8),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Text(
                    "SELECT ACTION",
                    style = getPoppinsExtraBold12().copy(fontSize = 10.sp),
                    color = Color(0xFF64748B)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    availableActions.forEach { (action, label) ->
                        val isSelected = selectedAction == action
                        val actionColor = when (action) {
                            BookingUpdateType.CLOCK_IN -> Color(0xFF3498DB)
                            BookingUpdateType.START_SERVICE -> Color(0xFF10B981)
                            BookingUpdateType.COMPLETE_SERVICE -> Color(0xFF8B5CF6)
                            BookingUpdateType.CLOCK_OUT -> Color(0xFFF09103)
                            BookingUpdateType.CANCEL_SERVICE -> Color(0xFFEF4444)
                        }

                        Card(
                            onClick = { selectedAction = action },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) actionColor.copy(alpha = 0.1f) else Color.White
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) actionColor else Color(0xFFE2E8F0)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(actionColor.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            when (action) {
                                                BookingUpdateType.CLOCK_IN -> "🕐"
                                                BookingUpdateType.START_SERVICE -> "▶️"
                                                BookingUpdateType.COMPLETE_SERVICE -> "✅"
                                                BookingUpdateType.CLOCK_OUT -> "⏸️"
                                                BookingUpdateType.CANCEL_SERVICE -> "❌"
                                            },
                                            style = getPoppinsSemiBold16().copy(fontSize = 20.sp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            label,
                                            style = getPoppinsSemiBold14(),
                                            color = if (isSelected) actionColor else Color(0xFF1E293B)
                                        )
                                        Text(
                                            when (action) {
                                                BookingUpdateType.CLOCK_IN -> "Mark your arrival"
                                                BookingUpdateType.START_SERVICE -> "Begin the service"
                                                BookingUpdateType.COMPLETE_SERVICE -> "Mark as done"
                                                BookingUpdateType.CLOCK_OUT -> "Pause or leave"
                                                BookingUpdateType.CANCEL_SERVICE -> "Cancel this booking"
                                            },
                                            style = getPoppinsMedium12().copy(fontSize = 11.sp),
                                            color = Color(0xFF64748B)
                                        )
                                    }
                                }

                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(actionColor),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "✓",
                                            style = getPoppinsBold14(),
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Agent Remark TextField
                Text(
                    "AGENT REMARK (Optional)",
                    style = getPoppinsExtraBold12().copy(fontSize = 10.sp),
                    color = Color(0xFF64748B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                androidx.compose.material3.OutlinedTextField(
                    value = agentRemark,
                    onValueChange = { agentRemark = it },
                    placeholder = {
                        Text(
                            "Add any notes about this action...",
                            style = getPoppinsMedium12()
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF3498DB),
                        unfocusedIndicatorColor = Color(0xFFE2E8F0),
                        cursorColor = Color(0xFF3498DB)
                    ),
                    textStyle = getPoppinsMedium14(),
                    maxLines = 4
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF1F5F9)
                    )
                ) {
                    Text(
                        text = "Cancel",
                        style = getPoppinsSemiBold14(),
                        color = Color(0xFF64748B)
                    )
                }

                Button(
                    onClick = {
                        selectedAction?.let { action ->
                            viewModel.updateBooking(
                                bookingId = agentBooking.bookingId,
                                updateType = action,
                                agentRemark = agentRemark.ifBlank { "No remark" }
                            )
                        }
                    },
                    enabled = selectedAction != null &&
                              updateBookingState !is UpdateBookingUiState.Loading &&
                              agentBooking.serviceStatus == "AGENT_DEPLOYED",
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = selectedAction?.let {
                            when (it) {
                                BookingUpdateType.CLOCK_IN -> Color(0xFF3498DB)
                                BookingUpdateType.START_SERVICE -> Color(0xFF10B981)
                                BookingUpdateType.COMPLETE_SERVICE -> Color(0xFF8B5CF6)
                                BookingUpdateType.CLOCK_OUT -> Color(0xFFF09103)
                                BookingUpdateType.CANCEL_SERVICE -> Color(0xFFEF4444)
                            }
                        } ?: Color(0xFFE2E8F0),
                        disabledContainerColor = Color(0xFFE2E8F0)
                    )
                ) {
                    if (updateBookingState is UpdateBookingUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = if (agentBooking.serviceStatus != "AGENT_DEPLOYED") {
                                "Not Available"
                            } else {
                                selectedAction?.let {
                                    when (it) {
                                        BookingUpdateType.CLOCK_IN -> "Clock In"
                                        BookingUpdateType.START_SERVICE -> "Start"
                                        BookingUpdateType.COMPLETE_SERVICE -> "Complete"
                                        BookingUpdateType.CLOCK_OUT -> "Clock Out"
                                        BookingUpdateType.CANCEL_SERVICE -> "Cancel"
                                    }
                                } ?: "Select Action"
                            },
                            style = getPoppinsSemiBold14(),
                            color = if (selectedAction != null && agentBooking.serviceStatus == "AGENT_DEPLOYED")
                                Color.White
                            else
                                Color(0xFF94A3B8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CleaningBookingDetailsContent(
    agentBooking: AgentBooking,
    cleaningBooking: CleaningBookingModel,
    viewModel: EmployeeBookingDetailsViewModel,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showImageSourceDialog by remember { mutableStateOf(false) }
    val uploadedImageUrls = remember { mutableStateListOf<String>() }
    var imageByte by remember { mutableStateOf<ByteArray?>(null) }
    val scope = rememberCoroutineScope()
    val (snackBar, snackBarHostState) = rememberSnackBar()

    val cameraLauncher = rememberCameraLauncher { imageData ->
        imageData?.let {
            imageByte = it
            viewModel.uploadImage(it, generateImageFileName(it))
        }
    }

    val imagePickerLaunch = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { imageData ->
                imageByte = imageData
                viewModel.uploadImage(imageData, generateImageFileName(imageData))
            }
        }
    )

    val imageUploadState = viewModel.imageUploadState.collectAsStateWithLifecycle()
    val updateImagesState = viewModel.updateImagesState.collectAsStateWithLifecycle()

    LaunchedEffect(imageUploadState.value) {
        when (val uploadResult = imageUploadState.value) {
            is ImageUploadState.Success -> {
                if (!uploadedImageUrls.contains(uploadResult.url)) {
                    uploadedImageUrls.add(uploadResult.url)
                }
            }

            is ImageUploadState.Error -> {
                snackBar.showError(uploadResult.message)
            }

            else -> {}
        }
    }

    // Determine primary color based on cleaning type
    val primaryColor = when {
        cleaningBooking.cleaningType.name.contains(
            "Basic",
            ignoreCase = true
        ) -> Color(0xFF3498DB) // Blue
        cleaningBooking.cleaningType.name.contains(
            "Deep",
            ignoreCase = true
        ) -> Color(0xFF8B5CF6) // Purple
        else -> Color(0xFF3498DB)
    }

    // Header status color based on service status
    val statusColor = when (agentBooking.serviceStatus) {
        "REQUEST_PENDING", "REQUEST_RECEIVED" -> Color(0xFF94A3B8) // Gray - Pending
        "REQUEST_CONFIRMED" -> Color(0xFF3498DB) // Blue - Confirmed
        "AGENT_DEPLOYED" -> Color(0xFFF09103) // Orange - In Progress
        "JOB_COMPLETED" -> Color(0xFF10B981) // Green - Completed
        else -> Color(0xFF94A3B8) // Default gray
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFCBD5E0))
    ) {
        // Dynamic Header - Searching State
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(statusColor)
                .padding(top = 55.dp, start = 24.dp, end = 24.dp, bottom = 35.dp)
        ) {
            // Back button
            androidx.compose.material3.IconButton(
                onClick = goBack,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    "‹",
                    style = getPoppinsSemiBold16().copy(fontSize = 24.sp),
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Pulse Box with Icon
                Box(
                    modifier = Modifier
                        .size(85.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(
                            width = 2.dp,
                            color = Color.White.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    agentBooking.assignedAgent?.profilePictureUrl?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "Agent Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } ?: run {
                        Text("🧹", style = getPoppinsSemiBold16().copy(fontSize = 32.sp))
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    if (agentBooking.assignedAgent != null)
                        "${agentBooking.assignedAgent.firstName} ${agentBooking.assignedAgent.lastName}"
                    else
                        "Request Confirmed",
                    style = getPoppinsBold18(),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    if (agentBooking.assignedAgent != null)
                        "Cleaning Specialist"
                    else
                        "Matching you with a trusted pro...",
                    style = getPoppinsMedium12().copy(fontSize = 13.sp),
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 1. Subscription Plan Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Plan Badge
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (cleaningBooking.cleaningType.name.contains(
                                        "Basic",
                                        ignoreCase = true
                                    )
                                )
                                    Color(0xFFEBF8FF)
                                else
                                    Color(0xFFF3E8FF),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (cleaningBooking.cleaningType.name.contains(
                                        "Basic",
                                        ignoreCase = true
                                    )
                                )
                                    Color(0xFFBEE3F8)
                                else
                                    Color(0xFFE9D5FF),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            "${cleaningBooking.cleaningType.name.uppercase()} PLAN",
                            style = getPoppinsExtraBold12().copy(
                                fontSize = 11.sp,
                                letterSpacing = 0.5.sp
                            ),
                            color = primaryColor
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Title
                    Text(
                        "${cleaningBooking.apartmentType.name} • ${cleaningBooking.location.name}",
                        style = getPoppinsSemiBold14().copy(fontSize = 16.sp),
                        color = Color(0xFF0F172A)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Parse cleaning dates
                    val datesList = cleaningBooking.cleaningDates.split(",").map { it.trim() }
                    val startDate = datesList.firstOrNull()?.formatToReadableDate() ?: "N/A"
                    val endDate = datesList.lastOrNull()?.formatToReadableDate() ?: "N/A"

                    // Subscription Summary Grid
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
                            .clip(RoundedCornerShape(14.dp))
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            // Start Date
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color.White)
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "START DATE",
                                    style = getPoppinsBold14().copy(
                                        fontSize = 9.sp,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = Color(0xFF64748B)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    startDate,
                                    style = getPoppinsBold14().copy(fontSize = 13.sp),
                                    color = Color(0xFF0F172A)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(55.dp)
                                    .background(Color(0xFFE2E8F0))
                            )

                            // End Date
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color.White)
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "END DATE",
                                    style = getPoppinsBold14().copy(
                                        fontSize = 9.sp,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = Color(0xFF64748B)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    endDate,
                                    style = getPoppinsBold14().copy(fontSize = 13.sp),
                                    color = Color(0xFF0F172A)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFE2E8F0))
                        )

                        // Schedule Info (full width)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "TOTAL SCHEDULE",
                                style = getPoppinsBold14().copy(
                                    fontSize = 9.sp,
                                    letterSpacing = 0.5.sp
                                ),
                                color = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                "${datesList.size} ${if (datesList.size > 1) "Sessions" else "Session"} • ${cleaningBooking.cleaningTime.formatTime()}",
                                style = getPoppinsBold14().copy(fontSize = 13.sp),
                                color = Color(0xFF0F172A)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Location Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        "LOCATION DETAILS",
                        style = getPoppinsExtraBold12().copy(
                            fontSize = 10.sp,
                            letterSpacing = 0.5.sp
                        ),
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column {
                        Text(
                            "ADDRESS",
                            style = getPoppinsExtraBold12().copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.3.sp
                            ),
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            cleaningBooking.address,
                            style = getPoppinsSemiBold14().copy(fontSize = 13.sp),
                            color = Color(0xFF0F172A),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Property Photos Card
            if (cleaningBooking.customerImages.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            "PROPERTY PHOTOS",
                            style = getPoppinsExtraBold12().copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            ),
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(cleaningBooking.customerImages) { imageUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF3F3F5))
                                ) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Property Photo",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // 3. Property Photos Card
            if (cleaningBooking.employeeImages.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            "JOB DONE IMAGES",
                            style = getPoppinsExtraBold12().copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            ),
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(cleaningBooking.employeeImages) { imageUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF3F3F5))
                                ) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Property Photo",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
            // Employee Image Upload Section
            if (agentBooking.serviceStatus.equals("AGENT_DEPLOYED")) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            "UPLOAD JOB COMPLETION IMAGES",
                            style = getPoppinsExtraBold12().copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            ),
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        PhotoUploadSection(
                            title = "Photos of completed work",
                            ctaText = "Tap to Capture or Upload",
                            helperText = "Max 5 photos • High quality preferred",
                            onTap = {
                                showImageSourceDialog = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (uploadedImageUrls.isEmpty()) {
                            EmptyState(
                                "No Images Yet",
                                "Your captured images will be displayed here",
                                modifier = Modifier.padding(12.dp)
                            )
                        } else {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                ImageGrid(uploadedImageUrls, deleteImage = { image ->
                                    uploadedImageUrls.remove(image)
                                })

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        uploadedImageUrls.clear()
                                        viewModel.updateBookingImages(
                                            bookingId = agentBooking.bookingId,
                                            images = uploadedImageUrls.toList()
                                        )
                                    },
                                    enabled = uploadedImageUrls.isNotEmpty(),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF10B981),
                                        disabledContainerColor = Color(0xFFE2E8F0)
                                    )
                                ) {
                                    Text(
                                        "Submit Images (${uploadedImageUrls.size})",
                                        style = getPoppinsSemiBold14(),
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (showImageSourceDialog) {
                MediaSourceDialog(
                    onDismiss = { showImageSourceDialog = false },
                    title = "Upload Picture",
                    description = "Choose how you'd like to upload your picture:",
                    showCamera = true,
                    showGallery = true,
                    showDocument = false,
                    onCameraSelected = {
                        showImageSourceDialog = false
                        cameraLauncher.launch()
                    },
                    onGallerySelected = {
                        showImageSourceDialog = false
                        imagePickerLaunch.launch()
                    }
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun DeepCleaningBookingDetailsContent(
    agentBooking: AgentBooking,
    cleaningBooking: CleaningBookingModel,
    viewModel: EmployeeBookingDetailsViewModel,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Primary color for deep cleaning
    val primaryColor = Color(0xFFF29100)

    // Header status color based on service status
    val statusColor = when (agentBooking.serviceStatus) {
        "REQUEST_PENDING", "REQUEST_RECEIVED" -> Color(0xFF94A3B8) // Gray - Pending
        "REQUEST_CONFIRMED" -> Color(0xFF8B5CF6) // Purple - Confirmed (Deep Cleaning)
        "AGENT_DEPLOYED" -> Color(0xFFF29100) // Orange - In Progress
        "JOB_COMPLETED" -> Color(0xFF10B981) // Green - Completed
        else -> Color(0xFF94A3B8) // Default gray
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE5E7EB))
    ) {
        // Dynamic Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(statusColor)
                .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 30.dp)
        ) {
            // Back button
            androidx.compose.material3.IconButton(
                onClick = goBack,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    "‹",
                    style = getPoppinsSemiBold16().copy(fontSize = 24.sp),
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Agent Avatar
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .border(4.dp, Color.White, RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    agentBooking.assignedAgent?.profilePictureUrl?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "Agent Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } ?: run {
                        Text("👤", style = getPoppinsSemiBold16().copy(fontSize = 32.sp))
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    when (agentBooking.serviceStatus) {
                        "REQUEST_PENDING", "REQUEST_RECEIVED" -> "Request Received"
                        "REQUEST_CONFIRMED" -> "Request Confirmed"
                        "AGENT_DEPLOYED" -> "In Progress"
                        "JOB_COMPLETED" -> "Job Completed!"
                        else -> "Request Received"
                    },
                    style = getPoppinsBold18(),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                agentBooking.assignedAgent?.let { agent ->
                    Text(
                        when (agentBooking.serviceStatus) {
                            "REQUEST_PENDING", "REQUEST_RECEIVED" -> "Finding your cleaning specialist"
                            "REQUEST_CONFIRMED" -> "Agent assigned to your booking"
                            "AGENT_DEPLOYED" -> "${agent.firstName} ${agent.lastName} is on the way"
                            "JOB_COMPLETED" -> "${agent.firstName} ${agent.lastName} finished the service"
                            else -> "Finding your cleaning specialist"
                        },
                        style = getPoppinsMedium12().copy(fontSize = 13.sp),
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                } ?: run {
                    Text(
                        "Matching you with a trusted pro...",
                        style = getPoppinsMedium12().copy(fontSize = 13.sp),
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
            // 1. Property Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Type Badge
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFFF7ED), RoundedCornerShape(6.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "${cleaningBooking.cleaningType.name.uppercase()} • ${
                                cleaningBooking.serviceType.replace(
                                    "_",
                                    " "
                                )
                            }",
                            style = getPoppinsExtraBold12().copy(
                                fontSize = 11.sp,
                                letterSpacing = 0.3.sp
                            ),
                            color = primaryColor
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Property Main
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            cleaningBooking.apartmentType.name,
                            style = getPoppinsBold18().copy(fontSize = 16.sp),
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            cleaningBooking.location.name,
                            style = getPoppinsMedium12().copy(fontSize = 13.sp),
                            color = Color(0xFF64748B)
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    // Grid with full address
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "FULL ADDRESS",
                            style = getPoppinsBold14().copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            ),
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            cleaningBooking.address,
                            style = getPoppinsSemiBold14().copy(fontSize = 13.sp),
                            color = Color(0xFF0F172A),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Schedule Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        "SCHEDULED TIMING",
                        style = getPoppinsBold14().copy(fontSize = 10.sp, letterSpacing = 0.5.sp),
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Parse cleaning dates
                    val datesList = cleaningBooking.cleaningDates.split(",").map { it.trim() }
                    val cleaningDate = datesList.firstOrNull()?.formatToReadableDate() ?: "N/A"

                    // Schedule Display
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(14.dp))
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("📅", style = getPoppinsSemiBold16().copy(fontSize = 20.sp))

                            Column {
                                Text(
                                    "$cleaningDate • ${cleaningBooking.cleaningTime.formatTime()}",
                                    style = getPoppinsBold14().copy(fontSize = 13.sp),
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    "Single Session Service",
                                    style = getPoppinsMedium12().copy(fontSize = 11.sp),
                                    color = Color(0xFF64748B)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Customer Images Card
            if (cleaningBooking.customerImages.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            "PROPERTY PHOTOS (${cleaningBooking.customerImages.size})",
                            style = getPoppinsBold14().copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            ),
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(cleaningBooking.customerImages) { imageUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(75.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF3F3F5))
                                ) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Property Photo",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // 3. Customer Images Card
            if (cleaningBooking.employeeImages.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            "PROPERTY PHOTOS (${cleaningBooking.customerImages.size})",
                            style = getPoppinsBold14().copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            ),
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(cleaningBooking.employeeImages) { imageUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(75.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF3F3F5))
                                ) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Property Photo",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // 4. Payment Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    "PAYMENT STATUS",
                                    style = getPoppinsBold14().copy(
                                        fontSize = 10.sp,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = Color(0xFF64748B)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    if (cleaningBooking.paymentStatus == "PAYMENT_COMPLETED")
                                        "✓ PAYMENT_COMPLETED"
                                    else
                                        cleaningBooking.paymentStatus.replace("_", " "),
                                    style = getPoppinsBold14().copy(fontSize = 12.sp),
                                    color = Color(0xFF10B981)
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    "TOTAL AMOUNT",
                                    style = getPoppinsBold14().copy(
                                        fontSize = 10.sp,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = Color(0xFF64748B)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "₦${cleaningBooking.amount.formatBalance()}",
                                    style = getPoppinsBold18(),
                                    color = Color(0xFF0F172A)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun ToiletBookingDetailsContent(
    agentBooking: AgentBooking,
    toiletBooking: ToiletBookingModel,
    viewModel: EmployeeBookingDetailsViewModel,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Determine status color based on service status
    val statusColor = when (agentBooking.serviceStatus) {
        "REQUEST_PENDING", "REQUEST_RECEIVED" -> Color(0xFF94A3B8) // Gray - Pending
        "REQUEST_CONFIRMED" -> Color(0xFF3498DB) // Blue - Confirmed
        "AGENT_DEPLOYED" -> Color(0xFFF09103) // Orange - In Progress
        "JOB_COMPLETED" -> Color(0xFF10B981) // Green - Completed
        else -> Color(0xFF94A3B8) // Default gray
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // Hero Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(statusColor)
                .padding(vertical = 25.dp, horizontal = 20.dp)
        ) {
            // Back button
            androidx.compose.material3.IconButton(
                onClick = goBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    "‹",
                    style = getPoppinsSemiBold16().copy(fontSize = 24.sp),
                    color = Color.White
                )
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
                    agentBooking.assignedAgent?.profilePictureUrl?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "Agent Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                agentBooking.assignedAgent?.let { agent ->
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
                    value = agentBooking.paymentStatus ?: "Pending",
                    color = if (agentBooking.paymentStatus == "PAYMENT_COMPLETED") Color(
                        0xFF27AE60
                    ) else Color(0XFFF09103),
                    modifier = Modifier.weight(1f)
                )
                StatusBadge(
                    label = "Service",
                    value = when (agentBooking.serviceStatus) {
                        "REQUEST_PENDING" -> "Pending"
                        "REQUEST_RECEIVED" -> "Received"
                        "REQUEST_CONFIRMED" -> "Confirmed"
                        "AGENT_DEPLOYED" -> "In Progress"
                        "JOB_COMPLETED" -> "Completed"
                        else -> agentBooking.serviceStatus ?: "Pending"
                    },
                    color = statusColor,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Agent Card - Only show if agent is assigned
            agentBooking.assignedAgent?.let { agent ->
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoItem("Company", toiletBooking.companyName, Modifier.weight(1f))
                    InfoItem(
                        "Duration",
                        "${toiletBooking.toiletEstimate.numberOfDays} Day Event",
                        Modifier.weight(1f)
                    )
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoItem(
                            "Standard Toilets",
                            "${toiletBooking.numberOfStandardToilet} Units",
                            Modifier.weight(1f)
                        )
                        InfoItem(
                            "VIP Toilets",
                            "${toiletBooking.numberOfVipToilet} Units",
                            Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoItem(
                            "Total Guests",
                            "${toiletBooking.toiletEstimate.totalNumberOfGuests} People",
                            Modifier.weight(1f)
                        )
                        InfoItem(
                            "Type",
                            if (toiletBooking.typeOfEvent.isEmpty()) "Unspecified" else toiletBooking.typeOfEvent,
                            Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Location Photos - Combine both lists and filter valid URLs
            val imageUrls =
                (toiletBooking.pictureOfEventLocation + toiletBooking.pictureOfToiletPlacement)
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
                PriceRow(
                    "Base Amount",
                    "₦${(toiletBooking.toiletEstimate.totalAmount).formatBalance()}"
                )
                Spacer(modifier = Modifier.height(8.dp))
                PriceRow(
                    "Tax(0.75%)",
                    "₦${abs(toiletBooking.toiletEstimate.totalAmount - (agentBooking.amountPaid ?: 0.0)).formatBalance()}",
                    Color(0xFF27AE60)
                )
                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider(
                    color = Color(0xFFE2E8F0),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Paid", style = getPoppinsBold18(), color = Color(0xFFF09103))
                    Text(
                        "₦${agentBooking.amountPaid?.formatBalance()}",
                        style = getPoppinsBold18(),
                        color = Color(0xFFF09103)
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun SepticBookingDetailsContent(
    agentBooking: AgentBooking,
    septicBooking: SepticBookingModel,
    viewModel: EmployeeBookingDetailsViewModel,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Determine status color based on service status
    val statusColor = when (agentBooking.serviceStatus) {
        "REQUEST_PENDING", "REQUEST_RECEIVED" -> Color(0xFF64748B) // Gray - Pending
        "REQUEST_CONFIRMED" -> Color(0xFF3498DB) // Blue - Confirmed
        "AGENT_DEPLOYED" -> Color(0xFFF09103) // Orange - In Progress
        "JOB_COMPLETED" -> Color(0xFF10B981) // Green - Completed
        else -> Color(0xFF64748B) // Default gray
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFF))
    ) {
        // Hero Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(statusColor)
                .padding(vertical = 25.dp, horizontal = 24.dp)
        ) {
            // Back button
            androidx.compose.material3.IconButton(
                onClick = goBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    "‹",
                    style = getPoppinsSemiBold16().copy(fontSize = 24.sp),
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Agent Avatar
                Box(
                    modifier = Modifier
                        .size(85.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(3.dp, Color.White, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    agentBooking.assignedAgent.profilePictureUrl.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "Agent Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } ?: run {
                        // Pending state - show search icon
                        Text("🔍", style = getPoppinsSemiBold16().copy(fontSize = 32.sp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                agentBooking.assignedAgent.let { agent ->
                    Text(
                        "${agent.firstName} ${agent.lastName}",
                        style = getPoppinsSemiBold14().copy(fontSize = 18.sp),
                        color = Color.White
                    )
                } ?: run {
                    Text(
                        "Finding Your Agent...",
                        style = getPoppinsSemiBold14().copy(fontSize = 18.sp),
                        color = Color.White
                    )
                }

                Text(
                    "Septic Service Expert",
                    style = getPoppinsMedium12(),
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Quick Status Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatusBadge(
                    label = "Payment",
                    value = agentBooking.paymentStatus ?: "Pending",
                    color = if (agentBooking.paymentStatus == "PAYMENT_COMPLETED") Color(
                        0xFF10B981
                    ) else Color(0xFF64748B),
                    modifier = Modifier.weight(1f)
                )
                StatusBadge(
                    label = "Service",
                    value = when (agentBooking.serviceStatus) {
                        "REQUEST_PENDING" -> "Pending"
                        "REQUEST_RECEIVED" -> "Received"
                        "REQUEST_CONFIRMED" -> "Confirmed"
                        "AGENT_DEPLOYED" -> "In Progress"
                        "JOB_COMPLETED" -> "Completed"
                        else -> agentBooking.serviceStatus ?: "Pending"
                    },
                    color = statusColor,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hero Metrics Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Tank Capacity",
                            style = getPoppinsMedium12(),
                            color = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "${septicBooking.liter} Liters",
                            style = getPoppinsBold18().copy(fontSize = 22.sp),
                            color = Color(0xFF111827)
                        )
                    }

                    // Vertical divider
                    Box(
                        modifier = Modifier
                            .width(5.dp)
                            .height(50.dp)
                            .background(statusColor, RoundedCornerShape(4.dp))
                    )

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            "Total Amount",
                            style = getPoppinsMedium12(),
                            color = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "₦${agentBooking.amountPaid.formatBalance()}",
                            style = getPoppinsBold18().copy(fontSize = 22.sp),
                            color = statusColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Information Grid
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Service Requested By (Full width)
                    InfoItem(
                        "Service Requested By",
                        septicBooking.fullName,
                        Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Date and Time (Grid)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoItem(
                            "Excavation Date",
                            septicBooking.dateOfExcavation.formatToReadableDate(),
                            Modifier.weight(1f)
                        )
                        InfoItem(
                            "Excavation Time",
                            septicBooking.timeOfExcavation.formatTime(),
                            Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Service Address (Full width)
                    InfoItem("Service Address", septicBooking.address, Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(18.dp))

                    // Special Note (Full width)
                    InfoItem(
                        "Special Note",
                        septicBooking.specialNote.ifEmpty { "No special instructions" },
                        Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun PestControlBookingDetailsContent(
    customerBooking: AgentBooking,
    pestControlBooking: PestControlBookingModel,
    viewModel: EmployeeBookingDetailsViewModel,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Determine status color based on service status
    val statusColor = when (customerBooking.serviceStatus) {
        "REQUEST_PENDING", "REQUEST_RECEIVED" -> Color(0xFF64748B) // Gray - Pending
        "REQUEST_CONFIRMED" -> Color(0xFF3498DB) // Blue - Confirmed
        "AGENT_DEPLOYED" -> Color(0xFFF29100) // Orange - In Progress
        "JOB_COMPLETED" -> Color(0xFF10B981) // Green - Completed
        else -> Color(0xFF64748B) // Default gray
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE2E8F0))
    ) {
        // Dynamic Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(statusColor)
                .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 30.dp)
        ) {
            // Back button
            androidx.compose.material3.IconButton(
                onClick = goBack,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    "‹",
                    style = getPoppinsSemiBold16().copy(fontSize = 24.sp),
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Searching Box / Agent Avatar
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .border(
                            width = 2.dp,
                            color = Color.White.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    customerBooking.assignedAgent?.profilePictureUrl?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "Agent Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } ?: run {
                        Text("🐜", style = getPoppinsSemiBold16().copy(fontSize = 32.sp))
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                customerBooking.assignedAgent?.let { agent ->
                    Text(
                        "${agent.firstName} ${agent.lastName}",
                        style = getPoppinsBold18(),
                        color = Color.White
                    )
                } ?: run {
                    Text(
                        "Finding Expert...",
                        style = getPoppinsBold18(),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    if (customerBooking.assignedAgent == null)
                        "Assigning your ${pestControlBooking.preorder.service.serviceName} specialist"
                    else
                        "Pest Control Specialist",
                    style = getPoppinsMedium12().copy(fontSize = 13.sp),
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 1. Service Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            pestControlBooking.preorder.service.serviceName,
                            style = getPoppinsBold18().copy(fontSize = 16.sp),
                            color = Color(0xFF0F172A)
                        )
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFF1F5F9), RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "${pestControlBooking.preorder.numberOfRooms} Rooms",
                                style = getPoppinsBold14().copy(fontSize = 12.sp),
                                color = Color(0xFFF29100)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Add-on Tags
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (pestControlBooking.isHotFogging) {
                            AddOnTag("✨ Hot Fogging Active", isActive = true)
                        }
                        if (pestControlBooking.customerOwnVehicle && pestControlBooking.numberOfVehicles > 0) {
                            AddOnTag(
                                "🚗 ${pestControlBooking.numberOfVehicles} Vehicles Included",
                                isActive = true
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Schedule & Location Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        "SCHEDULE & LOCATION",
                        style = getPoppinsExtraBold12().copy(
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        ),
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Property Address (full width)
                    Column {
                        Text(
                            "PROPERTY ADDRESS",
                            style = getPoppinsSemiBold14().copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.3.sp
                            ),
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            pestControlBooking.address,
                            style = getPoppinsSemiBold14().copy(fontSize = 13.sp),
                            color = Color(0xFF0F172A),
                            lineHeight = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    // Schedule Row - Two date boxes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Inspection Date Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    "INSPECTION",
                                    style = getPoppinsExtraBold12().copy(
                                        fontSize = 9.sp,
                                        letterSpacing = 0.4.sp
                                    ),
                                    color = Color(0xFFF29100)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    "${pestControlBooking.inspectionDate.formatToReadableDate()} • ${pestControlBooking.inspectionTime.formatTime()}",
                                    style = getPoppinsBold14().copy(fontSize = 12.sp),
                                    color = Color(0xFF0F172A),
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        // Service Date Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    "SERVICE DATE",
                                    style = getPoppinsExtraBold12().copy(
                                        fontSize = 9.sp,
                                        letterSpacing = 0.4.sp
                                    ),
                                    color = Color(0xFFF29100)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    "${pestControlBooking.serviceDate.formatToReadableDate()} • ${pestControlBooking.serviceTime.formatTime()}",
                                    style = getPoppinsBold14().copy(fontSize = 12.sp),
                                    color = Color(0xFF0F172A),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Media & Notes Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        "MEDIA & PROPERTY PHOTOS",
                        style = getPoppinsExtraBold12().copy(
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        ),
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Image Strip
                    if (pestControlBooking.images.isNotEmpty()) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(pestControlBooking.images) { imageUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF3F3F5))
                                ) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Property Photo",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            "No photos uploaded",
                            style = getPoppinsMedium12().copy(
                                fontSize = 13.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            ),
                            color = Color(0xFF64748B)
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    // Additional Notes
                    Text(
                        "ADDITIONAL NOTES",
                        style = getPoppinsExtraBold12().copy(
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        ),
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        pestControlBooking.extraNote.ifEmpty { "No special notes provided." },
                        style = getPoppinsMedium12().copy(
                            fontSize = 13.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        ),
                        color = Color(0xFF64748B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Financial Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        "PAYMENT SUMMARY",
                        style = getPoppinsExtraBold12().copy(
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        ),
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Base Service Fee
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Base Service Fee",
                            style = getPoppinsMedium12().copy(fontSize = 13.sp),
                            color = Color(0xFF64748B)
                        )
                        Text(
                            "₦${pestControlBooking.preorder.amount.formatBalance()}",
                            style = getPoppinsSemiBold14().copy(fontSize = 13.sp),
                            color = Color(0xFF64748B)
                        )
                    }

                    if (pestControlBooking.isHotFogging) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // Base Service Fee
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Hot Fogging Fee",
                                style = getPoppinsMedium12().copy(fontSize = 13.sp),
                                color = Color(0xFF64748B)
                            )
                            Text(
                                "₦${30000.00.formatBalance()}",
                                style = getPoppinsSemiBold14().copy(fontSize = 13.sp),
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    if (pestControlBooking.customerOwnVehicle && pestControlBooking.preorder.service.serviceName.equals(
                            "Bed Bug Control"
                        )
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // Base Service Fee
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Bed Bug Control Fee(${pestControlBooking.numberOfVehicles} Vehicles)",
                                style = getPoppinsMedium12().copy(fontSize = 13.sp),
                                color = Color(0xFF64748B)
                            )
                            Text(
                                "₦${(pestControlBooking.numberOfVehicles * 30000.00).formatBalance()}",
                                style = getPoppinsSemiBold14().copy(fontSize = 13.sp),
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    if (pestControlBooking.customerOwnVehicle && pestControlBooking.preorder.service.serviceName.equals(
                            "Tick Control"
                        )
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // Base Service Fee
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Tick Control Fee(${pestControlBooking.numberOfVehicles} Vehicles)",
                                style = getPoppinsMedium12().copy(fontSize = 13.sp),
                                color = Color(0xFF64748B)
                            )
                            Text(
                                "₦${(pestControlBooking.numberOfVehicles * 12500.00).formatBalance()}",
                                style = getPoppinsSemiBold14().copy(fontSize = 13.sp),
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Tax (0.75%)",
                            style = getPoppinsMedium12().copy(fontSize = 13.sp),
                            color = Color(0xFF64748B)
                        )
                        Text(
                            "₦${((pestControlBooking.preorder.amount + 30000) * 0.075).formatBalance()}",
                            style = getPoppinsSemiBold14().copy(fontSize = 13.sp),
                            color = Color(0xFF64748B)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(
                        color = Color(0xFFE2E8F0),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Total Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "✓ PAID TOTAL",
                            style = getPoppinsBold14(),
                            color = Color(0xFF10B981)
                        )
                        Text(
                            "₦${customerBooking.amountPaid?.formatBalance()}",
                            style = getPoppinsBold18(),
                            color = Color(0xFF0F172A)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun AddOnTag(text: String, isActive: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = if (isActive) Color(0xFFFFF7ED) else Color(0xFFF1F5F9),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = if (isActive) 1.dp else 0.dp,
                color = if (isActive) Color(0xFFFFEDD5) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text,
            style = getPoppinsBold12().copy(fontSize = 11.sp),
            color = if (isActive) Color(0xFFF29100) else Color(0xFF475569)
        )
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
            Text(value.replace("_", " "), style = getPoppinsBold12(), color = color)
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
    viewModel: EmployeeBookingDetailsViewModel,
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
                            color = if (starNumber <= rating) Color(0xFFFBBF24) else Color(
                                0xFFE2E8F0
                            )
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
                placeholder = {
                    Text(
                        "Tell us about your experience...",
                        style = getPoppinsMedium12()
                    )
                },
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
