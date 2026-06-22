package com.exquisite.a_mobile_kmm.feature.training.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class InitEnrollTrainingResponseDto(
    @Serializable(with = EnrollTrainingDataSerializer::class)
    val data: EnrollTrainingDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object EnrollTrainingDataSerializer :
    EmptyObjectAsNullSerializer<EnrollTrainingDataDto>(EnrollTrainingDataDto.serializer())

@Serializable
data class EnrollTrainingDataDto(
    val ref: String? = null,
    val paymentLink: String? = null
)
