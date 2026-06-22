package com.exquisite.a_mobile_kmm.feature.auth.domain.model

data class LoginModel(
    val authorization: String,
    val role: String,
    val user: User
)
