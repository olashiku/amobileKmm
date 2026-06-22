package com.exquisite.a_mobile_kmm.core.di

import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.network.ApiConfig
import com.exquisite.a_mobile_kmm.core.network.HttpClientFactory
import com.exquisite.a_mobile_kmm.feature.auth.data.repository.AuthRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.auth.domain.repository.AuthRepository
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.CompleteForgotPasswordUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.CompleteRegisterUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.InitForgotPasswordUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.InitRegisterUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.LoginUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.ResendOtpUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.UploadFileUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.ValidateOtpUseCase
import com.exquisite.a_mobile_kmm.feature.auth.presenter.create_password.CreatePasswordViewModel
import com.exquisite.a_mobile_kmm.feature.auth.presenter.forgot_password.ForgotPasswordViewModel
import com.exquisite.a_mobile_kmm.feature.auth.presenter.login.LoginViewModel
import com.exquisite.a_mobile_kmm.feature.auth.presenter.otp.OtpViewModel
import com.exquisite.a_mobile_kmm.feature.auth.presenter.signup.SignupViewModel
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.UploadImageViewModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.repository.EcommerceRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.repository.EcommerceRepository
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.CompletePaymentUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.CreateOrderUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.DebitFromWalletUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetAllProductsUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetAppProductsUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetProductsByCategoryUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.InitPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list.CheckoutListViewModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.deliver_option.DeliverOptionViewModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home.HomeViewModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_listing.ProductListingViewModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search.ProductSearchViewModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.search_product.SearchProductViewModel
import com.exquisite.a_mobile_kmm.feature.booking.data.repository.BookingRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.booking.domain.repository.BookingRepository
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetCleaningBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetCustomerBookingsUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetPestControlBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetSepticBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.GetToiletBookingUseCase
import com.exquisite.a_mobile_kmm.feature.booking.domain.usecase.RateAndReviewUseCase
import com.exquisite.a_mobile_kmm.feature.booking.presenter.booking.BookingViewModel
import com.exquisite.a_mobile_kmm.feature.booking.presenter.booking_details.BookingDetailsViewModel
import com.exquisite.a_mobile_kmm.feature.order.data.repository.OrderRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.order.domain.repository.OrderRepository
import com.exquisite.a_mobile_kmm.feature.order.domain.usecase.GetCustomerOrdersUseCase
import com.exquisite.a_mobile_kmm.feature.order.presenter.order_listing.OrderListingViewModel
import com.exquisite.a_mobile_kmm.feature.training.data.repository.TrainingRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.training.domain.repository.TrainingRepository
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.CompleteEnrollTrainingUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.EnrollTrainingByAccountBalanceUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.GetActiveCoursesAndTrainingUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.InitEnrollTrainingUseCase
import com.exquisite.a_mobile_kmm.feature.training.presenter.training.TrainingViewModel
import com.exquisite.a_mobile_kmm.feature.training.presenter.training_registration.TrainingRegistrationViewModel
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.repository.CleanersRegistrationRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.repository.CleanersRegistrationRepository
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.usecase.RegisterCleanerUseCase
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration.CleanersRegistrationViewModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.repository.CleaningServiceRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.repository.CleaningServiceRepository
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form.BasicCleaningFormViewModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_checkout.DeepCleaningCheckoutViewModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form.DeepCleaningFormViewModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.repository.MobileToiletRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.repository.MobileToiletRepository
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.CheckToiletAvailabilityUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.CompleteToiletPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.DebitFromAccountUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.GetEventTypeUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.GetStandardToiletsListUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.GetToiletPriceUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.InitToiletPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.RequestForConstructionUseCase
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.construction_mobile_toilet.ConstructionMobileToiletViewModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.event_toilet_checkout.EventToiletCheckoutViewModel
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.event_toilet_form.EventToiletFormViewModel
import com.exquisite.a_mobile_kmm.feature.janitorial.data.repository.JanitorialRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.repository.JanitorialRepository
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.usecase.CreateJanitorialUseCase
import com.exquisite.a_mobile_kmm.feature.janitorial.presenter.janitorial.JanitorialViewModel
import com.exquisite.a_mobile_kmm.feature.pest_control.data.repository.PestControlRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.repository.PestControlRepository
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.CompletePestControlPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.DebitFromWalletPestControlUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.GetPestControlPriceUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.GetServiceListUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.InitPestControlPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.RequestCommercialPestControlUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_commercial.PestControlCommercialViewModel
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_checkout.PestControlResidentialCheckoutViewModel
import com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form.PestControlResidentialFormViewModel
import com.exquisite.a_mobile_kmm.feature.septic.data.repository.SepticRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.septic.domain.repository.SepticRepository
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.CompleteSepticPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.DebitFromAccountSepticUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.GetSepticTruckSizeUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.InitSepticPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.SendEnquiryUseCase
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form.SepticCommercialFormViewModel
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_checkout.SepticResidentialCheckoutViewModel
import com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form.SepticResidentialFormViewModel
import com.exquisite.a_mobile_kmm.feature.address.data.repository.AddressRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.address.domain.repository.AddressRepository
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.CreateAddressUseCase
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.DeleteAddressUseCase
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.GetAddressesUseCase
import com.exquisite.a_mobile_kmm.feature.address.domain.usecase.UpdateAddressUseCase
import com.exquisite.a_mobile_kmm.feature.address.presenter.address_form.AddressFormViewModel
import com.exquisite.a_mobile_kmm.feature.address.presenter.address_list.AddressListViewModel
import com.exquisite.a_mobile_kmm.feature.profile.data.repository.ProfileRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.profile.domain.repository.ProfileRepository
import com.exquisite.a_mobile_kmm.feature.profile.domain.usecase.ChangePasswordUseCase
import com.exquisite.a_mobile_kmm.feature.profile.domain.usecase.EditProfileUseCase
import com.exquisite.a_mobile_kmm.feature.profile.presenter.profile_form.ProfileFormViewModel
import com.exquisite.a_mobile_kmm.feature.wallet.data.repository.WalletRepositoryImpl
import com.exquisite.a_mobile_kmm.feature.wallet.domain.repository.WalletRepository
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.CompleteTopUpAccountUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.GetCustomerBalanceUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.GetCustomerTransactionsUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.InitTopUpAccountUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet.WalletViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config : KoinAppDeclaration? = null){
    startKoin{
        config?.invoke(this)
        modules(platformModule,sharedModule)
    }
}

