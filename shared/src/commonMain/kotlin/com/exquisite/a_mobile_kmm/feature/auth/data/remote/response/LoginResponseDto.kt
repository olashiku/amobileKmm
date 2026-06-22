package com.exquisite.a_mobile_kmm.feature.auth.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @Serializable(with = LoginDataDtoSerializer::class)
    val data: LoginDataDto? = null,
    val responseMessage: String,
    val responseCode: String
)

/**
 * Serializer for LoginDataDto that handles empty JSON objects {} as null
 */
object LoginDataDtoSerializer :
    EmptyObjectAsNullSerializer<LoginDataDto>(LoginDataDto.serializer())

@Serializable
data class LoginDataDto(
    val auth: String? = null,
    val role: String? = null,
    @Serializable(with = UserDtoSerializer::class)
    val user: UserDto? = null
)

/**
 * Serializer for UserDto that handles empty JSON objects {} as null
 */
object UserDtoSerializer :
    EmptyObjectAsNullSerializer<UserDto>(UserDto.serializer())

@Serializable
data class UserDto(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val profilePictureUrl:String? = null,
    val isActive: String? = null,
    @Serializable(with = RoleDtoSerializer::class)
    val role: RoleDto? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * Serializer for RoleDto that handles empty JSON objects {} as null
 */
object RoleDtoSerializer :
    EmptyObjectAsNullSerializer<RoleDto>(RoleDto.serializer())

@Serializable
data class RoleDto(
    val role: String? = null,
    val id: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)
