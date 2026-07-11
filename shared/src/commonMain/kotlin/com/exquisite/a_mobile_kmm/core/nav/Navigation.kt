package com.exquisite.a_mobile_kmm.core.nav

import kotlinx.serialization.Serializable


// Authentication navigation
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
data class Success(val message:String, val title:String,val buttonText:String, val isAuth:Boolean)

@Serializable
object Home

@Serializable
object Booking

@Serializable
object Cart

@Serializable
object Academy

@Serializable
object Profile
@Serializable
object AuthNav

@Serializable
object DashboardNav

@Serializable
data class ProductDetails(
    val productItem: String
)

@Serializable
object Search

@Serializable
data class ProductListing(
    val categoryId:Int,
    val categoryName:String
)

@Serializable
object CheckoutList

@Serializable
 object AddressList

@Serializable
data class AddressForm
    (val id:Int?,val address:String?,val phone:String?)

@Serializable
data class DeliverOption(
    val createOrderModelJson : String,
    val paymentOption: String
)

@Serializable
data class WebViewUrl(val url:String)

@Serializable
object CleanersRegistration

@Serializable
data class CleanersDocumentUpload(val data:String)

@Serializable
object CleaningService

@Serializable
object DeepCleaningForm

@Serializable
data class DeepCleaningPriceDetails(val response:String,val data:String)

@Serializable
object BasicCleaningForm

@Serializable
data class DeepCleaningFormTwo(
    val response:String,val data:String
)




