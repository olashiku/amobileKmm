package com.exquisite.a_mobile_kmm.feature.auth.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class InitRegisterResponseDto(
    @Serializable(with = InitRegisterDataSerializer::class)
    val data: InitRegisterData? = null,
    val responseMessage: String,
    val responseCode: String,
)

/**
 * Serializer for CreatePasswordData that handles empty JSON objects {} as null
 */
object InitRegisterDataSerializer :
    EmptyObjectAsNullSerializer<InitRegisterData>(InitRegisterData.serializer())

@Serializable
data class InitRegisterData(
    val uniqueRef: String
)