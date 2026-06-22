package com.exquisite.a_mobile_kmm.feature.wallet.data.mapper

import com.exquisite.a_mobile_kmm.feature.wallet.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.*

fun GetCustomerBalanceResponseDto.toBalanceModel(): BalanceModel? {
    return data?.let {
        BalanceModel(
            id = it.id,
            balance = it.balance,
            createdAt = it.created_at,
            updatedAt = it.updated_at
        )
    }
}

fun GetCustomerTransactionsResponseDto.toTransactionModelList(): List<TransactionModel>? {
    return data?.map { it.toTransactionModel() }
}

fun TransactionDto.toTransactionModel(): TransactionModel {
    return TransactionModel(
        id = id,
        narration = narration,
        drcr = drcr,
        amount = amount,
        transactionId = transaction_id,
        createdAt = created_at,
        updatedAt = updated_at
    )
}

fun InitTopUpAccountResponseDto.toTopUpDataModel(): TopUpDataModel? {
    return data?.let {
        TopUpDataModel(
            ref = it.ref,
            paymentLink = it.paymentLink
        )
    }
}

fun CompleteTopUpAccountResponseDto.toBalanceModel(): BalanceModel? {
    return data?.let {
        BalanceModel(
            id = it.id,
            balance = it.balance,
            createdAt = it.created_at,
            updatedAt = it.updated_at
        )
    }
}
