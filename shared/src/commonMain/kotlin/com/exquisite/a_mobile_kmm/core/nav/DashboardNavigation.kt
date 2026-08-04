package com.exquisite.a_mobile_kmm.core.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils.decodeObject
import com.exquisite.a_mobile_kmm.core.screen_components.WebViewUrlScreen
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.feature.address.presenter.address_form.AddressFormScreen
import com.exquisite.a_mobile_kmm.feature.address.presenter.address_list.AddressListScreen
import com.exquisite.a_mobile_kmm.feature.auth.presenter.success.SuccessScreen
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBooking
import com.exquisite.a_mobile_kmm.feature.booking.presenter.booking.BookingScreen
import com.exquisite.a_mobile_kmm.feature.booking.presenter.booking_details.BookingDetailsScreen
import com.exquisite.a_mobile_kmm.feature.cart.presenter.CartScreen
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerRequest
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_document.CleanersDocumentUploadScreen
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration.CleanersRegistrationScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.BasicCleaningBreakdownModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.BasicCleaningForm2Model
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.BasicCleaningFormModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningPriceModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DeepCleaningFormData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DeepCleaningFormModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_checkout.BasicCleaningCheckoutScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form.BasicCleaningFormScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form_two.BasicCleaningFormTwoScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_price.BasicCleaningPriceDetailsScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.cleaning_service.CleaningServiceScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_checkout.DeepCleaningCheckoutScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form.DeepCleaningFormScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form_two.DeepCleaningFormTwoScreen
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_price.DeepCleaningPriceDetailsScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CreateOrderModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list.CheckoutListScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.deliver_option.DeliveryOptionScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home.HomeScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_details.ProductDetailsScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_listing.ProductListingScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search.ProductSearchScreen
import com.exquisite.a_mobile_kmm.feature.janitorial.presenter.janitorial.JanitorialScreen
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.MobileToiletEventFormOneFormData
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.MobileToiletFormThreeModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletAvailabilityModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletPriceModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.construction_mobile_toilet.ConstructionMobileToiletScreen
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.event_toilet_checkout.EventToiletCheckoutScreen
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_pricing.MobileToiletPricingScreen
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet.MobileToiletService
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_form_one.MobileToiletEventFormOneScreen
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_form_three.MobileToiletFormThreeScreen
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.mobile_toilet_event_toilet_form_two.EventToiletFormTwoScreen
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.onboarding.MobileToiletOnboardingScreen
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlResidentialFormModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.ResidentialPestControlFormTwoModel
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control.PestControlScreen
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_commercial.PestControlCommercialScreen
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_price.PestControlPriceScreen
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_checkout.PestControlResidentialCheckoutScreen
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form.PestControlResidentialFormScreen
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form_two.ResidentialPestControlFormTwoScreen
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticResidentialForm2Model
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticTruckSizeModel
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form.SepticCommercialFormScreen
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_checkout.SepticResidentialCheckoutScreen
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form.SepticResidentialFormScreen
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form2.SepticResidentialForm2Screen
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_pricing.SepticResidentialPricingScreen
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_service.SepticServiceScreen
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.presenter.profile.ProfileScreen
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingCourse
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingRegistrationModel
import com.exquisite.a_mobile_kmm.feature.training.presenter.course_details.CourseDetailsScreen
import com.exquisite.a_mobile_kmm.feature.training.presenter.training.TrainingScreen
import com.exquisite.a_mobile_kmm.feature.training.presenter.training_checkout.TrainingCheckoutScreen
import com.exquisite.a_mobile_kmm.feature.training.presenter.training_registration.TrainingRegistrationScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun DashboardNavigation(onLogout: () -> Unit = {}) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showBottomBar = currentRoute in listOf(
        Home::class.qualifiedName,
        Booking::class.qualifiedName,
        Cart::class.qualifiedName,
        Academy::class.qualifiedName,
        Profile::class.qualifiedName
    )

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar, enter = slideInVertically(
                    initialOffsetY = { it }, // Slide up from bottom
                    animationSpec = tween(
                        durationMillis = 1000, easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 1000)
                ), exit = slideOutVertically(
                    targetOffsetY = { it }, // Slide down to bottom
                    animationSpec = tween(
                        durationMillis = 1000, easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(durationMillis = 300)
                ), content = { BottomBar(navController) })

        }, containerColor = Color(0xFFFFFFFF), contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(padding)
        ) {
            composable<Home> {
                HomeScreen(goToSearchDialog = {
                    navController.navigate(Search)
                }, goToCartScreen = {
                    navController.popBackStack<Home>(inclusive = false)
                    navController.navigate(Cart)
                }, getCategoryProduct = { product ->
                    navController.navigate(ProductDetails(product))
                }, goToProductListing = { categoryId, categoryName ->
                    navController.navigate(ProductListing(categoryId, categoryName))
                }, goToCleanersRegistration = {
                    navController.navigate(CleanersRegistration)
                }, goToMenuItem = { label ->
                    when (label) {
                        "cleaning" -> {
                            navController.navigate(CleaningService)
                        }

                        "mobile_toilet" -> {
                            navController.navigate(MobileToiletOnboarding)
                        }

                        "pest_control" -> {
                            navController.navigate(PestControl)
                        }

                        "academy" -> {
                            navController.navigate(Academy)
                        }

                        "janitorial_service" -> {
                            navController.navigate(JanitorialService)
                        }

                        "septic" -> {
                            navController.navigate(SepticService)
                        }
                    }
                })
            }

            composable<JanitorialService> {
                JanitorialScreen(goBack = {
                    navController.popBackStack()
                }, goToSuccessPage = { title, message, buttonText ->
                    navController.navigate(Success(message, title, buttonText, false))

                })
            }

            composable<SepticService> {
                SepticServiceScreen(goBack = {
                    navController.popBackStack()
                }, goToNextPage = { destination ->
                    if (destination.equals("residential")) {
                        navController.navigate(SepticServiceResidential)
                    } else {
                        navController.navigate(SepticServiceCommercial)
                    }
                })
            }

            composable<SepticServiceResidential> {
                SepticResidentialFormScreen(goBack = {
                    navController.popBackStack()
                }, goToPricing = {
                    navController.navigate(SepticResidentialPricing(it))
                })
            }

            composable<SepticResidentialPricing> { backStackEntry ->
                val data = backStackEntry.toRoute<SepticResidentialPricing>()
                val septicTruckSizeModel =
                    decodeObject<SepticTruckSizeModel>(data.septicTruckSizeModel)

                SepticResidentialPricingScreen(septicTruckSizeModel, goBack = {
                    navController.popBackStack()
                }, goToNextPage = {
                    navController.navigate(SepticResidentialForm2(data.septicTruckSizeModel))
                })
            }

            composable<SepticResidentialForm2> { backStackEntry ->
                val data = backStackEntry.toRoute<SepticResidentialPricing>()
                val septicTruckSizeModel =
                    decodeObject<SepticTruckSizeModel>(data.septicTruckSizeModel)

                SepticResidentialForm2Screen(goBack = {
                    navController.popBackStack()
                }, goToCheckout = { septicResidentialForm2Model ->
                    navController.navigate(
                        SepticResidentialCheckout(
                            data.septicTruckSizeModel,
                            septicResidentialForm2Model
                        )
                    )
                })
            }

            composable<SepticResidentialCheckout> { backTrack ->
                val data = backTrack.toRoute<SepticResidentialCheckout>()
                val septicTruckSizeModel =
                    decodeObject<SepticTruckSizeModel>(data.septicTruckSizeModel)
                val septicResidentialForm2Model =
                    decodeObject<SepticResidentialForm2Model>(data.septicResidentialForm2Model)

                SepticResidentialCheckoutScreen(
                    septicTruckSizeModel,
                    septicResidentialForm2Model,
                    backTrack.savedStateHandle,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToWebView = { url ->
                        navController.navigate(WebViewUrl(url))
                    },
                    goToSuccess = { title, message, buttonText ->
                        navController.navigate(Success(message, title, buttonText, false))
                    })
            }


            composable<SepticServiceCommercial> {
                SepticCommercialFormScreen(goBack = {
                    navController.popBackStack()
                }, goToSuccess = { title, message, buttonText ->
                    navController.navigate(Success(message, title, buttonText, false))
                })
            }

            composable<Booking> {
                BookingScreen(
                    toBookingDetails = { customerBooking->
                        navController.navigate(BookingDetails(customerBooking))
                    }
                )
            }

             composable<BookingDetails> { backTrack ->
                 val data = backTrack.toRoute<BookingDetails>()
                 val customerBooking = decodeObject<CustomerBooking>(data.customerBooking)
                 BookingDetailsScreen(
                     customerBooking = customerBooking,
                     goBack = { navController.popBackStack() }
                 )
             }

            composable<Cart> {
                CartScreen(toCheckout = {
                    navController.navigate(CheckoutList)
                })
            }

            composable<Academy> {
                TrainingScreen(goToCourseDetails = { trainingCourse ->
                    navController.navigate(CourseDetails(trainingCourse))
                }, goToTrainingReg = { trainingCourse ->
                    navController.navigate(TrainingForm(trainingCourse))
                })
            }

            composable<CourseDetails> { backTrack ->
                val data = backTrack.toRoute<CourseDetails>()
                val trainingCourse =
                    decodeObject<TrainingCourse>(data.trainingCourse)
                CourseDetailsScreen(
                    trainingCourse, goBack =
                        { navController.popBackStack() })
            }

            composable<TrainingForm> { backTrack ->
                val data = backTrack.toRoute<TrainingForm>()

                TrainingRegistrationScreen(goBack = {
                    navController.popBackStack()
                }, goToCheckoutPage = { trainingRegistrationModel ->
                    navController.navigate(
                        TrainingCheckout(
                            data.trainingCourse,
                            trainingRegistrationModel
                        )
                    )
                })
            }

            composable<TrainingCheckout> { backTrack ->
                val data = backTrack.toRoute<TrainingCheckout>()
                val trainingCourse =
                    decodeObject<TrainingCourse>(data.trainingCourse)
                val trainingRegistrationModel =
                    decodeObject<TrainingRegistrationModel>(data.trainingFormModel)

                TrainingCheckoutScreen(
                    trainingCourse, trainingRegistrationModel,
                    backTrack.savedStateHandle,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToWebView = { url ->
                        navController.navigate(WebViewUrl(url))
                    },
                    goToSuccess = { title, message, buttonText ->
                        navController.navigate(Success(message, title, buttonText, false))
                    })
            }

            composable<Profile> {
                ProfileScreen()
            }

            composable<ProductDetails> { backStackEntry ->
                showBottomBar = false
                val data = backStackEntry.toRoute<ProductDetails>()
                ProductDetailsScreen(decodeObject(data.productItem), onBackClick = {
                    navController.popBackStack()
                }, onSearchClick = {
                    navController.navigate(Search)
                }, onCartClick = {
                    navController.popBackStack<Home>(inclusive = false)
                    navController.navigate(Cart)
                })
            }

            composable<CheckoutList> {
                CheckoutListScreen(goBack = {
                    navController.popBackStack()
                }, addNewAddress = {
                    navController.navigate(AddressList)
                }, continueButton = { createOrderModelJson, paymentOption ->
                    navController.navigate(DeliverOption(createOrderModelJson, paymentOption))
                })
            }

            composable<DeliverOption> { backStack ->
                val createOrderModel =
                    decodeObject<CreateOrderModel>(backStack.toRoute<DeliverOption>().createOrderModelJson)
                DeliveryOptionScreen(
                    createOrderModel = createOrderModel,
                    paymentOption = backStack.toRoute<DeliverOption>().paymentOption,
                    savedStateHandle = backStack.savedStateHandle,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToWebView = { url ->
                        navController.navigate(WebViewUrl(url))
                    },
                    goToSuccessScreen = { title, message, buttonText ->
                        navController.navigate(Success(message, title, buttonText, false))

                    }

                )
            }

            composable<WebViewUrl> { backStackEntry ->
                WebViewUrlScreen(backStackEntry.toRoute<WebViewUrl>().url) { transaction_id ->

                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "transaction_id",
                        transaction_id
                    )
                    navController.popBackStack()
                }
            }

            composable<AddressList> {
                AddressListScreen(goBack = {
                    navController.popBackStack()
                }, goBackToCheckout = {
                    navController.popBackStack()
                }, { id, address, phone ->
                    navController.navigate(AddressForm(id, address, phone))
                })
            }

            composable<AddressForm> {
                val addressData = it.toRoute<AddressForm>()
                AddressFormScreen(addressData.id, addressData.address, addressData.phone, {
                    navController.popBackStack()
                })
            }

            composable<ProductListing> { backStackEntry ->
                val data = backStackEntry.toRoute<ProductListing>()
                ProductListingScreen(data.categoryId, data.categoryName, onBackClick = {
                    navController.popBackStack()
                }, onSearchClick = {
                    navController.navigate(Search)
                }, onCartClick = {
                    navController.popBackStack<Home>(inclusive = false)
                    navController.navigate(Cart)
                }, onProductClick = { product ->
                    navController.navigate(ProductDetails(product))
                })
            }
            dialog<Search> {
                ProductSearchScreen(onDismiss = {
                    navController.popBackStack()
                }, onProductSelected = { product ->
                    navController.popBackStack()
                    navController.navigate(ProductDetails(NavigationUtils.encodeObject(product)))
                })
            }

            composable<Success> { backTrack ->
                val successData = backTrack.toRoute<Success>()
                SuccessScreen(successData.title, successData.message, successData.buttonText) {
                    navController.popBackStack(Home, false)
                }
            }

            composable<CleanersRegistration> {
                CleanersRegistrationScreen(goBack = {
                    navController.popBackStack()

                }, goToDataCapture = { data ->
                    navController.navigate(CleanersDocumentUpload(data))
                })
            }

            composable<CleanersDocumentUpload> { backStack ->
                val data = backStack.toRoute<CleanersDocumentUpload>()
                val request = decodeObject<RegisterCleanerRequest>(data.data)
                CleanersDocumentUploadScreen(request, goBack = {
                    navController.popBackStack()
                }, goToSuccessPage = { title, message, buttonText ->
                    navController.navigate(Success(message, title, buttonText, false))
                })
            }

            composable<CleaningService> {
                CleaningServiceScreen(goBack = {
                    navController.popBackStack()
                }, goToCleaning = { type ->
                    if (type.equals("deep")) {
                        navController.navigate(DeepCleaningForm)
                    } else {
                        navController.navigate(BasicCleaningForm)
                    }
                })
            }

            composable<DeepCleaningForm> {
                DeepCleaningFormScreen(goBack = {
                    navController.popBackStack()
                }, goToPrice = { result, data ->
                    navController.navigate(DeepCleaningPriceDetails(result, data))
                })
            }

            composable<DeepCleaningPriceDetails> { backTrack ->
                val data = backTrack.toRoute<DeepCleaningPriceDetails>()
                val deepCleaningFormData =
                    decodeObject<DeepCleaningFormData>(data.data)
                val deepCleaningPriceModel =
                    decodeObject<CleaningPriceModel>(data.response)
                DeepCleaningPriceDetailsScreen(
                    deepCleaningFormData,
                    deepCleaningPriceModel,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToNextPage = {
                        navController.navigate(DeepCleaningFormTwo(data.response, data.data))
                    })
            }

            composable<DeepCleaningFormTwo> { backTrack ->
                val data = backTrack.toRoute<DeepCleaningPriceDetails>()
                DeepCleaningFormTwoScreen(goBack = {
                    navController.popBackStack()
                }, goToCheckoutPage = { formData ->
                    navController.navigate(
                        DeepCleaningCheckout(
                            data.response, data.data, formData
                        )
                    )
                })
            }

            composable<DeepCleaningCheckout> { backTrace ->
                val data = backTrace.toRoute<DeepCleaningCheckout>()
                val deepCleaningData = decodeObject<DeepCleaningFormData>(data.data)
                val deepCleaningPriceModel =
                    decodeObject<CleaningPriceModel>(data.response)
                val deepCleaningFormModel =
                    decodeObject<DeepCleaningFormModel>(data.imageData)
                DeepCleaningCheckoutScreen(
                    deepCleaningData,
                    deepCleaningPriceModel,
                    deepCleaningFormModel,
                    savedStateHandle = backTrace.savedStateHandle,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToWebView = { url ->
                        navController.navigate(WebViewUrl(url))
                    },
                    goToSuccess = { title, message, buttonText ->
                        navController.navigate(Success(message, title, buttonText, false))
                    }
                )
            }

            composable<BasicCleaningForm> {
                BasicCleaningFormScreen(goBack = {
                    navController.popBackStack()
                }, goToPreview = { data, response ->
                    navController.navigate(BasicCleaningPriceDetails(data, response))
                })
            }

            composable<BasicCleaningPriceDetails> { backTrack ->
                val data = backTrack.toRoute<BasicCleaningPriceDetails>()
                val basicCleaningFormModel =
                    decodeObject<BasicCleaningFormModel>(data.data)
                val basicCleaningBreakdownModel =
                    decodeObject<BasicCleaningBreakdownModel>(data.response)
                BasicCleaningPriceDetailsScreen(
                    basicCleaningFormModel,
                    basicCleaningBreakdownModel,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToNextPage = {
                        navController.navigate(BasicCleaningFormTwo(data.data, data.response))
                    })
            }

            composable<BasicCleaningFormTwo> { backTrack ->
                val data = backTrack.toRoute<BasicCleaningPriceDetails>()
                val basicCleaningFormModel =
                    decodeObject<BasicCleaningFormModel>(data.data)
                val basicCleaningBreakdownModel =
                    decodeObject<BasicCleaningBreakdownModel>(data.response)

                BasicCleaningFormTwoScreen(
                    basicCleaningFormModel, basicCleaningBreakdownModel, goBack = {
                        navController.popBackStack()
                    },

                    goToCheckoutPage = { basicCleaningFormModel, basicCleaningBreakdownModel, basicCleaningFormTwoData ->
                        navController.navigate(
                            BasicCleaningCheckout(
                                basicCleaningFormModel,
                                basicCleaningBreakdownModel,
                                basicCleaningFormTwoData
                            )
                        )
                    })
            }

            composable<BasicCleaningCheckout> { backTrack ->
                val data = backTrack.toRoute<BasicCleaningCheckout>()
                val basicCleaningFormModel =
                    decodeObject<BasicCleaningFormModel>(data.data)
                val basicCleaningBreakdownModel =
                    decodeObject<BasicCleaningBreakdownModel>(data.response)
                val basicCleaningForm2Model =
                    decodeObject<BasicCleaningForm2Model>(data.inputData)


                BasicCleaningCheckoutScreen(
                    basicCleaningFormModel,
                    basicCleaningBreakdownModel,
                    basicCleaningForm2Model,
                    savedStateHandle = backTrack.savedStateHandle,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToWebView = { url ->
                        navController.navigate(WebViewUrl(url))
                    },
                    gotoSuccessPage = { title, message, buttonText ->
                        navController.navigate(Success(message, title, buttonText, false))
                    })
            }

            composable<PestControl> {
                PestControlScreen(goBack = {
                    navController.popBackStack()

                }, goToNextPage = { selection ->
                    if (selection.equals("residential")) {
                        navController.navigate(ResidentialPestControl)
                    } else {
                        navController.navigate(CompanyPestControl)
                    }
                })
            }

            composable<ResidentialPestControl> {
                PestControlResidentialFormScreen(goBack = {
                    navController.popBackStack()
                }, goToPricing = { amount, uniqueRef, formData ->
                    navController.navigate(
                        ResidentialPestControlPricing(
                            amount,
                            uniqueRef,
                            formData
                        )
                    )
                })
            }

            composable<ResidentialPestControlPricing> { backStack ->
                val data = backStack.toRoute<ResidentialPestControlPricing>()
                val formData =
                    decodeObject<PestControlResidentialFormModel>(data.formData)
                PestControlPriceScreen(data.amount, data.uniqueRef, formData, goBack = {
                    navController.popBackStack()
                }, goToNextPage = {
                    navController.navigate(
                        ResidentialPestControlFormTwo(
                            data.amount,
                            data.uniqueRef,
                            data.formData
                        )
                    )
                })
            }

            composable<ResidentialPestControlFormTwo> { backStack ->
                val data = backStack.toRoute<ResidentialPestControlPricing>()
                val formData =
                    decodeObject<PestControlResidentialFormModel>(data.formData)

                ResidentialPestControlFormTwoScreen(
                    formData,
                    goBack = {
                        navController.popBackStack()
                    }, goToNextPage = { formData2 ->
                        navController.navigate(
                            ResidentialPestControlCheckout(
                                data.amount,
                                data.uniqueRef,
                                data.formData,
                                formData2
                            )
                        )
                    })
            }

            composable<ResidentialPestControlCheckout> { backTrack ->
                val data = backTrack.toRoute<ResidentialPestControlCheckout>()
                val formData =
                    decodeObject<PestControlResidentialFormModel>(data.formData)
                val formData2 =
                    decodeObject<ResidentialPestControlFormTwoModel>(data.formData2)

                PestControlResidentialCheckoutScreen(
                    data.amount, data.uniqueRef, formData, formData2,
                    savedStateHandle = backTrack.savedStateHandle,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToWebView = { url ->
                        navController.navigate(WebViewUrl(url))
                    },
                    goToSuccess = { title, message, buttonText ->
                        navController.navigate(Success(message, title, buttonText, false))
                    })
            }

            composable<CompanyPestControl> {
                PestControlCommercialScreen(goBack = {
                    navController.popBackStack()
                }, goToSuccess = { title, message, buttonText ->
                    navController.navigate(Success(message, title, buttonText, false))
                })
            }

            composable<MobileToiletOnboarding> {
                MobileToiletOnboardingScreen(goBack = {
                    navController.popBackStack()
                }, goToNextPage = {
                    navController.navigate(MobileToilet)
                })
            }

            composable<MobileToilet> {
                MobileToiletService(goBack = {
                    navController.popBackStack()
                }, goToNextPage = { route ->
                    if (route.equals("event")) {
                        navController.navigate(MobileToiletEventFormOne)
                    } else {
                        navController.navigate(MobileToiletConstruction)
                    }
                })
            }

            composable<MobileToiletEventFormOne> {
                MobileToiletEventFormOneScreen(goBack = {
                    navController.popBackStack()
                }, goToNextPage = { mobileToiletEventFormOneFormData, toiletAvailabilityModel ->
                    navController.navigate(
                        MobileToiletEventFormTwo(
                            mobileToiletEventFormOneFormData,
                            toiletAvailabilityModel
                        )
                    )
                })
            }

            composable<MobileToiletEventFormTwo> {
                val data = it.toRoute<MobileToiletEventFormTwo>()
                val mobileToiletEventFormOneFormData =
                    decodeObject<MobileToiletEventFormOneFormData>(data.mobileToiletEventFormOneFormData)
                val toiletAvailabilityModel =
                    decodeObject<ToiletAvailabilityModel>(data.toiletAvailabilityModel)

                EventToiletFormTwoScreen(
                    mobileToiletEventFormOneFormData, toiletAvailabilityModel, goBack = {
                        navController.popBackStack()
                    },
                    goToPricePage = { toiletPriceModel ->
                        navController.navigate(
                            MobileToiletPricing(
                                data.mobileToiletEventFormOneFormData,
                                data.toiletAvailabilityModel,
                                toiletPriceModel
                            )
                        )
                    }
                )
            }

            composable<MobileToiletPricing> { backTrack ->
                val data = backTrack.toRoute<MobileToiletPricing>()
                val mobileToiletEventFormOneFormData =
                    decodeObject<MobileToiletEventFormOneFormData>(data.mobileToiletEventFormOneFormData)
                val toiletPriceModel = decodeObject<ToiletPriceModel>(data.toiletPriceModel)

                MobileToiletPricingScreen(
                    goBack = {
                        navController.popBackStack()
                    },
                    goToNextPage = {
                        navController.navigate(
                            MobileToiletFormThree(
                                data.mobileToiletEventFormOneFormData,
                                data.toiletAvailabilityModel,
                                data.toiletPriceModel
                            )
                        )
                    },
                    toiletPriceModel = toiletPriceModel,
                    mobileToiletEventFormOneFormData = mobileToiletEventFormOneFormData
                )
            }

            composable<MobileToiletFormThree> { backTrack ->
                val data = backTrack.toRoute<MobileToiletFormThree>()
                val mobileToiletEventFormOneFormData = decodeObject<MobileToiletEventFormOneFormData>(data.mobileToiletEventFormOneFormData)
                val toiletPriceModel = decodeObject<ToiletPriceModel>(data.toiletPriceModel)
                val toiletAvailabilityModel = decodeObject<ToiletAvailabilityModel>(data.toiletAvailabilityModel)

                MobileToiletFormThreeScreen( goBack = {
                    navController.popBackStack()
                }, goToNextPage = { mobileToiletFormThreeModel ->
                    navController.navigate(EventToiletCheckout(data.mobileToiletEventFormOneFormData, data.toiletAvailabilityModel, data.toiletPriceModel, mobileToiletFormThreeModel))
                })
            }

            composable<EventToiletCheckout> { backTrack->
                val data = backTrack.toRoute<EventToiletCheckout>()
                val mobileToiletEventFormOneFormData = decodeObject<MobileToiletEventFormOneFormData>(data.mobileToiletEventFormOneFormData)
                val toiletPriceModel = decodeObject<ToiletPriceModel>(data.toiletPriceModel)
                val toiletAvailabilityModel = decodeObject<ToiletAvailabilityModel>(data.toiletAvailabilityModel)
                val mobileToiletFormThreeModel = decodeObject<MobileToiletFormThreeModel>(data.mobileToiletFormThreeData)

                EventToiletCheckoutScreen(toiletPriceModel,mobileToiletFormThreeModel,
                    backTrack.savedStateHandle,
                    goBack = {
                        navController.popBackStack()
                    },
                    goToWebView = { url ->
                        navController.navigate(WebViewUrl(url))
                    },
                    goToSuccess = { title, message, buttonText ->
                        navController.navigate(Success(message, title, buttonText, false))
                    })
            }



            composable<MobileToiletConstruction> {
                ConstructionMobileToiletScreen(goBack = {
                    navController.popBackStack()
                }, goToSuccess = { title, message, buttonText ->
                    navController.navigate(Success(message, title, buttonText, false))
                })
            }
        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    data class BottomNavItem(
        val route: Any,
        val selectedIcon: DrawableResource,
        val unselectedIcon: DrawableResource,
        val label: String
    )

    val items = listOf(
        BottomNavItem(
            Home,
            DashboardBottomNav.Home.selectedIcon,
            DashboardBottomNav.Home.unselectedIcon,
            DashboardBottomNav.Home.label
        ), BottomNavItem(
            Booking,
            DashboardBottomNav.Booking.selectedIcon,
            DashboardBottomNav.Booking.unselectedIcon,
            DashboardBottomNav.Booking.label
        ), BottomNavItem(
            Cart,
            DashboardBottomNav.Cart.selectedIcon,
            DashboardBottomNav.Cart.unselectedIcon,
            DashboardBottomNav.Cart.label
        ), BottomNavItem(
            Academy,
            DashboardBottomNav.Training.selectedIcon,
            DashboardBottomNav.Training.unselectedIcon,
            DashboardBottomNav.Training.label
        ), BottomNavItem(
            Profile,
            DashboardBottomNav.Profile.selectedIcon,
            DashboardBottomNav.Profile.unselectedIcon,
            DashboardBottomNav.Profile.label
        )
    )

    val colorsPalette = LocalColorsPalette.current

    NavigationBar(containerColor = Color(0xFFFFFFFF)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route::class.qualifiedName

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            if (isSelected) item.selectedIcon else item.unselectedIcon
                        ), contentDescription = item.label
                    )
                },
                label = { Text(item.label, style = MaterialTheme.typography.bodySmall) },
                selected = isSelected,
                onClick = {
                    if (item.route == Home) {
                        navController.popBackStack<Home>(inclusive = false)
                    } else {
                        navController.popBackStack<Home>(inclusive = false)
                        navController.navigate(item.route)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorsPalette.titleLabelColor,
                    selectedTextColor = colorsPalette.titleLabelColor,
                    unselectedIconColor = colorsPalette.textGray,
                    unselectedTextColor = colorsPalette.textGray,
                    indicatorColor = colorsPalette.captionColor
                )
            )
        }
    }
}
