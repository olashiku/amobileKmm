package com.exquisite.a_mobile_kmm.feature.training.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.SummaryData
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CheckoutItemModel


fun getTrainingList( trainingCourse: TrainingCourse,
                     trainingRegistrationModel: TrainingRegistrationModel,): List<SummaryData> {
    return listOf(
        SummaryData("Full Name", trainingRegistrationModel.fullName),
        SummaryData("Email", trainingRegistrationModel.email),
        SummaryData("Phone", trainingRegistrationModel.phone),
        SummaryData("Address", trainingRegistrationModel.address),
        SummaryData("Gender", trainingRegistrationModel.gender),
        SummaryData("Training Course", trainingCourse.title),
        SummaryData("Author",trainingCourse.author),
        SummaryData("Venue", trainingCourse.trainingVenue?:""),
        SummaryData("Start Date", trainingCourse.startDate?.formatToReadableDate()?:""),
        SummaryData("End Date", trainingCourse.endDate?.formatToReadableDate()?:""),
        )
}



fun getBalances(trainingCourse: TrainingCourse): List<CheckoutItemModel> {

    val totalAmount = trainingCourse.amount
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