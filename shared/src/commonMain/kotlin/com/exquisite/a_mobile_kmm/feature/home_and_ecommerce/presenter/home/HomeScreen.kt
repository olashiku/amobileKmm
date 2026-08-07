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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
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

            // Join Team CTA Banner
            CleanersRecruitmentBanner(
                onClick = { goToCleanersRegistration() }
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
    val quantity = product.product?.quantity ?: 0
    val isLowStock = quantity in 1..10
    val isOutOfStock = quantity <= 0

    Card(
        modifier = modifier.width(180.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    getCategoryProduct(NavigationUtils.encodeObject(product))
                }
        ) {
            // Image Container with Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                // Product Image
                AsyncImage(
                    model = product.images.firstOrNull() ?: "",
                    contentDescription = product.product?.name ?: "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF8F9FA))
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )

                // Out of Stock Overlay
                if (isOutOfStock) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "OUT OF STOCK",
                                style = getPoppinsBold14(),
                                color = Color(0xFFEF4444),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Low Stock Badge - Top Left
                if (isLowStock && !isOutOfStock) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                        color = Color(0xFFFEF3C7),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Only $quantity left",
                            style = getPoppinsSemiBold12(),
                            color = Color(0xFFD97706),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Product Details Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Product Name
                Text(
                    text = product.product?.name ?: "",
                    style = getPoppinsSemiBold14(),
                    color = Color(0xFF0F172A),
                    maxLines = 2,
                    lineHeight = 18.sp,
                    modifier = Modifier.height(36.dp)
                )

                // Price Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "₦${product.product?.price?.formatBalance()}",
                            style = getPoppinsBold16(),
                            color = Color(0xFFF29100)
                        )

                        // Stock Status Text
                        if (!isOutOfStock) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(
                                            if (isLowStock) Color(0xFFFBBF24) else Color(0xFF10B981),
                                            CircleShape
                                        )
                                )
                                Text(
                                    text = "$quantity in stock",
                                    style = getPoppinsRegular12(),
                                    color = Color(0xFF64748B)
                                )
                            }
                        }
                    }

                    // Quick Add to Cart Button
                    if (!isOutOfStock) {
                        Surface(
                            modifier = Modifier.size(36.dp),
                            color = Color(0xFFF29100),
                            shape = CircleShape,
                            shadowElevation = 2.dp
                        ) {
                            IconButton(
                                onClick = { /* TODO: Quick add to cart */ },
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ShoppingCart,
                                    contentDescription = "Add to Cart",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp).clickable{
                                        getCategoryProduct(NavigationUtils.encodeObject(product))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CleanersRecruitmentBanner(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8ED)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left section: Icon and text
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon container
                Surface(
                    modifier = Modifier.size(56.dp),
                    color = Color(0xFFF29100),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Join Team",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Text content
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Become a Cleaner",
                        style = getPoppinsSemiBold16(),
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "Join our team today",
                        style = getPoppinsMedium14(),
                        color = Color(0xFF64748B)
                    )
                }
            }

            // Right section: Arrow button
            Surface(
                modifier = Modifier.size(40.dp),
                color = Color(0xFFF29100),
                shape = CircleShape
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Join",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
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