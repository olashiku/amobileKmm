package com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.profile_form

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold20
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.EditProfileRequest
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFormScreen(
    onBackClick: (() -> Unit)? = null,
    viewModel: ProfileFormViewModel = koinViewModel<ProfileFormViewModel>()
) {
    val state by viewModel.profileFormState.collectAsState()
    val userData by viewModel.userData.collectAsState()

    // Form state
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var customerId by remember { mutableStateOf(0) }
    var profilePicture by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+234") }
    var expandedCountryCode by remember { mutableStateOf(false) }

    val countryCodes = listOf("+234", "+1", "+44", "+91", "+254")

    // Validators
    val firstNameValidator = remember {
        FieldValidator(ValidationHelper::validateFullName)
    }
    val lastNameValidator = remember {
        FieldValidator(ValidationHelper::validateFullName)
    }
    val emailValidator = remember {
        FieldValidator(ValidationHelper::validateEmail)
    }
    val phoneValidator = remember {
        FieldValidator(ValidationHelper::validatePhoneNumber)
    }

    // Load user data from viewModel
    LaunchedEffect(userData) {
        if (userData.firstName.isNotEmpty() || userData.email.isNotEmpty()) {
            firstName = userData.firstName
            lastName = userData.lastName
            email = userData.email
            phone = userData.phone
            customerId = userData.customerId
            profilePicture = userData.profilePicture

            // Set initial validator values
            firstNameValidator.setValue(firstName)
            lastNameValidator.setValue(lastName)
            emailValidator.setValue(email)
        }
    }

    // Handle form submission
    fun handleSaveChanges() {
        // Validate all fields
        firstNameValidator.forceValidation()
        lastNameValidator.forceValidation()
        emailValidator.forceValidation()
        phoneValidator.forceValidation()

        if (firstNameValidator.isValid.value &&
            lastNameValidator.isValid.value &&
            emailValidator.isValid.value &&
            phoneValidator.isValid.value
        ) {
            val request = EditProfileRequest(
                email = emailValidator.value.value,
                firstName = firstNameValidator.value.value,
                lastName = lastNameValidator.value.value,
                phone = phoneValidator.value.value,
                customerId = customerId,
                profilePicture = profilePicture
            )
            viewModel.editProfile(request)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEF2F6))
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 40.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Back Button
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Back",
                                tint = Color(0xFF0F172A),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    // Title
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Edit Profile",
                            style = getPoppinsBold20(),
                            color = Color(0xFF0F172A)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person, // Using Person as placeholder for lock
                                contentDescription = "Lock",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = "Your personal data is private and secure.",
                                style = getPoppinsMedium13(),
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Avatar Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(100.dp)
                ) {
                    // Avatar Circle
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2E8F0))
                            .border(2.dp, Color(0xFFE2E8F0), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (profilePicture.isEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile Avatar",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(40.dp)
                            )
                        } else {
                            AsyncImage(
                                model = profilePicture,
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    // Camera Badge
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.BottomEnd)
                            .shadow(4.dp, CircleShape)
                            .background(Color(0xFFF29100), CircleShape)
                            .border(3.dp, Color.White, CircleShape)
                            .clickable {
                                // TODO: Implement image picker
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person, // Using Person as placeholder for camera
                            contentDescription = "Change Photo",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Form Fields
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // First Name & Last Name Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        ValidatedTextField(
                            labelText = "First Name",
                            placeHolder = "Samuel",
                            fieldValidator = firstNameValidator,
                            defaultText = firstName,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        ValidatedTextField(
                            labelText = "Last Name",
                            placeHolder = "Oluwa",
                            fieldValidator = lastNameValidator,
                            defaultText = lastName,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    }
                }

                // Phone Number with Country Code
                Column {
                    Text(
                        text = "Phone Number",
                        style = getPoppinsSemiBold13(),
                        color = Color(0xFF0F172A),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Country Code Dropdown
                        ExposedDropdownMenuBox(
                            expanded = expandedCountryCode,
                            onExpandedChange = { expandedCountryCode = !expandedCountryCode },
                            modifier = Modifier.weight(0.3f)
                        ) {
                            OutlinedTextField(
                                value = countryCode,
                                onValueChange = {},
                                readOnly = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF6F6F6),
                                    unfocusedContainerColor = Color(0xFFF6F6F6),
                                    focusedBorderColor = Color(0xFFF29100),
                                    unfocusedBorderColor = Color(0xFFE2E8F0)
                                ),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = expandedCountryCode,
                                onDismissRequest = { expandedCountryCode = false }
                            ) {
                                countryCodes.forEach { code ->
                                    DropdownMenuItem(
                                        text = { Text(code, style = getPoppinsMedium14()) },
                                        onClick = {
                                            countryCode = code
                                            expandedCountryCode = false
                                        }
                                    )
                                }
                            }
                        }

                        // Phone Number Field
                        Box(modifier = Modifier.weight(0.7f)) {
                            ValidatedTextField(
                                labelText = "",
                                placeHolder = "080 0000 0000",
                                fieldValidator = phoneValidator,
                                defaultText = phone,
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            )
                        }
                    }
                }

                // Email Address
                ValidatedTextField(
                    labelText = "Email Address",
                    placeHolder = "example@email.com",
                    fieldValidator = emailValidator,
                    defaultText = email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                )
            }
        }

        // Fixed Footer with Save Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = Color.White.copy(alpha = 0.95f)
                )
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            when (state) {
                is ProfileFormState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFFF29100),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                else -> {
                    PrimaryButton(
                        text = "Save Changes",
                        onClick = { handleSaveChanges() }
                    )
                }
            }
        }
    }

    // Handle state changes
    when (state) {
        is ProfileFormState.EditProfileSuccess -> {
            // Show success message or navigate back
            LaunchedEffect(Unit) {
                // TODO: Show success snackbar
                onBackClick?.invoke()
            }
        }
        is ProfileFormState.Error -> {
            // Show error message
            LaunchedEffect(state) {
                // TODO: Show error snackbar
            }
        }
        else -> {}
    }
}
