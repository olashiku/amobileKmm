package com.exquisite.a_mobile_kmm.feature.wallet.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.wallet.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.CompleteTopUpAccountRequest
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.InitTopUpAccountRequest
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    suspend fun getCustomerBalance(customerId: Int): Flow<Result<GetCustomerBalanceResponseDto>>
    suspend fun getCustomerTransactions(customerId: Int): Flow<Result<GetCustomerTransactionsResponseDto>>
    suspend fun initTopUpAccount(request: InitTopUpAccountRequest): Flow<Result<InitTopUpAccountResponseDto>>
    suspend fun completeTopUpAccount(request: CompleteTopUpAccountRequest): Flow<Result<CompleteTopUpAccountResponseDto>>
}
