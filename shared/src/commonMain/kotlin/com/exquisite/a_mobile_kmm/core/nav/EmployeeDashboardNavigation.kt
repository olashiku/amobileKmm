package com.exquisite.a_mobile_kmm.core.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils.decodeObject
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.feature.employee.domain.model.AgentBooking
import com.exquisite.a_mobile_kmm.feature.employee.presenter.booking.BookingsListScreen
import com.exquisite.a_mobile_kmm.feature.employee.presenter.booking_details.EmployeeBookingDetailsScreen
import com.exquisite.a_mobile_kmm.feature.employee.presenter.home.EmployeeHomeScreen
import com.exquisite.a_mobile_kmm.feature.employee.presenter.profile.EmployeeProfileScreen
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.password_manager.PasswordManagerScreen
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.presenter.profile_form.ProfileFormScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun EmployeeDashboardNavigation(onLogout: () -> Unit = {}) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showBottomBar = currentRoute in listOf(
        EmployeeHome::class.qualifiedName,
        EmployeeProfile::class.qualifiedName
    )

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar, enter = slideInVertically(
                    initialOffsetY = { it }, // Slide up from bottom
                    animationSpec = tween(
                        durationMillis = 1000, easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 1000)
                ), exit = slideOutVertically(
                    targetOffsetY = { it }, // Slide down to bottom
                    animationSpec = tween(
                        durationMillis = 1000, easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(durationMillis = 300)
                ), content = { EmployeeBottomBar(navController) })

        }, containerColor = Color(0xFFFFFFFF), contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = EmployeeHome,
            modifier = Modifier.padding(padding)
        ) {

            composable<EmployeeHome> {
                EmployeeHomeScreen(goToBooking = { tag ->
                    navController.navigate(EmployeeBooking(tag))
                })
            }

            composable<EmployeeBooking> { backTrack ->
                val data = backTrack.toRoute<EmployeeBooking>()
                BookingsListScreen(data.bookingType, goBack = {
                    navController.popBackStack()
                }, toBookingDetails = { agentBooking ->
                    navController.navigate(EmployeeBookingDetails(agentBooking))
                })
            }

            composable<EmployeeBookingDetails> { backTrack ->
                val data = backTrack.toRoute<EmployeeBookingDetails>()
                val agentBooking = decodeObject<AgentBooking>(data.agentBooking)
                EmployeeBookingDetailsScreen(
                    agentBooking = agentBooking,
                    goBack = { navController.popBackStack() },
                    goToHomePage = {
                        navController.popBackStack<EmployeeHome>(inclusive = false)
                    }
                )
            }


            composable<EmployeeProfile> {
                EmployeeProfileScreen(onMenuItemClick = { label ->
                    when (label) {
                        "edit_profile" -> {
                            navController.navigate(ProfileForm)
                        }

                        "password_manager" -> {
                            navController.navigate(PasswordManager)
                        }

                        "logout" -> {
                            onLogout()
                        }
                    }
                })
            }

            composable<ProfileForm> {
                ProfileFormScreen(onBackClick = {
                    navController.popBackStack()
                })
            }

            composable<PasswordManager> {
                PasswordManagerScreen(onBackClick = {
                    navController.popBackStack()
                })
            }

        }
    }
}


@Composable
fun EmployeeBottomBar(navController: NavHostController) {
    data class EmployeeDashboardBottomNav(
        val route: Any,
        val selectedIcon: DrawableResource,
        val unselectedIcon: DrawableResource,
        val label: String
    )

    val items = listOf(
        EmployeeDashboardBottomNav(
            EmployeeHome,
            DashboardBottomNav.Home.selectedIcon,
            DashboardBottomNav.Home.unselectedIcon,
            DashboardBottomNav.Home.label
        ), EmployeeDashboardBottomNav(
            EmployeeProfile,
            DashboardBottomNav.Profile.selectedIcon,
            DashboardBottomNav.Profile.unselectedIcon,
            DashboardBottomNav.Profile.label
        )
    )

    val colorsPalette = LocalColorsPalette.current

    NavigationBar(containerColor = Color(0xFFFFFFFF)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route::class.qualifiedName

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            if (isSelected) item.selectedIcon else item.unselectedIcon
                        ), contentDescription = item.label
                    )
                },
                label = { Text(item.label, style = MaterialTheme.typography.bodySmall) },
                selected = isSelected,
                onClick = {
                    if (item.route == EmployeeHome) {
                        navController.popBackStack<EmployeeHome>(inclusive = false)
                    } else {
                        navController.popBackStack<EmployeeHome>(inclusive = false)
                        navController.navigate(item.route)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorsPalette.titleLabelColor,
                    selectedTextColor = colorsPalette.titleLabelColor,
                    unselectedIconColor = colorsPalette.textGray,
                    unselectedTextColor = colorsPalette.textGray,
                    indicatorColor = colorsPalette.captionColor
                )
            )
        }
    }
}
