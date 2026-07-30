package com.exquisite.a_mobile_kmm.feature.training.data.mapper

import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.CompleteEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.EnrollTrainingByAccountBalanceRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.InitEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.domain.model.CompleteEnrollTrainingRequest
import com.exquisite.a_mobile_kmm.feature.training.domain.model.EnrollTrainingByAccountBalanceRequest
import com.exquisite.a_mobile_kmm.feature.training.domain.model.InitEnrollTrainingRequest

object TrainingRequestMapper {

    fun InitEnrollTrainingRequest.toDto(): InitEnrollTrainingRequestDto {
        return InitEnrollTrainingRequestDto(
            trainingId = this.trainingId,
            customerId = this.customerId,
            fullName = this.fullName,
            email = this.email,
            phone = this.phone,
            address = this.address,
            gender = this.gender
        )
    }

    fun CompleteEnrollTrainingRequest.toDto(): CompleteEnrollTrainingRequestDto {
        return CompleteEnrollTrainingRequestDto(
            customerId = this.customerId,
            ref = this.ref,
            txnRef = this.txnRef
        )
    }

    fun EnrollTrainingByAccountBalanceRequest.toDto(): EnrollTrainingByAccountBalanceRequestDto {
        return EnrollTrainingByAccountBalanceRequestDto(
            trainingId = this.trainingId,
            customerId = this.customerId,
            fullName = this.fullName,
            email = this.email,
            phone = this.phone,
            address = this.address,
            gender = this.gender
        )
    }
}
