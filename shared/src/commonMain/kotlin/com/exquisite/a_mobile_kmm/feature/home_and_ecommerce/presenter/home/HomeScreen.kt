package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.avatar_line
import amobilekmm.shared.generated.resources.cart_icon
import amobilekmm.shared.generated.resources.magnifer_icon
import amobilekmm.shared.generated.resources.notification_icon
import amobilekmm.shared.generated.resources.settings_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screenUtils.getTimeBasedGreeting
import com.exquisite.a_mobile_kmm.core.screen_components.AvatarIcon
import com.exquisite.a_mobile_kmm.core.screen_components.Banner
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.screen_components.LinerBackground
import com.exquisite.a_mobile_kmm.core.screen_components.MenuItem
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold20
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CategoryProduct
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.DashboardModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.getDashboardModel
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    goToSearchDialog: () -> Unit = {},
    goToCartScreen: () -> Unit = {},
    getCategoryProduct: (String) -> Unit,
    goToProductListing: (Int, String) -> Unit,
    goToCleanersRegistration: () -> Unit = {},
    goToMenuItem: (String) -> Unit = {},
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    modifier : Modifier = Modifier
) {

    val (snackBar, snackBarHostState) = rememberSnackBar()

    // states
    val homeState = viewModel.homeState.collectAsStateWithLifecycle()
    val cartState = viewModel.cartState.collectAsStateWithLifecycle()
    val customerNameState = viewModel.customerName.collectAsStateWithLifecycle()
    val profilePictureState = viewModel.profilePicture.collectAsStateWithLifecycle()



    val dashboardMenu = getDashboardModel()
    var productsListing by remember { mutableStateOf(listOf<CategoryProduct>()) }

    var isLoading by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    when (val state = homeState.value) {
        is HomeState.Idle -> {
            isLoading = false
        }

        is HomeState.Loading -> {
            isLoading = true
        }

        is HomeState.Success -> {
            isLoading = false
            productsListing = state.data.categories
        }

        is HomeState.Error -> {
            isLoading = false
            snackBar.showError("Error: ${state.message}")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 27.dp, vertical = 20.dp)
        ) {
            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if(profilePictureState.value.isEmpty()){
                        AvatarIcon(50.dp, vectorResource(Res.drawable.avatar_line))
                    }else {
                        AsyncImage(
                            model = profilePictureState.value,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .border(
                                    1.dp,
                                    LocalColorsPalette.current.borderColor,
                                    CircleShape
                                )
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = getTimeBasedGreeting(),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = customerNameState.value,
                            style = getPoppinsSemiBold18()
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        Image(
                            painter = painterResource(Res.drawable.cart_icon),
                            contentDescription = "cart",
                            modifier = Modifier.clickable {
                                goToCartScreen.invoke()
                            }
                        )
                        if (cartState.value > 0) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = 6.dp, y = (-6).dp)
                                    .background(Color.Red, shape = CircleShape)
                                    .padding(0.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (cartState.value > 99) "99+" else cartState.value.toString(),
                                    color = Color.White,
                                    style = getPoppinsSemiBold12(),
                                    modifier = Modifier.padding(0.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    // TODO:  implement this  later
                    Image(
                        painter = painterResource(Res.drawable.notification_icon),
                        contentDescription = "notifications"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search and Settings Section
            Row(
                modifier = Modifier.fillMaxWidth().clickable {
                    goToSearchDialog.invoke()
                },
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LinerBackground(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.magnifer_icon),
                            contentDescription = "search"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Search",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF252525)
                        )
                    }
                }
                LinerBackground {
                    Box(
                        modifier = Modifier.padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.settings_icon),
                            contentDescription = "settings"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Services Section
            Banner(
                bannerColor = Color(0xFFE8492A),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Our Services",
                    color = Color(0xFF252525),
                    style = getPoppinsBold16()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            ServicesGrid(
                services = dashboardMenu,
                modifier = Modifier.fillMaxWidth(),
                goToMenuItem
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Join Team Text
            Text(
                text = "Join our Team of Cleaners!",
                color = Color(0xFFF09103),
                style = getPoppinsSemiBold20().copy(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.fillMaxWidth().clickable{
                    goToCleanersRegistration()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Products Section
            Banner(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Our Products",
                    color = Color(0xFF252525),
                    style = getPoppinsBold16()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isLoading) {
                ProductListingShimmer()
            } else {
                if (productsListing.isEmpty()) {
                    EmptyState("No Product!", "Products would be available soon")
                } else {
                    ProductListing(
                        productCategory = productsListing,
                        getCategoryProduct,
                        goToProductListing,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp)
        )
    }
}

@Composable
fun ProductListing(
    productCategory: List<CategoryProduct>,
    getCategoryProduct: (String) -> Unit,
    goToProductListing: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        productCategory.forEach { category ->
            ProductGrid(
                goToProductListing = goToProductListing,
                getCategoryProduct = getCategoryProduct,
                category = category
            )
        }
    }
}

@Composable
fun ProductGrid(
    goToProductListing: (Int, String) -> Unit,
    getCategoryProduct: (String) -> Unit,
    category: CategoryProduct,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Category Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.category,
                color = Color(0xFF252525),
                style = getPoppinsMedium16()
            )
            Text(
                text = "View all",
                color = Color(0xFFF09103),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.clickable {
                    goToProductListing.invoke(category.categoryId, category.category)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Products Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(category.products.size) { index ->
                ProdItem(
                    getCategoryProduct = getCategoryProduct,
                    product = category.products[index]
                )
            }
        }
    }
}

@Composable
fun ProdItem(
    getCategoryProduct: (String) -> Unit,
    product: ProductItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(169.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFFEF9F2))
            .clickable {
                getCategoryProduct(NavigationUtils.encodeObject(product))
            }
    ) {
        // Product Image
        AsyncImage(
            model = product.images.firstOrNull() ?: "",
            contentDescription = product.product?.name ?: "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
        )

        // Product Details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Text(
                text = product.product?.name ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF716F6D),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Price and Quantity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₦${product.product?.price?.formatBalance()} | (${product.product?.quantity}) items",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFF09103)
                )
            }
        }
    }
}


@Composable
private fun ServicesGrid(
    services: List<DashboardModel>,
    modifier: Modifier = Modifier,goToMenuItem: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(50.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(services.size) { index ->
            MenuItem(services[index],goToMenuItem)
        }
    }
}