package com.exquisite.a_mobile_kmm.core.network

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    open val responseMessage: String,
    open val responseCode: String
)
