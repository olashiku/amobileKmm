package com.exquisite.a_mobile_kmm.feature.wallet.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.wallet.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.wallet.data.remote.response.*
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    suspend fun getCustomerBalance(customerId: Int): Flow<Result<GetCustomerBalanceResponseDto>>
    suspend fun getCustomerTransactions(customerId: Int): Flow<Result<GetCustomerTransactionsResponseDto>>
    suspend fun initTopUpAccount(request: InitTopUpAccountRequestDto): Flow<Result<InitTopUpAccountResponseDto>>
    suspend fun completeTopUpAccount(request: CompleteTopUpAccountRequestDto): Flow<Result<CompleteTopUpAccountResponseDto>>
}
