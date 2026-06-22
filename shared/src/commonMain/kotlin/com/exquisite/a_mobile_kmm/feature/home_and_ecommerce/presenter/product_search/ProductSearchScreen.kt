package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search

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
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar

@Composable
fun ProductSearchScreen(
    viewModel: ProductSearchViewModel,
    modifier: Modifier = Modifier
) {
    val productSearchState = viewModel.productSearchState.collectAsStateWithLifecycle()
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
        when (val state = productSearchState.value) {
            is ProductSearchState.Idle -> {
                // Initial state
            }

            is ProductSearchState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ProductSearchState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(state.data.products) { product ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Price: $${product.price}", style = MaterialTheme.typography.bodySmall)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            is ProductSearchState.Error -> {
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp).align(Alignment.Center)
                )
            }
        }

        CustomSnackbarHost(
            snackbarHostState = snackBarHostState.second,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
