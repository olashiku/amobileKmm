package com.exquisite.a_mobile_kmm.feature.auth.data.mapper

import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.LoginResponseDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.RoleDto
import com.exquisite.a_mobile_kmm.feature.auth.data.remote.response.UserDto
import com.exquisite.a_mobile_kmm.feature.auth.domain.model.LoginModel
import com.exquisite.a_mobile_kmm.feature.auth.domain.model.Role
import com.exquisite.a_mobile_kmm.feature.auth.domain.model.User

/**
 * Maps LoginResponseDto to LoginModel domain model
 * Returns null if any required fields are missing
 */
fun LoginResponseDto.toLoginModel(): LoginModel? {
    val loginData = data ?: return null
    return if (loginData.auth != null && loginData.role != null && loginData.user != null) {
        val domainUser = loginData.user.toDomainModel() ?: return null
        LoginModel(
            authorization = loginData.auth,
            role = loginData.role,
            user = domainUser
        )
    } else {
        null
    }
}

/**
 * Maps UserDto to User domain model
 * Returns null if any required fields are missing
 */
fun UserDto.toDomainModel(): User? {
    return if (id != null &&
               firstName != null &&
               lastName != null &&
               email != null &&
               phone != null &&
               isActive != null &&
               role != null &&
               createdAt != null &&
               updatedAt != null) {
        val domainRole = role.toDomainModel() ?: return null
        User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            profilePictureUrl = profilePictureUrl,
            isActive = isActive,
            role = domainRole,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

/**
 * Maps RoleDto to Role domain model
 * Returns null if any required fields are missing
 */
fun RoleDto.toDomainModel(): Role? {
    return if (role != null && id != null && created_at != null && updated_at != null) {
        Role(
            role = role,
            id = id,
            createdAt = created_at,
            updatedAt = updated_at
        )
    } else {
        null
    }
}
