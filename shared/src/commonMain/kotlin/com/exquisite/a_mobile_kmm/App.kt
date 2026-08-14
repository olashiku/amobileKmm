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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun App() {
    AMobileTheme {

        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val dataStore: AMobileDataStore = koinInject()

        // Auth start destination - set once and stable
        val authStartDestination = remember { mutableStateOf<Any>(Splash) }
        var userRole by remember { mutableStateOf<String?>(null) }

        // Determine initial start destination - set once and never changes
        val initialStartDestination = remember {
            mutableStateOf<Any?>(null)
        }

        LaunchedEffect(Unit) {
            try {
                // Read initial values once using first() instead of collect()
                // This avoids iOS Flow collection issues
                println("App: Reading initial data...")

                val loggedIn = try {
                    dataStore.hasLoggedIn().first() ?: false
                } catch (e: Exception) {
                    println("App: Error reading hasLoggedIn: ${e.message}")
                    false
                }

                val role = try {
                    dataStore.getRole().first()
                } catch (e: Exception) {
                    println("App: Error reading role: ${e.message}")
                    null
                }

                println("App: hasLoggedIn: $loggedIn")
                println("App: role: $role")

                userRole = role

                // Set auth start destination (for nested AuthenticationNavigation)
                authStartDestination.value = when {
                    role == "CUSTOMER" -> Login
                    role.isNullOrEmpty() -> Splash
                    else -> Login
                }

                // Set main start destination
                initialStartDestination.value = if (loggedIn && !role.isNullOrEmpty()) {
                    when (role) {
                        "CUSTOMER" -> DashboardNav
                        "EMPLOYEE", "AGENT" -> EmployeeDashboardNav
                        else -> {
                            // Unknown role - log and redirect to auth
                            println("App: WARNING - Unknown role '$role', redirecting to auth")
                            AuthNav
                        }
                    }
                } else {
                    AuthNav
                }

                println("App: authStartDestination set to ${authStartDestination.value}")
                println("App: initialStartDestination set to ${initialStartDestination.value}")

            } catch (e: Exception) {
                println("App: Error in LaunchedEffect: ${e.message}")
                e.printStackTrace()
                // Fallback to default navigation
                authStartDestination.value = Splash
                initialStartDestination.value = AuthNav
            }
        }

        // Wait for initial data to load
        if (initialStartDestination.value == null) {
            return@AMobileTheme
        }

        NavHost(
            navController = navController,
            startDestination = initialStartDestination.value!!
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
                    startDestination = authStartDestination.value
                )
            }
            composable<DashboardNav> {
                DashboardNavigation(
                    onLogout = {
                        scope.launch {
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