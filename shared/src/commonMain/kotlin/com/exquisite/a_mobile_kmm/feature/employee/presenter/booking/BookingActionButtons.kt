package com.exquisite.a_mobile_kmm.feature.employee.presenter.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.BookingUpdateType
import org.koin.compose.viewmodel.koinViewModel

/**
 * Reusable booking action buttons component
 * Shows Clock In, Clock Out, Complete Service buttons with proper state handling
 */
@Composable
fun BookingActionButtons(
    bookingId: Int,
    onSuccess: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateBookingViewModel = koinViewModel()
) {
    val updateState = viewModel.updateState.collectAsStateWithLifecycle()

    // Handle success/error states
    LaunchedEffect(updateState.value) {
        when (val state = updateState.value) {
            is UpdateBookingUiState.Success -> {
                onSuccess(state.message)
           //     viewModel.resetState()
            }
            else -> { /* Handle other states in UI */ }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Show loading or error state
        when (val state = updateState.value) {
            is UpdateBookingUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Updating booking...", style = getPoppinsSemiBold13())
                }
            }
            is UpdateBookingUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message,
                        style = getPoppinsSemiBold13(),
                        color = Color.Red
                    )
                }
            }
            else -> {
                // Show action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Clock In Button
                    Button(
                        onClick = {
                            viewModel.updateBooking(
                                bookingId = bookingId,
                                updateType = BookingUpdateType.CLOCK_IN,
                                agentRemark = "Starting service, all equipment checked"
                            )
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF10B981)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Clock In", style = getPoppinsSemiBold13())
                    }

                    // Clock Out Button
                    OutlinedButton(
                        onClick = {
                            viewModel.updateBooking(
                                bookingId = bookingId,
                                updateType = BookingUpdateType.CLOCK_OUT,
                                agentRemark = "Service completed successfully"
                            )
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Clock Out", style = getPoppinsSemiBold13())
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Complete Service Button
                Button(
                    onClick = {
                        viewModel.updateBooking(
                            bookingId = bookingId,
                            updateType = BookingUpdateType.COMPLETE_SERVICE,
                            agentRemark = "All tasks completed, customer satisfied"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F172A)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Complete Service", style = getPoppinsSemiBold13())
                }
            }
        }
    }
}
