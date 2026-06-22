package com.exquisite.a_mobile_kmm.feature.wallet.domain.model

data class TransactionModel(
    val id: Int,
    val narration: String,
    val drcr: String,
    val amount: Double,
    val transactionId: String,
    val createdAt: String,
    val updatedAt: String
)
