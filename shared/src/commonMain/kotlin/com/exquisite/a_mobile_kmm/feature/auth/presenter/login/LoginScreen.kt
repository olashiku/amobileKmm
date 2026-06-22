package com.exquisite.a_mobile_kmm.feature.auth.presenter.login

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.auth_logo
import amobilekmm.shared.generated.resources.email_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.screenUtils.Strings
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.PrimaryButton
import com.exquisite.dripp.core.components.PrimaryCheckBox
import com.exquisite.dripp.core.components.ValidatedPasswordTextField
import com.exquisite.dripp.core.components.ValidatedTextField
import com.exquisite.dripp.core.components.rememberSnackBar
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    goToDashboard: () -> Unit,
    goToForgotPassword: () -> Unit,
    goToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {

    val emailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }
    val passwordValidator = remember {
        FieldValidator(
            ValidationHelper::validatePassword
        )
    }

    val loginState = viewModel.loginState.collectAsStateWithLifecycle()
    val rememberMeState = viewModel.rememberMeState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()

    when (val result = loginState.value) {
        is LoginState.Loading -> {
            LoadingDialog(true)
        }

        is LoginState.Error -> {
            snackBar.showError("Error: ${result.message}")
        }

        is LoginState.Success -> {
            snackBar.showSuccess("welcome")

            goToDashboard.invoke()
        }

        is LoginState.Idle -> {
            LoadingDialog(false)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
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
            Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(5.dp))
            Image(
                painter = painterResource(Res.drawable.auth_logo),
                contentDescription = "logo"
            )
            Spacer(modifier = modifier.height(29.dp))
            Text(
                text = "Welcome back", style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF232323), textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = "Login to your account\n using email or social networks",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(24.dp))
            ValidatedTextField(
                labelText = Strings.Auth.EMAIL,
                placeHolder = Strings.Auth.ENTER_EMAIL,
                fieldValidator = emailValidator,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                leadingIconRes = Res.drawable.email_icon
            )
            Spacer(modifier = Modifier.height(15.dp))
            ValidatedPasswordTextField(
                labelText = Strings.Auth.PASSWORD,
                placeHolder = Strings.Auth.ENTER_PASSWORD,
                fieldValidator = passwordValidator
            )
            Spacer(modifier = Modifier.height(15.dp))
            PrimaryButton("Login", {
                val isEmailValid = emailValidator.forceValidation()
                val isPasswordValid = passwordValidator.forceValidation()

                if (isEmailValid && isPasswordValid) {
                    viewModel.login(
                        emailValidator.value.value,
                        passwordValidator.value.value
                    )
                } else {
                    snackBar.showError("Please fill in all fields correctly")
                }
            })
            Spacer(modifier = Modifier.height(15.dp))
            RememberMeAndForgotPassword(
                rememberMe = rememberMeState.value,
                onRememberMeChanged = { isChecked ->
                    viewModel.saveRememberMe(isChecked)
                },
                goToForgotPassword = goToForgotPassword
            )
            Spacer(modifier = Modifier.height(40.dp))
            orSignup()
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF252525))) {
                        append(Strings.Auth.NO_ACCOUNT)
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFF09103))) {
                        append(Strings.Auth.SIGNUP)
                    }
                }, textAlign = TextAlign.Center,

                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).clickable {
                    goToSignUp()
                }
            )

        }

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp)
        )

    }
}

@Composable
fun orSignup(modifier:Modifier =Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically){
    HorizontalDivider(modifier.weight(1f).height(1.dp))
      Spacer(modifier = modifier.width(10.dp))
        Text(
            text = "Or Signup",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF7C7C7C),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.width(10.dp))
        HorizontalDivider(modifier.weight(1f).height(1.dp))
    }
}

@Composable
fun RememberMeAndForgotPassword(
    rememberMe: Boolean,
    onRememberMeChanged: (Boolean) -> Unit,
    goToForgotPassword: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        PrimaryCheckBox(
            Strings.Auth.REMEMBER_ME,
            rememberMe,
            onRememberMeChanged
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = Strings.Auth.FORGOT_PASSWORD,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFF09103),
            modifier = Modifier.clickable {
                goToForgotPassword()
            }
        )
    }
}