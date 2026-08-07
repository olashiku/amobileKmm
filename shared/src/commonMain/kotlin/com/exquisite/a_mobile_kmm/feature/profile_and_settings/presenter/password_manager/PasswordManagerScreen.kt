package com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.password_manager

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.success_icon
import amobilekmm.shared.generated.resources.warning_icon
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationResult
import com.exquisite.a_mobile_kmm.core.screen_components.GenericAlertModal
import com.exquisite.a_mobile_kmm.core.screen_components.ModalButton
import com.exquisite.a_mobile_kmm.core.screen_components.ModalType
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedPasswordTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.ChangePasswordRequest
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.profile_form.ProfileFormState
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.profile_form.ProfileFormViewModel
import org.koin.compose.viewmodel.koinViewModel

enum class PasswordStrength {
    WEAK, MEDIUM, STRONG
}

data class PasswordRequirement(
    val text: String,
    val isMet: Boolean
)

@Composable
fun PasswordManagerScreen(
    onBackClick: (() -> Unit)? = null,
    viewModel: ProfileFormViewModel = koinViewModel<ProfileFormViewModel>()
) {
    val state by viewModel.profileFormState.collectAsState()
    val userData by viewModel.userData.collectAsState()
    var showSuccessModal by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Form state
    var email by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var customerId by remember { mutableStateOf(0) }

    // Password validators
    val currentPasswordValidator = remember {
        FieldValidator(ValidationHelper::validatePassword)
    }
    val newPasswordValidator = remember {
        FieldValidator(ValidationHelper::validatePassword)
    }
    val confirmPasswordValidator = remember {
        FieldValidator { password ->
            when {
                password.isEmpty() -> ValidationResult(
                    false,
                    "Please confirm your password"
                )
                password != newPasswordValidator.value.value -> ValidationResult(
                    false,
                    "Passwords do not match"
                )
                else -> ValidationResult(true)
            }
        }
    }

    // Load user data from viewModel
    LaunchedEffect(userData) {
        if (userData.firstName.isNotEmpty() || userData.email.isNotEmpty()) {
            email = userData.email
            firstName = userData.firstName
            lastName = userData.lastName
            phone = userData.phone
            customerId = userData.customerId
        }
    }

    // Observe state changes and trigger one-time events
    LaunchedEffect(state) {
        when (val result = state) {
            is ProfileFormState.ChangePasswordSuccess -> {
                showSuccessModal = true
                viewModel.resetState()
            }
            is ProfileFormState.Error -> {
                errorMessage = result.message
                viewModel.resetState()
            }
            else -> {}
        }
    }

    // Calculate password strength
    val passwordStrength by remember {
        derivedStateOf {
            calculatePasswordStrength(newPasswordValidator.value.value)
        }
    }

    // Password requirements
    val requirements by remember {
        derivedStateOf {
            listOf(
                PasswordRequirement(
                    "At least 8 characters",
                    newPasswordValidator.value.value.length >= 8
                ),
                PasswordRequirement(
                    "Contains a number or symbol",
                    newPasswordValidator.value.value.any { it.isDigit() || !it.isLetterOrDigit() }
                )
            )
        }
    }

    fun handleUpdatePassword() {
        currentPasswordValidator.forceValidation()
        newPasswordValidator.forceValidation()
        confirmPasswordValidator.forceValidation()

        if (currentPasswordValidator.isValid.value &&
            newPasswordValidator.isValid.value &&
            confirmPasswordValidator.isValid.value &&
            newPasswordValidator.value.value == confirmPasswordValidator.value.value
        ) {
            val request = ChangePasswordRequest(
                customerId = customerId,
                oldPassword = currentPasswordValidator.value.value,
                newPassword = newPasswordValidator.value.value
            )
            viewModel.changePassword(request)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
                    .border(
                        width = 0.dp,
                        color = Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(0.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(0xFF1E293B),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(if (onBackClick != null) 30.dp else 0.dp))

                Text(
                    text = "Change Password",
                    style = getPoppinsBold18(),
                    color = Color(0xFF1E293B)
                )
            }

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE2E8F0))
            )

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Current Password
                Column {
                    ValidatedPasswordTextField(
                        labelText = "Current Password",
                        placeHolder = "••••••••••••",
                        fieldValidator = currentPasswordValidator
                    )

                }

                // New Password
                Column {
                    ValidatedPasswordTextField(
                        labelText = "New Password",
                        placeHolder = "Min 8 characters",
                        fieldValidator = newPasswordValidator
                    )

                    // Strength Meter
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(Color(0xFFE2E8F0), RoundedCornerShape(2.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(
                                    when (passwordStrength) {
                                        PasswordStrength.WEAK -> 0.33f
                                        PasswordStrength.MEDIUM -> 0.66f
                                        PasswordStrength.STRONG -> 1f
                                    }
                                )
                                .height(4.dp)
                                .background(
                                    when (passwordStrength) {
                                        PasswordStrength.WEAK -> Color(0xFFEF4444)
                                        PasswordStrength.MEDIUM -> Color(0xFFFACC15)
                                        PasswordStrength.STRONG -> Color(0xFF10B981)
                                    },
                                    RoundedCornerShape(2.dp)
                                )
                        )
                    }

                    // Requirements
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        requirements.forEach { requirement ->
                            PasswordRequirementItem(requirement)
                        }
                    }
                }

                // Confirm Password
                ValidatedPasswordTextField(
                    labelText = "Confirm New Password",
                    placeHolder = "Repeat new password",
                    fieldValidator = confirmPasswordValidator
                )
            }

            // Footer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(0.dp)
                    )
                    .padding(24.dp)
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
                            text = "Update Password",
                            onClick = { handleUpdatePassword() }
                        )
                    }
                }
            }
        }
    }

    // Show success modal controlled by local state
    if (showSuccessModal) {
        GenericAlertModal(
            modalType = ModalType.Success(iconRes = Res.drawable.success_icon),
            title = "Success!",
            message = "Your password has been changed successfully",
            primaryButton = ModalButton(
                text = "Continue",
                backgroundColor = Color(0xFF10B981),
                action = {
                    showSuccessModal = false
                    onBackClick?.invoke()
                }
            )
        )
    }

    // Show error modal controlled by local state
    if (!errorMessage.isEmpty()) {
        GenericAlertModal(
            modalType = ModalType.Error(iconRes = Res.drawable.warning_icon),
            title = "Error!",
            message = errorMessage,
            primaryButton = ModalButton(
                text = "Continue",
                backgroundColor = Color(0xFF10B981),
                action = {
                    errorMessage = ""

                }
            )
        )
    }
}

@Composable
private fun PasswordRequirementItem(requirement: PasswordRequirement) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = if (requirement.isMet) "●" else "○",
            style = getPoppinsMedium12(),
            color = if (requirement.isMet) Color(0xFF10B981) else Color(0xFF64748B),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(
            text = requirement.text,
            style = getPoppinsMedium12(),
            color = if (requirement.isMet) Color(0xFF10B981) else Color(0xFF64748B)
        )
    }
}

private fun calculatePasswordStrength(password: String): PasswordStrength {
    if (password.length < 8) return PasswordStrength.WEAK

    var score = 0

    // Length check
    if (password.length >= 8) score++
    if (password.length >= 12) score++

    // Character variety
    if (password.any { it.isUpperCase() }) score++
    if (password.any { it.isLowerCase() }) score++
    if (password.any { it.isDigit() }) score++
    if (password.any { !it.isLetterOrDigit() }) score++

    return when {
        score <= 2 -> PasswordStrength.WEAK
        score <= 4 -> PasswordStrength.MEDIUM
        else -> PasswordStrength.STRONG
    }
}
