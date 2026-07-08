package com.exquisite.a_mobile_kmm.core.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.exquisite.a_mobile_kmm.feature.auth.presenter.create_password.CreatePasswordScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.forgot_password.ForgotPasswordScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.login.LoginScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.onboard.OnboardScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.otp.OtpScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.signup.SignupScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.splash.SplashScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.success.SuccessScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.UploadImageScreen
import kotlinx.coroutines.delay

@Composable
fun AuthenticationNavigation( goToDashboard: () -> Unit,
                              startDestination: Any = Splash,

                              ) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Splash
    ) {

        composable<Splash> {
            SplashScreen()
            LaunchedEffect(Unit) {
                delay(1000)
                navController.navigate(Onboard)
            }
        }

        composable<Onboard> {
            OnboardScreen {
                navController.navigate(Login)
            }
        }

        composable<Login> {
            LoginScreen({
                goToDashboard.invoke()
            }, {
                navController.navigate(ForgotPassword)
            }, {
                navController.navigate(SignUp)
            })

        }

        composable<ForgotPassword> {
            ForgotPasswordScreen({
                navController.popBackStack()
            }, { uniqueRef,email,from ->
                navController.navigate(Otp(uniqueRef, email,from))
            })
        }

        composable<SignUp> {
            SignupScreen({
                navController.popBackStack()
            }, { uniqueRef, email,from ->
                navController.navigate(Otp(uniqueRef, email,from))
            }, {

            })
        }

        composable<Otp> { backStack ->
            val otp = backStack.toRoute<Otp>()
            OtpScreen(otp, {
                navController.popBackStack()
            }, { realOtp ->
                navController.navigate(CreatePassword(otp.uniqueRef, realOtp,otp.from))
            })
        }

        composable<CreatePassword> { backStack ->
            val createPassword = backStack.toRoute<CreatePassword>()
            CreatePasswordScreen(createPassword,{
                navController.popBackStack()
            }, { password ->
                navController.navigate(UploadImage(createPassword.uniqueRef,createPassword.realOtp, password))
            },
                { message,title ->
                    navController.navigate(Success(message,title,true))
                })
        }

        composable<UploadImage> { backStack ->
            val uploadImage = backStack.toRoute<UploadImage>()
            UploadImageScreen(uploadImage,{
                navController.popBackStack()
            }, { message,title ->
                navController.navigate(Success(message,title,true))

            })
        }
        composable<Success> { backTrack->
            val successData = backTrack.toRoute<Success>()
            SuccessScreen(successData.title,successData.message) {
                navController.popBackStack(Login, false)
            }
        }
    }
}