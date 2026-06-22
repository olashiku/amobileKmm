package com.exquisite.a_mobile_kmm.feature.auth.domain.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val profilePictureUrl: String?,
    val isActive: String,
    val role: Role,
    val createdAt: String,
    val updatedAt: String
)
