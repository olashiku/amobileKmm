package com.exquisite.a_mobile_kmm.feature.auth.presenter.otp

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.Otp
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.a_mobile_kmm.core.screen_components.OtpTextField
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun OtpScreen(
    otp: Otp, goBack: () -> Unit,
    goToCreatePassword: (String) -> Unit, modifier: Modifier = Modifier,
    viewModel: OtpViewModel = koinViewModel<OtpViewModel>()
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    val otpState = viewModel.otpMutableStateFlow.collectAsStateWithLifecycle()
    val validateOtpState = viewModel.validateOtpStateFlow.collectAsStateWithLifecycle()
    var otpText by remember { mutableStateOf("") }

    when (val result = validateOtpState.value) {

        is VerifyOtpState.Loading -> {
            LoadingDialog(true)
        }

        is VerifyOtpState.Error -> {
            println("errorMessage ${result.message}")
            snackBar.showError("Error: ${result.message}")
        }

        is VerifyOtpState.Success -> {
            viewModel.reset()
            goToCreatePassword(otpText)
        }

        is VerifyOtpState.Idle -> {
            LoadingDialog(false)
        }
    }

    when (val result = otpState.value) {

        is OtpState.Loading -> {
            LoadingDialog(true)
        }

        is OtpState.Error -> {
            println("errorMessage ${result.message}")
            snackBar.showError("Error: ${result.message}")
        }

        is OtpState.Success -> {
            snackBar.showSuccess("Success: ${result.data}")
        }

        is OtpState.Idle -> {
            LoadingDialog(false)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.reset()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
            color = Color.White
        )
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.align(Alignment.TopStart)
                .padding(24.dp).clickable {
                    goBack()
                }
        ) {
            Image(
                painter = painterResource(Res.drawable.back_arrow),
                contentDescription = "Back arrow"
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "OTP",
                style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "An OTP was sent to your email,\n kindly check your mail box to complete this step",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))

            // OTP Input Section
            OtpTextField(
                {
                    otpText = it
                    if (it.length == 6) {
                        viewModel.validateOtp(otp.uniqueRef, otpText)
                    }
                })
            Spacer(modifier = Modifier.height(32.dp))

            // Resend Section
            Text(
                text = "Didn’t receive OTP?",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Resend Code",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFF09103),
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    viewModel.resendOtp(otp.uniqueRef)
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            PrimaryButton("Confirm", {
                viewModel.validateOtp(otp.uniqueRef, otpText)
            })
            Spacer(modifier = Modifier.height(32.dp))
        }


        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}