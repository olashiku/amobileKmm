package com.exquisite.a_mobile_kmm.feature.auth.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

@Serializable
data class UploadResponseDto(
    @Serializable(with = UploadDataSerializer::class)
    val data: List<String>? = null,
    val responseMessage: String,
    val responseCode: String
)

/**
 * Serializer for upload data that handles empty arrays [] and empty objects {} as null
 */
object UploadDataSerializer :
    EmptyObjectAsNullSerializer<List<String>>(ListSerializer(String.serializer()))


