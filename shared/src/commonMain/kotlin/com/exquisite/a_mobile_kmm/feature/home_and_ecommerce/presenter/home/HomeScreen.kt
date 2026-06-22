package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CategoryProduct
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val homeState = viewModel.homeState.collectAsStateWithLifecycle()
    val snackBarHostState = rememberSnackBar()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        when (val state = homeState.value) {
            is HomeState.Idle -> {
                // Initial state
            }

            is HomeState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is HomeState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item {
                        Text(
                            text = "Products",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    items(state.data.categories) { categoryProduct ->
                        CategorySection(categoryProduct = categoryProduct)
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            is HomeState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        CustomSnackbarHost(
            snackbarHostState = snackBarHostState.second,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CategorySection(categoryProduct: CategoryProduct) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = categoryProduct.category,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        categoryProduct.products.forEach { productItem ->
            ProductItemCard(productItem = productItem)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ProductItemCard(productItem: com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = productItem.product.name,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = productItem.product.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Price: $${productItem.product.price}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Quantity: ${productItem.product.quantity}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
