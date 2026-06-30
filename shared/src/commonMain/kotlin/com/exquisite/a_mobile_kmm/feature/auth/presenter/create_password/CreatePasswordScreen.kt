package com.exquisite.a_mobile_kmm.feature.auth.presenter.create_password

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.nav.CreatePassword
import com.exquisite.a_mobile_kmm.core.screenUtils.Strings
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.ValidatedPasswordTextField
import com.exquisite.dripp.core.components.rememberSnackBar
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.screenUtils.ValidationHelper
import com.exquisite.dripp.core.components.LoadingDialog
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreatePasswordScreen(
    createPassword:CreatePassword,
    goBack: () -> Unit,
    goToUploadImage: (String) -> Unit,
    goToSuccessScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    createPasswordViewModel: CreatePasswordViewModel = koinViewModel<CreatePasswordViewModel>()
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()

    val confirmPasswordState = createPasswordViewModel.confirmPasswordState.collectAsStateWithLifecycle()
    when(val result = confirmPasswordState.value){
        is ConfirmPasswordState.Loading ->{
            LoadingDialog(true)

        }
        is ConfirmPasswordState.Idle ->{
            LoadingDialog(false)

        }
        is ConfirmPasswordState.Success ->{
            goToSuccessScreen.invoke("ConfirmPassword")
        }
        is ConfirmPasswordState.Error ->{
            snackBar.showError("Error: ${result.message}")

        }
    }

    DisposableEffect(Unit){
        onDispose{
            createPasswordViewModel.reset()
        }
    }


    val passwordValidator = remember {
        FieldValidator(
            ValidationHelper::validatePassword
        )
    }

    val revalidatePasswordValidator = remember {
        FieldValidator(
            ValidationHelper::validatePassword
        )
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
            val title = if(createPassword.from.equals("Signup")) "Create New Password" else "Reset your password"
            Text(
                text = title, style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF232323), textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(8.dp))
            val body = if(createPassword.from.equals("Signup")) "Kindly ensure that you\n validate your password before proceeding" else "Your new password must be different\n from previously used passwords."
            Text(
                text = body,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(24.dp))
            ValidatedPasswordTextField(
                labelText = Strings.Auth.PASSWORD,
                placeHolder = Strings.Auth.ENTER_PASSWORD,
                fieldValidator = passwordValidator
            )
            Spacer(modifier = Modifier.height(15.dp))
            ValidatedPasswordTextField(
                labelText = Strings.Auth.CONFIRM_PASSWORD,
                placeHolder = Strings.Auth.RE_CONFIRM_PASSWORD,
                fieldValidator = revalidatePasswordValidator
            )
            Spacer(modifier = Modifier.height(15.dp))
            PrimaryButton("Create New Password", {
                if(passwordValidator.value.value  == revalidatePasswordValidator.value.value){
                    if(!createPassword.from.equals("Signup")){
                        createPasswordViewModel.confirmPassword(createPassword.uniqueRef,passwordValidator.value.value,createPassword.realOtp)
                    }else {
                        goToUploadImage(passwordValidator.value.value)
                    }
                }else {
                    snackBar.showError("Password mismatch pls try again later")
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