package com.exquisite.a_mobile_kmm.feature.settings_and_profile.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
