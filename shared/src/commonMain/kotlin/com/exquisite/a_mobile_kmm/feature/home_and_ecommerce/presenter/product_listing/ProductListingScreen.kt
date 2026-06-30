package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_listing

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.cart_icon
import amobilekmm.shared.generated.resources.search_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.MenuItem
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.DashboardModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.Product
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductsListModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home.ProdItem
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListingScreen(
    categoryId: Int,
    categoryName:String,
    onBackClick : () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onProductClick:(String) -> Unit = {},
    viewModel: ProductListingViewModel = koinViewModel<ProductListingViewModel>(),
    modifier: Modifier = Modifier
) {

    val productListingState = viewModel.productListingState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var productList  by remember { mutableStateOf<ProductsListModel?>(null) }
    val cartItemCount = 0

    LaunchedEffect(Unit) {
        viewModel.loadProductsByCategory(categoryId)
    }


    when (val state = productListingState.value) {
        is ProductListingState.Idle -> {
            viewModel.clearState()
        }

        is ProductListingState.Loading -> {
            LoadingDialog(true)
        }

        is ProductListingState.Success -> {
            productList = state.data
        }

        is ProductListingState.Error -> {
            snackBar.showError("Error: ${state.message}")
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = categoryName,
                        style = getPoppinsSemiBold18(),
                        color = Color(0xFF252525),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Image(
                            painter = painterResource(Res.drawable.back_arrow),
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Image(
                            painter = painterResource(Res.drawable.search_icon),
                            contentDescription = "searchIcon",
                        )
                    }
                    BadgedBox(
                        badge = {
                            if (cartItemCount > 0) {
                                Badge { Text(text = cartItemCount.toString()) }
                            }
                        }
                    ) {
                        IconButton(onClick = onCartClick) {
                            Image(
                                painter = painterResource(Res.drawable.cart_icon),
                                contentDescription = "cartIcon",
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFFFFF), // light gray background
                ),
            )
            // Orange bottom divider
            HorizontalDivider(
                thickness = 2.dp,
                color = Color(0xFFFFA500),
            )
        }

        Spacer(modifier = modifier.height(22.dp))
        ProductListGrid(productList!!,onProductClick)


        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ProductListGrid(
    productListModel: ProductsListModel,
    onProductClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(50.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(productListModel.products.size) { index ->
         //   ProdItem(getCategoryProduct = onProductClick,product = productListModel.products[index])

        }
    }
}



