package com.exquisite.a_mobile_kmm

import com.exquisite.a_mobile_kmm.core.di.initKoin

/**
 * Helper function for iOS to initialize Koin
 * Called from Swift AppDelegate before any other initialization
 */
fun doInitKoin() {
    initKoin()
}
