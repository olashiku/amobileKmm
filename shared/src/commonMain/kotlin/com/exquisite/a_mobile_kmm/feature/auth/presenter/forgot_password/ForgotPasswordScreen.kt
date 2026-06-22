package com.exquisite.a_mobile_kmm.feature.auth.presenter.forgot_password

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.Strings
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.PrimaryButton
import com.exquisite.dripp.core.components.ValidatedTextField
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ForgotPasswordScreen(
    goBack: () -> Unit,
    goToVerifyCode: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    forgotPasswordViewModel: ForgotPasswordViewModel = koinViewModel<ForgotPasswordViewModel>()
) {

    val forgotPasswordState =
        forgotPasswordViewModel.forgotPasswordState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var uniqueRef by remember { mutableStateOf("") }

    val emailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }

    DisposableEffect(Unit){
        onDispose{
            forgotPasswordViewModel.reset()
        }
    }

    when (val result = forgotPasswordState.value) {
        is ForgotPasswordState.Idle -> {
            LoadingDialog(false)
        }

        is ForgotPasswordState.Loading -> {
            LoadingDialog(true)
        }

        is ForgotPasswordState.Success -> {
            uniqueRef = result.uniqueRef
            goToVerifyCode(uniqueRef, emailValidator.value.value, "ForgotPassword")
        }

        is ForgotPasswordState.Error -> {
            snackBar.showError("Error: ${result.message}")
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
            modifier = modifier.align(Alignment.TopStart)
                .padding(20.dp).clickable {
                    goBack()
                }
        ) {
            Image(
                painter = painterResource(Res.drawable.back_arrow),
                contentDescription = "logo"
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = modifier.height(19.dp))
            Text(
                text = "Forgot password", style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF232323), textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = "Please enter your email to reset the password",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(24.dp))
            ValidatedTextField(
                labelText = Strings.Auth.EMAIL,
                placeHolder = Strings.Auth.ENTER_EMAIL,
                fieldValidator = emailValidator,
                imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.height(15.dp))
            PrimaryButton("Reset password", {
                val isEmailValid = emailValidator.forceValidation()
                if (isEmailValid) {
                    forgotPasswordViewModel.invoke(emailValidator.value.value)
                }
            })
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