package com.exquisite.a_mobile_kmm

import androidx.compose.runtime.Composable
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
import com.exquisite.a_mobile_kmm.core.nav.EmployeeDashboardNav
import com.exquisite.a_mobile_kmm.core.nav.EmployeeDashboardNavigation
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
        var userRole by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            dataStore.hasLoggedIn().collect { loggedIn ->
                println("App: hasLoggedIn: $loggedIn")
                isLoggedIn = loggedIn ?: false
            }

            dataStore.getRole().collect { role ->
                println("App: role: $role")
                userRole = role
                if (role == "CUSTOMER") {
                    authStartDestination = DashboardNav
                } else {
                    authStartDestination = EmployeeDashboardNav
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) {
                if (userRole == "CUSTOMER") DashboardNav else EmployeeDashboardNav
            } else {
                AuthNav
            }
        ) {

            composable<AuthNav> {
                AuthenticationNavigation(
                    goToDashboard = {
                        navController.navigate(DashboardNav) {
                            popUpTo(AuthNav) { inclusive = true }
                        }
                    },
                    goToEmployeeDashboard = {
                        navController.navigate(EmployeeDashboardNav) {
                            popUpTo(AuthNav) { inclusive = true }
                        }
                    },
                    startDestination = authStartDestination ?: Splash
                )
            }
            composable<DashboardNav> {
                DashboardNavigation(
                    onLogout = {
                        scope.launch {

                            // Set start destination to Login screen
                            authStartDestination = Login

                            dataStore.saveHasLoggedIn(false)
                            dataStore.saveRole("")
                            navController.navigate(AuthNav) {
                                popUpTo(DashboardNav) { inclusive = true }
                            }
                        }
                    }
                )
            }

            composable<EmployeeDashboardNav> {
                EmployeeDashboardNavigation(
                    onLogout = {
                        scope.launch {
                            authStartDestination = Login
                            dataStore.saveHasLoggedIn(false)
                            dataStore.saveRole("")
                            navController.navigate(AuthNav) {
                                popUpTo(EmployeeDashboardNav) { inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    }
}