package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.construction_mobile_toilet

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.formatTime
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.SingleDateCalendarSelector
import com.exquisite.a_mobile_kmm.core.screen_components.TimeSlotGrid
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConstructionMobileToiletScreen(
    goBack: () -> Unit, goToSuccess: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConstructionMobileToiletViewModel = koinViewModel<ConstructionMobileToiletViewModel>()
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()
    val formData by viewModel.formData.collectAsStateWithLifecycle()
    val constructionMobileToiletState = viewModel.constructionMobileToiletState.collectAsStateWithLifecycle()

    when(val result = constructionMobileToiletState.value){
        is ConstructionMobileToiletState.Idle -> {
            LoadingDialog(false)

        }
        is ConstructionMobileToiletState.Loading -> {
            LoadingDialog(true)
        }
        is ConstructionMobileToiletState.Success -> {
            viewModel.clearState()
            goToSuccess.invoke("Thank you for registering","A customer service representative will get in touch with you shortly","Go Back Home")
        }

        is ConstructionMobileToiletState.Error -> {
            viewModel.reset()
            snackBar.showError(result.message)
        }
    }

    val times = listOf("9:00 AM", "10:00 AM", "11:30 AM", "12:00 PM", "1:00 PM", "2:30 PM", "4:00 PM")


    LaunchedEffect(Unit){
        if (formData.availabilityTime == null) {
            val timeToSet = formData.availabilityTime?.ifEmpty { times[1] }
            viewModel.setSelectedTime(timeToSet)
        }
    }

    val validateCompanyName = remember {
        FieldValidator(
            ValidationHelper::validateCompanyName
        )
    }

    val emailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }

    val addressValidator = remember {
        FieldValidator(
            ValidationHelper::validateAddress
        )
    }

    val recipientNameValidator = remember {
        FieldValidator(
            ValidationHelper::validateName
        )
    }

    val recipientEmailValidator = remember {
        FieldValidator(
            ValidationHelper::validateEmail
        )
    }

    val recipientPhoneValidator = remember {
        FieldValidator(
            ValidationHelper::validatePhoneNumber
        )
    }

    val peopleOnSiteValidator = remember {
        FieldValidator(
            ValidationHelper::peopleOnSiteValidation
        )
    }

    val durationValidator = remember {
        FieldValidator(
            ValidationHelper::durationValidation
        )
    }


    // Initialize selected time if not set
    LaunchedEffect(Unit) {
        if (formData.availabilityTime == null) {
            viewModel.setSelectedTime(times[1])
        }
    }

    // Sync field validators with ViewModel when values change
    LaunchedEffect(validateCompanyName.value.value) {
        if (validateCompanyName.value.value != formData.companyName) {
            viewModel.setCompanyName(validateCompanyName.value.value)
        }
    }

    LaunchedEffect(emailValidator.value.value) {
        if (emailValidator.value.value != formData.companyEmail) {
            viewModel.setCompanyEmail(emailValidator.value.value)
        }
    }

    LaunchedEffect(addressValidator.value.value) {
        if (addressValidator.value.value != formData.constructionAddress) {
            viewModel.setAddress(addressValidator.value.value)
        }
    }

    LaunchedEffect(recipientNameValidator.value.value) {
        if (recipientNameValidator.value.value != formData.recipientName) {
            viewModel.setRecipientName(recipientNameValidator.value.value)
        }
    }

    LaunchedEffect(recipientEmailValidator.value.value) {
        if (recipientEmailValidator.value.value != formData.recipientEmail) {
            viewModel.setRecipientEmail(recipientEmailValidator.value.value)
        }
    }

    LaunchedEffect(recipientPhoneValidator.value.value) {
        if (recipientPhoneValidator.value.value != formData.recipientPhone) {
            viewModel.setRecipientPhone(recipientPhoneValidator.value.value)
        }
    }

    LaunchedEffect(peopleOnSiteValidator.value.value) {
        if (peopleOnSiteValidator.value.value != formData.numberOfPeopleOnSite) {
            viewModel.setNumberOfPeopleOnSite(peopleOnSiteValidator.value.value)
        }
    }

    LaunchedEffect(durationValidator.value.value) {
        if (durationValidator.value.value != formData.numberOfMonths) {
            viewModel.setDuration(durationValidator.value.value)
        }
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Construction Mobile Toilet Request",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, bottom = 100.dp)
            ) {
                Spacer(modifier = modifier.height(22.dp))
                Text(
                    text = "Please fill in the form below with the required information",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = modifier.height(22.dp))

                ValidatedTextField(
                    labelText = "Company  Name",
                    placeHolder = "Enter company name",
                    fieldValidator = validateCompanyName,
                    defaultText = formData.companyName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Company Email",
                    placeHolder = "Enter company email ",
                    fieldValidator = emailValidator,
                    defaultText = formData.companyEmail,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Address",
                    placeHolder = "Enter construction address",
                    fieldValidator = addressValidator,
                    defaultText = formData.constructionAddress,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Choose an available Date", style = getPoppinsMedium14(),
                    color = Color(0xFF252525)
                )
                Spacer(modifier = Modifier.height(15.dp))
                SingleDateCalendarSelector(
                    selectedDate = formData.availabilityDate,
                    onDateSelected = { viewModel.setSelectedDate(it) },
                    excludeSundays = false,
                    excludePastDates = true
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Choose an available time", style = getPoppinsMedium14(),
                    color = Color(0xFF252525)
                )
                Spacer(modifier = Modifier.height(15.dp))
                TimeSlotGrid(times, formData.availabilityTime) {
                    viewModel.setSelectedTime(it)
                }
                Spacer(modifier = modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Recipient Name",
                    placeHolder = "Enter Recipient Name",
                    fieldValidator = recipientNameValidator,
                    defaultText = formData.recipientName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "Recipient Email",
                    placeHolder = "Enter recipient email ",
                    fieldValidator = recipientEmailValidator,
                    defaultText = formData.recipientEmail,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = modifier.height(15.dp))

                ValidatedTextField(
                    labelText = "Recipient Phone Number",
                    placeHolder = "Enter Recipient Phone Number ",
                    fieldValidator = recipientPhoneValidator,
                    defaultText = formData.recipientPhone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                )
                Spacer(modifier = modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "People On Site",
                    placeHolder = "Number of People On Site",
                    fieldValidator = peopleOnSiteValidator,
                    defaultText = formData.numberOfPeopleOnSite,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
                Spacer(modifier = modifier.height(15.dp))
                ValidatedTextField(
                    labelText = "How long would you need our service",
                    placeHolder = "Duration(Months)",
                    fieldValidator = durationValidator,
                    defaultText = formData.numberOfMonths,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
                Spacer(modifier = modifier.height(45.dp))


            }
        }

        // Button container with background to prevent content from flowing underneath
        Surface(
            modifier = Modifier
                .align(BottomCenter)
                .fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            PrimaryButton("Register", {

                val isCompanyNameValid = validateCompanyName.forceValidation()
                val isCompanyEmailValid = emailValidator.forceValidation()
                val isCompanyAddressValid = addressValidator.forceValidation()
                val isDateSelected = formData.availabilityDate != null
                val isSelectedTimeValid = formData.availabilityTime != null
                val isRecipientNameValid = recipientNameValidator.forceValidation()
                val isRecipientEmailValid = recipientEmailValidator.forceValidation()
                val isRecipientPhoneValid = recipientPhoneValidator.forceValidation()
                val isPeopleOnSiteValid = peopleOnSiteValidator.forceValidation()
                val isDurationValid = durationValidator.forceValidation()


                if (isCompanyNameValid && isCompanyEmailValid
                    && isCompanyAddressValid && isDateSelected && isSelectedTimeValid && isRecipientNameValid && isRecipientEmailValid &&
                    isRecipientPhoneValid && isPeopleOnSiteValid && isDurationValid
                ) {

                    // All form data is already persisted in ViewModel
                    viewModel.requestForConstruction(
                        companyName = validateCompanyName.value.value,
                        companyEmail = emailValidator.value.value,
                        constructionAddress = addressValidator.value.value,
                        availabilityDate = formData.availabilityDate?.fullDate ?: "",
                        availabilityTime = formData.availabilityTime?.formatTime() ?: "",
                        recipientName = recipientNameValidator.value.value,
                        recipientEmail = recipientEmailValidator.value.value,
                        recipientPhone = recipientPhoneValidator.value.value,
                        numberOfPeopleOnSite = peopleOnSiteValidator.value.value,
                        numberOfMonths = durationValidator.value.value
                    )
                } else {
                    snackBar.showError("Kindly ensure that you fill all the required field in the form")
                }
            }, modifier = Modifier.padding(20.dp))
        }

        // Snackbar above the button
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(bottom = 100.dp)
        )
    }
}
