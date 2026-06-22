package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetEventTypeResponseDto(
    @Serializable(with = EventTypeListSerializer::class)
    val data: List<EventTypeDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object EventTypeListSerializer :
    EmptyObjectAsNullSerializer<List<EventTypeDto>>(ListSerializer(EventTypeDto.serializer()))

@Serializable
data class EventTypeDto(
    val id: Int,
    val name: String
)
