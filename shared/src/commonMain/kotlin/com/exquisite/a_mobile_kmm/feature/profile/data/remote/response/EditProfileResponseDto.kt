package com.exquisite.a_mobile_kmm.feature.profile.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class EditProfileResponseDto(
    val responseMessage: String = "",
    val responseCode: String = ""
)
