package com.exquisite.a_mobile_kmm.feature.auth.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class InitForgotPasswordResponseDto(
    @Serializable(with = InitForgotPasswordDataSerializer::class)
    val data: InitForgotPasswordData? = null,
    val responseMessage: String,
    val responseCode: String
)

/**
 * Serializer for InitForgotPasswordData that handles empty JSON objects {} as null
 */
object InitForgotPasswordDataSerializer :
    EmptyObjectAsNullSerializer<InitForgotPasswordData>(InitForgotPasswordData.serializer())

@Serializable
data class InitForgotPasswordData(
    val uniqueRef: String
)
