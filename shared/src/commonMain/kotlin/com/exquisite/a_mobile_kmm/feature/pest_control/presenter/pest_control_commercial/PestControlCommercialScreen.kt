package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_commercial

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
fun PestControlCommercialScreen(goBack:()->Unit,goToSuccess:(String,String,String)->Unit,modifier :Modifier = Modifier, viewModel :PestControlCommercialViewModel = koinViewModel<PestControlCommercialViewModel>()) {

    val formData by viewModel.formData.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()

    val pestControlState  = viewModel.pestControlCommercialState.collectAsStateWithLifecycle()
    when(val result = pestControlState.value){
        is PestControlCommercialState.Success -> {
            viewModel.clearState()
            goToSuccess.invoke("Thank you for registering","A customer service representative will get in touch with you shortly","Go Back Home")

        }
        is PestControlCommercialState.Error -> {
            snackBar.showError(result.message)
        }
        is PestControlCommercialState.Idle -> {
            LoadingDialog(false)

        }
        is PestControlCommercialState.Loading -> {
            LoadingDialog(true)
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

    val times = listOf("9:00 AM", "10:00 AM", "11:30 AM","12:00 PM", "1:00 PM", "2:30 PM", "4:00 PM")

    // Initialize selected time if not set
    LaunchedEffect(Unit) {
        if (formData.selectedTime == null) {
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
        if (addressValidator.value.value != formData.address) {
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


    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Commercial Pest Control",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, bottom = 80.dp)
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
                    placeHolder = "Enter company address",
                    fieldValidator = addressValidator,
                    defaultText = formData.address,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Choose an available Date"
                    , style = getPoppinsMedium14(),
                    color = Color(0xFF252525))
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Minimum lead time: 2-3 working days"
                    , style = getPoppinsRegular12(),
                    color = Color(0xFF252525), modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                SingleDateCalendarSelector(
                    selectedDate = formData.selectedDate,
                    onDateSelected = { viewModel.setSelectedDate(it) },
                    excludeSundays = true,
                    excludePastDates = true
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Choose an available time"
                    , style = getPoppinsMedium14(),
                    color = Color(0xFF252525))
                Spacer(modifier = Modifier.height(15.dp))
                TimeSlotGrid(times, formData.selectedTime) {
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
                    imeAction = ImeAction.Done)
                Spacer(modifier = modifier.height(50.dp))

            }
        }

        PrimaryButton("Register", {

            val isCompanyNameValid = validateCompanyName.forceValidation()
            val isCompanyEmailValid = emailValidator.forceValidation()
            val isCompanyAddressValid = addressValidator.forceValidation()
            val isDateSelected = formData.selectedDate != null
            val isSelectedTimeValid = formData.selectedTime != null
            val isRecipientNameValid = recipientNameValidator.forceValidation()
            val isRecipientEmailValid = recipientEmailValidator.forceValidation()
            val isRecipientPhoneValid = recipientPhoneValidator.forceValidation()


            if (isCompanyNameValid && isCompanyEmailValid
                && isCompanyAddressValid && isDateSelected && isSelectedTimeValid && isRecipientNameValid && isRecipientEmailValid &&
                isRecipientPhoneValid ) {

                // All form data is already persisted in ViewModel
                viewModel.requestCommercialPestControl(
                    companyName =validateCompanyName.value.value,
                    companyEmail = emailValidator.value.value,
                    companyAddress = addressValidator.value.value,
                    availabilityDate = formData.selectedDate?.fullDate?:"",
                    availabilityTime =  formData.selectedTime?.formatTime()?:"",
                    recipientName = recipientNameValidator.value.value,
                    recipientEmail = recipientNameValidator.value.value,
                    recipientPhone = recipientPhoneValidator.value.value
                )
            }else {
                snackBar.showError("Kindly ensure that you fill all the required field in the form")
            }


        },modifier = Modifier.align(BottomCenter).padding(20.dp))

        // Snackbar above the button
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(bottom = 90.dp)
        )
    }
}
