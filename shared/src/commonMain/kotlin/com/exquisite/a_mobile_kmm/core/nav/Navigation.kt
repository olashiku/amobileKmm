package com.exquisite.a_mobile_kmm.core.nav

import kotlinx.serialization.Serializable


@Serializable
object Splash

@Serializable
object Onboard

@Serializable
object Login


@Serializable
object Dashboard

@Serializable
object ForgotPassword


@Serializable
object SignUp

@Serializable
data class Otp(val uniqueRef:String,val email:String ,val from:String)

@Serializable
data class  CreatePassword(
    val uniqueRef:String,val realOtp:String,val from:String)

@Serializable
data class  UploadImage(val uniqueRef:String,val otp:String,val password:String)


@Serializable
data class Success(val from:String)

