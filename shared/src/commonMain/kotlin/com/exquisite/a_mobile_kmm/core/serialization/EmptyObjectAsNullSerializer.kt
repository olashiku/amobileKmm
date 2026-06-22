package com.exquisite.a_mobile_kmm.core.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject

/**
 * Generic serializer that handles empty JSON objects {} as null.
 * This is useful for APIs that return empty objects for error cases instead of null.
 *
 * Usage with inline reified type (recommended):
 * ```
 * @Serializable
 * data class LoginDataDto(val token: String, val user: UserDto)
 *
 * @Serializable
 * data class ResponseDto(
 *     @Serializable(with = LoginDataDtoSerializer::class)
 *     val data: LoginDataDto?
 * )
 *
 * object LoginDataDtoSerializer : EmptyObjectAsNullSerializer<LoginDataDto>(LoginDataDto.serializer())
 * ```
 *
 * @param T The type to deserialize to
 * @param serializer The serializer for type T
 */
open class EmptyObjectAsNullSerializer<T : Any>(
    private val serializer: KSerializer<T>
) : KSerializer<T?> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        "EmptyObjectAsNull<${serializer.descriptor.serialName}>"
    )

    override fun deserialize(decoder: Decoder): T? {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw SerializationException("EmptyObjectAsNullSerializer can only be used with Json format")

        return when (val element = jsonDecoder.decodeJsonElement()) {
            is JsonObject -> {
                // Empty object {} -> null
                if (element.isEmpty()) null
                else jsonDecoder.json.decodeFromJsonElement(serializer, element)
            }
            is JsonArray -> {
                // Empty array [] -> null
                if (element.isEmpty()) null
                else jsonDecoder.json.decodeFromJsonElement(serializer, element)
            }
            JsonNull -> null
            else -> jsonDecoder.json.decodeFromJsonElement(serializer, element)
        }  }

    override fun serialize(encoder: Encoder, value: T?) {
        if (value == null) {
            // Serialize null as an empty JSON object to match API format
            encoder.encodeSerializableValue(JsonObject.serializer(), JsonObject(emptyMap()))
        } else {
            encoder.encodeSerializableValue(serializer, value)
        }
    }
}
