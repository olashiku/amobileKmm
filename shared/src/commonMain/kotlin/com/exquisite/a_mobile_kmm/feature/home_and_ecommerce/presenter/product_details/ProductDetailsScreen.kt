package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_details

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.cart_icon
import amobilekmm.shared.generated.resources.search_icon
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.QuantityCounter
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold20
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold28
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productItem:ProductItem,
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    viewModel: ProductDetailsViewModel = koinViewModel<ProductDetailsViewModel>(),
    modifier: Modifier = Modifier
) {
    var quantity by remember {mutableStateOf(1)}
    val pagerState = rememberPagerState(pageCount = { productItem.images.size })
    val scope = rememberCoroutineScope()
    val (snackBar, snackBarHostState) = rememberSnackBar()

    val cartCount = viewModel.cartState.collectAsStateWithLifecycle().value


    Box(
        modifier = Modifier.fillMaxSize().background(
            color = Color(0xFFF8F9FA)
        )
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 64.dp, bottom = 100.dp) // Space for header and button
        ) {
            // Image Gallery Section
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(380.dp)
                    ) { page ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = productItem.images[page],
                                contentDescription = "Product image ${page + 1}",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    // Pager Indicators
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(pagerState.pageCount) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(
                                        width = if (isSelected) 24.dp else 8.dp,
                                        height = 8.dp
                                    )
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        if (isSelected) Color(0xFFF29100)
                                        else Color(0xFFE2E8F0)
                                    )
                                    .clickable {
                                        scope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Product Name
                    Text(
                        text = productItem.product?.name ?: "",
                        style = getPoppinsBold20(),
                        color = Color(0xFF0F172A),
                        lineHeight = 28.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Price and Stock Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Price
                        Text(
                            text = "₦${productItem.product?.price?.formatBalance()}",
                            style = getPoppinsBold28(),
                            color = Color(0xFFF29100)
                        )

                        // Stock Status
                        val stockQuantity = productItem.product?.quantity ?: 0
                        val isLowStock = stockQuantity in 1..10
                        val isOutOfStock = stockQuantity <= 0

                        Surface(
                            color = when {
                                isOutOfStock -> Color(0xFFFEE2E2)
                                isLowStock -> Color(0xFFFEF3C7)
                                else -> Color(0xFFDCFCE7)
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (!isOutOfStock) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = when {
                                            isLowStock -> Color(0xFFD97706)
                                            else -> Color(0xFF10B981)
                                        },
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = when {
                                        isOutOfStock -> "Out of Stock"
                                        isLowStock -> "Only $stockQuantity left"
                                        else -> "In Stock"
                                    },
                                    style = getPoppinsSemiBold14(),
                                    color = when {
                                        isOutOfStock -> Color(0xFFEF4444)
                                        isLowStock -> Color(0xFFD97706)
                                        else -> Color(0xFF10B981)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "PRODUCT DESCRIPTION",
                        style = getPoppinsBold14(),
                        color = Color(0xFF64748B),
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = productItem.product?.description ?: "",
                        style = getPoppinsRegular14(),
                        color = Color(0xFF475569),
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Fixed Header at Top
        Surface(
            modifier = Modifier.align(Alignment.TopCenter),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF0F172A)
                    )
                }

                Text(
                    text = "Product Details",
                    style = getPoppinsSemiBold18(),
                    color = Color(0xFF0F172A)
                )

                Row {
                    IconButton(onClick = onSearchClick) {
                        Image(
                            painter = painterResource(Res.drawable.search_icon),
                            contentDescription = "Search"
                        )
                    }

                    if (cartCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = Color(0xFFEF4444)
                                ) {
                                    Text(
                                        text = if (cartCount > 99) "99+" else cartCount.toString(),
                                        style = getPoppinsBold12(),
                                        color = Color.White
                                    )
                                }
                            }
                        ) {
                            IconButton(onClick = onCartClick) {
                                Image(
                                    painter = painterResource(Res.drawable.cart_icon),
                                    contentDescription = "Cart"
                                )
                            }
                        }
                    } else {
                        IconButton(onClick = onCartClick) {
                            Image(
                                painter = painterResource(Res.drawable.cart_icon),
                                contentDescription = "Cart"
                            )
                        }
                    }
                }
            }
        }

        // Fixed Action Section at Bottom
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                // Price Summary Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Total Price",
                            style = getPoppinsMedium14(),
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = "₦${((productItem.product?.price ?: 0.0) * quantity).formatBalance()}",
                            style = getPoppinsBold20(),
                            color = Color(0xFF0F172A)
                        )
                    }

                    // Quantity Counter
                    QuantityCounter(
                        initialQuantity = 1,
                        onQuantityChange = { newQuantity ->
                            quantity = newQuantity
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Add to Cart Button
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {
                            viewModel.addToCart(
                                CartModel(
                                    productItem.product?.id ?: 0,
                                    productItem.product?.name ?: "",
                                    productItem.images[0],
                                    productItem.product?.price ?: 0.0,
                                    quantity
                                )
                            )
                            snackBar.showSuccess("Item added to cart successfully")
                        },
                    color = Color(0xFFF29100),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add to Cart",
                            style = getPoppinsSemiBold16(),
                            color = Color.White
                        )
                    }
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


