package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DeepCleaningFormData(
    val region: Pair<String,String> ?= null,
    val location: Pair<String,String>?= null,
    val typeOfApartment:  Pair<String,String>?= null,
    val numberOfRooms:  Pair<String,String>?= null,
    val cleaningType: Pair<String,String>?= null,
    val address:  Pair<String,String?>?= null
)

data class CleaningSummaryData(
    val title:String,
    val description:String
)

 fun getCleaningSummaryData(data:DeepCleaningFormData): List<CleaningSummaryData>{
     return listOf(
         CleaningSummaryData("Region",data.region?.first?:""),
         CleaningSummaryData("Location",data.location?.first?:""),
         CleaningSummaryData("Apartment Type",data.typeOfApartment?.first?:""),
         CleaningSummaryData("Number of Rooms",data.numberOfRooms?.first?:""),
     )
 }
