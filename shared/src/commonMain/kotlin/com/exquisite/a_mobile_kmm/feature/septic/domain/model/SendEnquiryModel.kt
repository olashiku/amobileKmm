package com.exquisite.a_mobile_kmm.feature.septic.domain.model

data class SendEnquiryModel(
    val businessName: String,
    val contactPersonName: String,
    val contactPersonPhone: String,
    val companyEmail: String,
    val estimatedTankSize: String,
    val availableExecutionDate: String,
    val additionalMessage: String,
    val customerId: Int
)
