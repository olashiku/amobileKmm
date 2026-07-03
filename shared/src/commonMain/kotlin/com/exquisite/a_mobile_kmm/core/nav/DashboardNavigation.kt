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
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.feature.address.presenter.address_form.AddressFormScreen
import com.exquisite.a_mobile_kmm.feature.address.presenter.address_list.AddressListScreen
import com.exquisite.a_mobile_kmm.feature.booking.presenter.booking.BookingScreen
import com.exquisite.a_mobile_kmm.feature.cart.presenter.CartScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list.CheckoutListScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home.HomeScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_details.ProductDetailsScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_listing.ProductListingScreen
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search.ProductSearchScreen
import com.exquisite.a_mobile_kmm.feature.settings_and_profile.presenter.profile.ProfileScreen
import com.exquisite.a_mobile_kmm.feature.training.presenter.training.TrainingScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun DashboardNavigation(onLogout: () -> Unit = {}) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showBottomBar = currentRoute in listOf(
        Home::class.qualifiedName,
        Booking::class.qualifiedName,
        Cart::class.qualifiedName,
        Academy::class.qualifiedName,
        Profile::class.qualifiedName
    )

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(
                    initialOffsetY = { it }, // Slide up from bottom
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 1000)
                ),
                exit =
                    slideOutVertically(
                        targetOffsetY = { it }, // Slide down to bottom
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(
                        animationSpec = tween(durationMillis = 300)
                    ),
                content = { BottomBar(navController) })

        }, containerColor = Color(0xFFFFFFFF),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(padding)
        ) {
            composable<Home> {
                HomeScreen(
                    goToSearchDialog = {
                        navController.navigate(Search)
                    },
                    goToCartScreen = {
                        navController.popBackStack<Home>(inclusive = false)
                        navController.navigate(Cart)
                    },
                    getCategoryProduct = { product ->
                        navController.navigate(ProductDetails(product))
                    },
                    goToProductListing = { categoryId, categoryName ->
                        navController.navigate(ProductListing(categoryId, categoryName))
                    }
                )
            }

            composable<Booking> {
                BookingScreen()
            }

            composable<Cart> {
                CartScreen(toCheckout = {
                    navController.navigate(CheckoutList)
                })
            }

            composable<Academy> {
                TrainingScreen()
            }

            composable<Profile> {
                ProfileScreen()
            }

            composable<ProductDetails> { backStackEntry ->
                showBottomBar = false
                val data = backStackEntry.toRoute<ProductDetails>()
                ProductDetailsScreen(NavigationUtils.decodeObject(data.productItem), onBackClick = {
                    navController.popBackStack()
                }, onSearchClick = {
                    navController.navigate(Search)
                }, onCartClick = {
                    navController.popBackStack<Home>(inclusive = false)
                    navController.navigate(Cart)
                })
            }

            composable<CheckoutList> {
                CheckoutListScreen({
                    navController.popBackStack()
                }, {
                    navController.navigate(AddressList)
                }, {
                    // continue button
                })
            }

            composable<AddressList> {
                AddressListScreen({
                    navController.popBackStack()
                },{
                    navController.navigate(AddressForm)
                })
            }

             composable<AddressForm>{
                 AddressFormScreen({
                     navController.popBackStack()
                 })
             }

            composable<ProductListing> { backStackEntry ->
                val data = backStackEntry.toRoute<ProductListing>()
                ProductListingScreen(
                    data.categoryId, data.categoryName,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSearchClick = {
                        navController.navigate(Search)
                    },
                    onCartClick = {
                        navController.popBackStack<Home>(inclusive = false)
                        navController.navigate(Cart)
                    },
                    onProductClick = { product ->
                        navController.navigate(ProductDetails(product))
                    }
                )
            }
            dialog<Search> {
                ProductSearchScreen(
                    onDismiss = {
                        navController.popBackStack()
                    },
                    onProductSelected = { product ->
                        navController.popBackStack()
                        navController.navigate(ProductDetails(NavigationUtils.encodeObject(product)))
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    data class BottomNavItem(
        val route: Any,
        val selectedIcon: DrawableResource,
        val unselectedIcon: DrawableResource,
        val label: String
    )

    val items = listOf(
        BottomNavItem(
            Home,
            DashboardBottomNav.Home.selectedIcon,
            DashboardBottomNav.Home.unselectedIcon,
            DashboardBottomNav.Home.label
        ),
        BottomNavItem(
            Booking,
            DashboardBottomNav.Booking.selectedIcon,
            DashboardBottomNav.Booking.unselectedIcon,
            DashboardBottomNav.Booking.label
        ),
        BottomNavItem(
            Cart,
            DashboardBottomNav.Cart.selectedIcon,
            DashboardBottomNav.Cart.unselectedIcon,
            DashboardBottomNav.Cart.label
        ),
        BottomNavItem(
            Academy,
            DashboardBottomNav.Training.selectedIcon,
            DashboardBottomNav.Training.unselectedIcon,
            DashboardBottomNav.Training.label
        ),
        BottomNavItem(
            Profile,
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
                        ),
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label, style = MaterialTheme.typography.bodySmall) },
                selected = isSelected,
                onClick = {
                    if (item.route == Home) {
                        navController.popBackStack<Home>(inclusive = false)
                    } else {
                        navController.popBackStack<Home>(inclusive = false)
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