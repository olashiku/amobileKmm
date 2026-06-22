package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form

import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.*

sealed class DeepCleaningFormState {
    data object Idle : DeepCleaningFormState()
    data object Loading : DeepCleaningFormState()
    data class RegionsSuccess(val regions: List<RegionModel>) : DeepCleaningFormState()
    data class LocationsSuccess(val locations: List<LocationModel>) : DeepCleaningFormState()
    data class ApartmentTypesSuccess(val apartmentTypes: List<ApartmentTypeModel>) : DeepCleaningFormState()
    data class CleaningTypesSuccess(val cleaningTypes: List<CleaningTypeModel>) : DeepCleaningFormState()
    data class NumberOfRoomsSuccess(val numberOfRooms: List<NumberOfRoomsModel>) : DeepCleaningFormState()
    data class PriceSuccess(val price: CleaningPriceModel) : DeepCleaningFormState()
    data class PaymentSuccess(val paymentResponse: PaymentResponseModel) : DeepCleaningFormState()
    data class Error(val message: String) : DeepCleaningFormState()
}
