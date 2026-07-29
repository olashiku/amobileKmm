package com.exquisite.a_mobile_kmm.feature.septic.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import com.exquisite.a_mobile_kmm.core.screenUtils.to12HourFormat
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.SummaryData
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CheckoutItemModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.ResidentialPestControlFormTwoModel


fun getSepticPricingList(septicTruckSizeModel: SepticTruckSizeModel,
                         septicResidentialForm2Model: SepticResidentialForm2Model): List<SummaryData> {
    return listOf(
        SummaryData("Full Name", septicResidentialForm2Model.fullName),
        SummaryData("Email", septicResidentialForm2Model.email),
        SummaryData("Phone", septicResidentialForm2Model.phone),
        SummaryData("Address", septicResidentialForm2Model.address),
        SummaryData("Excavation Date", septicResidentialForm2Model.selectedDate?.fullDate?.formatToReadableDate()?:""),
        SummaryData("Excavation Time", septicResidentialForm2Model.selectedTime?.to12HourFormat()?:""),
        SummaryData("Number of Liter", septicTruckSizeModel.liter.toString()),
        SummaryData("Additional Message", if(septicResidentialForm2Model.additionalMessage.isEmpty()) "No additional message" else septicResidentialForm2Model.additionalMessage))
}



fun getBalances(amount:Double): List<CheckoutItemModel> {

    val totalAmount = amount
    val  taxAmount = totalAmount * 0.075
    return listOf(

        CheckoutItemModel(
            title = "Sub-Total",
            balance = totalAmount
        ),
        CheckoutItemModel(
            title = "Tax (7.5%)",
            balance = taxAmount
        )
    )
}


