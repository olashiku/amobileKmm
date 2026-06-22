package com.exquisite.a_mobile_kmm.core.network

object ApiConfig {
    const val BASE_URL = "https://d23n561hg6.execute-api.us-east-1.amazonaws.com/Stage/"

    // Timeout configurations
    // Standard timeouts for regular API calls
    const val CONNECT_TIMEOUT = 15000L
    const val REQUEST_TIMEOUT = 50000L
    const val SOCKET_TIMEOUT = 50000L
    
    // Extended timeouts for long-running operations (e.g., Google Virtual Try-On API)
    // Virtual Try-On takes approximately 90 seconds, so we set timeout to 120 seconds for safety margin
    const val EXTENDED_REQUEST_TIMEOUT = 120000L
    const val EXTENDED_SOCKET_TIMEOUT = 120000L
}