expect var platformModule: Module

val sharedModule :Module = module {

    single { HttpClientFactory.createHttpClient(get(), ApiConfig.BASE_URL,get()) }
    single { AMobileDataStore(get()) }

    //repository
    single<AuthRepository>{ AuthRepositoryImpl(get()) }
    single<EcommerceRepository>{ EcommerceRepositoryImpl(get()) }
    single<OrderRepository>{ OrderRepositoryImpl(get()) }
    single<BookingRepository>{ BookingRepositoryImpl(get()) }
    single<TrainingRepository>{ TrainingRepositoryImpl(get()) }
    single<CleanersRegistrationRepository>{ CleanersRegistrationRepositoryImpl(get()) }
    single<CleaningServiceRepository>{ CleaningServiceRepositoryImpl(get()) }
    single<MobileToiletRepository>{ MobileToiletRepositoryImpl(get()) }
    single<JanitorialRepository>{ JanitorialRepositoryImpl(get()) }
    single<PestControlRepository>{ PestControlRepositoryImpl(get()) }
    single<SepticRepository>{ SepticRepositoryImpl(get()) }
    single<AddressRepository>{ AddressRepositoryImpl(get()) }
    single<ProfileRepository>{ ProfileRepositoryImpl(get()) }
    single<WalletRepository>{ WalletRepositoryImpl(get()) }

    //usecase
    single{ InitRegisterUseCase(get()) }
    single{ InitForgotPasswordUseCase(get()) }
    single{ ResendOtpUseCase(get()) }
    single{ LoginUseCase(get(),get()) }
    single{ CompleteForgotPasswordUseCase(get()) }
    single{ CompleteRegisterUseCase(get()) }
    single{ ValidateOtpUseCase(get()) }
    single{UploadFileUseCase(get())}
    single{ GetAppProductsUseCase(get()) }
    single{ GetProductsByCategoryUseCase(get()) }
    single{ GetAllProductsUseCase(get()) }
    single{ CreateOrderUseCase(get()) }
    single{ InitPaymentUseCase(get()) }
    single{ DebitFromWalletUseCase(get()) }
    single{ CompletePaymentUseCase(get()) }
    single{ GetCustomerOrdersUseCase(get()) }
    single{ GetCustomerBookingsUseCase(get()) }
    single{ GetCleaningBookingUseCase(get()) }
    single{ GetSepticBookingUseCase(get()) }
    single{ GetPestControlBookingUseCase(get()) }
    single{ GetToiletBookingUseCase(get()) }
    single{ RateAndReviewUseCase(get()) }
    single{ GetActiveCoursesAndTrainingUseCase(get()) }
    single{ InitEnrollTrainingUseCase(get()) }
    single{ CompleteEnrollTrainingUseCase(get()) }
    single{ EnrollTrainingByAccountBalanceUseCase(get()) }
    single{ RegisterCleanerUseCase(get()) }
    single{ FindAllRegionsUseCase(get()) }
    single{ FindLocationByRegionUseCase(get()) }
    single{ FindApartmentTypeUseCase(get()) }
    single{ FindCleaningTypeUseCase(get()) }
    single{ FindNumberOfRoomsUseCase(get()) }
    single{ GetCleaningPriceUseCase(get()) }
    single{ DebitFromWalletDeepCleaningPaymentUseCase(get()) }
    single{ InitDeepCleaningPaymentUseCase(get()) }
    single{ CompleteDeepCleaningPaymentUseCase(get()) }
    single{ GetBasicCleaningLocationsUseCase(get()) }
    single{ RequestForConstructionUseCase(get()) }
    single{ InitToiletPaymentUseCase(get()) }
    single{ GetToiletPriceUseCase(get()) }
    single{ CompleteToiletPaymentUseCase(get()) }
    single{ DebitFromAccountUseCase(get()) }
    single{ GetStandardToiletsListUseCase(get()) }
    single{ GetEventTypeUseCase(get()) }
    single{ CheckToiletAvailabilityUseCase(get()) }
    single{ CreateJanitorialUseCase(get()) }
    single{ RequestCommercialPestControlUseCase(get()) }
    single{ GetServiceListUseCase(get()) }
    single{ GetPestControlPriceUseCase(get()) }
    single{ DebitFromWalletPestControlUseCase(get()) }
    single{ InitPestControlPaymentUseCase(get()) }
    single{ CompletePestControlPaymentUseCase(get()) }
    single{ GetSepticTruckSizeUseCase(get()) }
    single{ InitSepticPaymentUseCase(get()) }
    single{ DebitFromAccountSepticUseCase(get()) }
    single{ SendEnquiryUseCase(get()) }
    single{ CompleteSepticPaymentUseCase(get()) }
    single{ GetAddressesUseCase(get()) }
    single{ UpdateAddressUseCase(get()) }
    single{ CreateAddressUseCase(get()) }
    single{ DeleteAddressUseCase(get()) }
    single{ EditProfileUseCase(get()) }
    single{ ChangePasswordUseCase(get()) }
    single{ GetCustomerBalanceUseCase(get()) }
    single{ GetCustomerTransactionsUseCase(get()) }
    single{ InitTopUpAccountUseCase(get()) }
    single{ CompleteTopUpAccountUseCase(get()) }

    //viewModel
    single{ SignupViewModel(get()) }
    single{ LoginViewModel(get()) }
    single{ OtpViewModel(get(),get())}
    single{ UploadImageViewModel(get(),get()) }
    single{ForgotPasswordViewModel(get())}
    single{ CreatePasswordViewModel(get())}
    single{ HomeViewModel(get()) }
    single{ ProductListingViewModel(get()) }
    single{ ProductSearchViewModel(get()) }
    single{ SearchProductViewModel(get()) }
    single{ CheckoutListViewModel(get()) }
    single{ DeliverOptionViewModel(get(), get(), get()) }
    single{ OrderListingViewModel(get()) }
    single{ BookingViewModel(get()) }
    single{ BookingDetailsViewModel(get(), get(), get(), get(), get()) }
    single{ TrainingViewModel(get()) }
    single{ TrainingRegistrationViewModel(get(), get(), get()) }
    single{ CleanersRegistrationViewModel(get()) }
    single{ DeepCleaningFormViewModel(get(), get(), get(), get(), get(), get(), get()) }
    single{ DeepCleaningCheckoutViewModel(get(), get()) }
    single{ BasicCleaningFormViewModel(get()) }
    single{ ConstructionMobileToiletViewModel(get()) }
    single{ EventToiletCheckoutViewModel(get(), get(), get(), get(), get()) }
    single{ EventToiletFormViewModel(get(), get()) }
    single{ JanitorialViewModel(get()) }
    single{ PestControlCommercialViewModel(get()) }
    single{ PestControlResidentialFormViewModel(get(), get()) }
    single{ PestControlResidentialCheckoutViewModel(get(), get(), get()) }
    single{ SepticResidentialFormViewModel(get()) }
    single{ SepticResidentialCheckoutViewModel(get(), get(), get()) }
    single{ SepticCommercialFormViewModel(get()) }
    single{ AddressListViewModel(get(), get()) }
    single{ AddressFormViewModel(get(), get()) }
    single{ ProfileFormViewModel(get(), get()) }
    single{ WalletViewModel(get(), get(), get(), get()) }

}
