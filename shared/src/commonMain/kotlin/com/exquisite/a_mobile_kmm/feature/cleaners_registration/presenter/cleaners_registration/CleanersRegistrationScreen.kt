package com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CleanersRegistrationScreen(
    viewModel: CleanersRegistrationViewModel=koinViewModel<CleanersRegistrationViewModel>(),
    modifier: Modifier = Modifier
) {
    Text("Cleaners Registration Screen")
}
