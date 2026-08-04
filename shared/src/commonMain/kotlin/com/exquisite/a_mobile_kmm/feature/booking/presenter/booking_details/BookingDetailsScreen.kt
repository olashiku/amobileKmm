package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBooking
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookingDetailsScreen(
    customerBooking: CustomerBooking,
    viewModel: BookingDetailsViewModel = koinViewModel<BookingDetailsViewModel>(),
    modifier: Modifier = Modifier
) {

    val state = viewModel.bookingDetailsState.collectAsStateWithLifecycle()

    when(val result = state.value){
        is BookingDetailsState.Idle ->{

        }
        is BookingDetailsState.Loading ->{

        }
        is BookingDetailsState.Error ->{
            Text(text = result.message)
        }
        is BookingDetailsState.CleaningBookingSuccess ->{

        }
        is BookingDetailsState.SepticBookingSuccess ->{

        }
        is BookingDetailsState.PestControlBookingSuccess ->{

        }
        is BookingDetailsState.ToiletBookingSuccess ->{

        }
        is BookingDetailsState.RateReviewSuccess ->{

        }
    }


    LaunchedEffect(Unit){
        when(customerBooking.bookingType){
            "TOILET" ->{
                viewModel.loadToiletBooking(customerBooking.bookingId)
            }
            "SEPTIC_REQUEST" ->{
                viewModel.loadSepticBooking(customerBooking.bookingId)
            }
            "PEST_CONTROL" ->{
                viewModel.loadPestControlBooking(customerBooking.bookingId)
            }
            "BASIC_CLEANING","DEEP_CLEANING" ->{
                viewModel.loadCleaningBooking(customerBooking.bookingId)
            }
        }
    }

}
