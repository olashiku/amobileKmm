package com.exquisite.a_mobile_kmm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.nav.AuthNav
import com.exquisite.a_mobile_kmm.core.nav.AuthenticationNavigation
import com.exquisite.a_mobile_kmm.core.nav.DashboardNav
import com.exquisite.a_mobile_kmm.core.nav.DashboardNavigation
import com.exquisite.a_mobile_kmm.core.nav.Login
import com.exquisite.a_mobile_kmm.core.nav.Splash
import com.exquisite.a_mobile_kmm.core.theme.AMobileTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun App() {
    AMobileTheme {

        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val dataStore: AMobileDataStore = koinInject()

        var authStartDestination by remember { mutableStateOf<Any?>(null) }
        var isLoggedIn by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            dataStore.hasLoggedIn().collect { loggedIn ->
                println("App: hasLoggedIn: $loggedIn")
                isLoggedIn = loggedIn ?: false
            }
        }

        NavHost(navController = navController,
            startDestination = if(isLoggedIn) DashboardNav else AuthNav
        ) {

            composable<AuthNav> {
                AuthenticationNavigation(
                    goToDashboard = {
                        navController.navigate(DashboardNav){
                            popUpTo(AuthNav) { inclusive = true }
                        }
                    },
                    startDestination = Splash
                )
            }
            composable<DashboardNav> {
                DashboardNavigation(
                    onLogout = {
                        scope.launch {
                            // Clear all user data from DataStore
                            //    dataStore.clearAllData()

                            // Set start destination to Login screen
                            authStartDestination = Login

                            // Navigate back to Auth screen
                            navController.navigate(AuthNav) {
                                popUpTo(DashboardNav) { inclusive = true }
                            }
                        }
                    }
                )
            }

        }
    }
}