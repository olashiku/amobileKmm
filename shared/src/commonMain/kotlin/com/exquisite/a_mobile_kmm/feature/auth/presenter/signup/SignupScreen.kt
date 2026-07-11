package com.exquisite.a_mobile_kmm.feature.auth.presenter.signup

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryCheckBox
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.dripp.core.components.rememberSnackBar
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignupScreen(
    goBack: () -> Unit,
    goToOtp: (String, String,String) -> Unit,
    goToTerms: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = koinViewModel<SignupViewModel>()
) {
    val persistedFormData by viewModel.persistedFormData.collectAsStateWithLifecycle()
    val isTermsClicked = remember { mutableStateOf(false) }

    val firstNameValidator = remember {
        FieldValidator(
            ValidationHelper::validateFirstName
        )
    }

    val lastNameValidator = remember {
        FieldValidator(
            ValidationHelper::validateLastName
        )
    }

    val phoneValidator = remember {
        FieldValidator(
            ValidationHelper::validatePhoneNumber
        )
    }

    val emailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }

    val signUpState = viewModel._registrationState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()

    when (val result = signUpState.value) {
        is RegisterState.Loading -> {
            LoadingDialog(true)
        }

        is RegisterState.Error -> {
            snackBar.showError("Error: ${result.message}")
        }

        is RegisterState.Success -> {
            goToOtp.invoke(result.uniqueRef, emailValidator.value.value,"Signup")
        }

        is RegisterState.Idle -> {
            LoadingDialog(false)
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }


    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
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
                text = "Create New Account",
                style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Set up your email and password\n or register with your social network account",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Form Section
            ValidatedTextField(
                labelText = "First Name",
                placeHolder = "Enter first name",
                fieldValidator = firstNameValidator,
                defaultText = persistedFormData.firstName,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                leadingIconRes = Res.drawable.email_icon
            )
            Spacer(modifier = Modifier.height(20.dp))
            ValidatedTextField(
                labelText = "Last Name",
                placeHolder = "Enter last name",
                fieldValidator = lastNameValidator,
                defaultText = persistedFormData.lastName,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                leadingIconRes = Res.drawable.email_icon
            )
            Spacer(modifier = Modifier.height(20.dp))
            ValidatedTextField(
                labelText = "Email",
                placeHolder = "Enter email",
                fieldValidator = emailValidator,
                defaultText = persistedFormData.email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                leadingIconRes = Res.drawable.email_icon
            )
            Spacer(modifier = Modifier.height(20.dp))
            ValidatedTextField(
                labelText = "Mobile number",
                placeHolder = "Enter mobile number",
                fieldValidator = phoneValidator,
                defaultText = persistedFormData.phone,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
                leadingIconRes = Res.drawable.email_icon
            )

            // Terms Section
            Spacer(modifier = Modifier.height(24.dp))
            TermsAndConditions({
                goToTerms()
            }, { value ->
                isTermsClicked.value = value
            })
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton("Signup", {
                val isFirstNameValid = firstNameValidator.forceValidation()
                val isLastNAmeValid = lastNameValidator.forceValidation()
                val isEmailValid = emailValidator.forceValidation()
                val isPhoneValid = phoneValidator.forceValidation()

                if (isFirstNameValid && isLastNAmeValid && isEmailValid && isPhoneValid && isTermsClicked.value) {
                    // Save form data before navigating
                    viewModel.saveFormData(
                        firstName = firstNameValidator.value.value,
                        lastName = lastNameValidator.value.value,
                        email = emailValidator.value.value,
                        phone = phoneValidator.value.value
                    )

                    viewModel.register(
                        emailValidator.value.value,
                        firstNameValidator.value.value,
                        lastNameValidator.value.value,
                        phoneValidator.value.value
                    )
                } else {
                    snackBar.showError("Kindly accept the terms and conditions and complete the form")
                }
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

@Composable
fun TermsAndConditions(goToTermsAndConditions: () -> Unit, isTermsClicked: (Boolean) -> Unit) {
    val isChecked = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        PrimaryCheckBox(
            {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF232323))) {
                            append("I Agree with ")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFFF09103))) {
                            append("Terms & Condition ")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFF09103),
                    modifier = Modifier.clickable {
                        goToTermsAndConditions()
                    }
                )
            },
            isChecked.value,
            { checked ->
                isChecked.value = checked
                isTermsClicked.invoke(checked)
            }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
