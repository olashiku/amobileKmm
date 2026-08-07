package com.exquisite.a_mobile_kmm.feature.wallet.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.wallet.data.mapper.toDto
import com.exquisite.a_mobile_kmm.feature.wallet.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.CompleteTopUpAccountRequest
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.InitTopUpAccountRequest
import com.exquisite.a_mobile_kmm.feature.wallet.domain.repository.WalletRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class WalletRepositoryImpl(private val httpClient: HttpClient) : WalletRepository {

    override suspend fun getCustomerBalance(customerId: Int): Flow<Result<GetCustomerBalanceResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/customer/get_customer_balance?customerId=$customerId")
        }
    }

    override suspend fun getCustomerTransactions(customerId: Int): Flow<Result<GetCustomerTransactionsResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/customer/get_customer_transactions?customerId=$customerId")
        }
    }

    override suspend fun initTopUpAccount(request: InitTopUpAccountRequest): Flow<Result<InitTopUpAccountResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/customer/init_top_up_account") {
                setBody(request.toDto())
            }
        }
    }

    override suspend fun completeTopUpAccount(request: CompleteTopUpAccountRequest): Flow<Result<CompleteTopUpAccountResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/customer/complete_top_up_account") {
                setBody(request.toDto())
            }
        }
    }
}
