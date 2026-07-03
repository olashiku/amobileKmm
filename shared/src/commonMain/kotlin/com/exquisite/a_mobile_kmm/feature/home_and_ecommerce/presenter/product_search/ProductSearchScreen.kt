package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.SearchTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold15
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold20
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSearchScreen(
    onDismiss: () -> Unit,
    onProductSelected: (ProductItem) -> Unit = {},
    viewModel: ProductSearchViewModel = koinViewModel<ProductSearchViewModel>(),
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = Color.White,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFE0E0E0))
            )
        }
    ) {
        ProductSearchContent(
            onProductSelected = onProductSelected,
            viewModel = viewModel,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ProductSearchContent(
    onProductSelected: (ProductItem) -> Unit,
    viewModel: ProductSearchViewModel,
    modifier: Modifier = Modifier
) {
    val productSearchState = viewModel.productSearchState.collectAsStateWithLifecycle()
    val (snackBar, snackBarHostState) = rememberSnackBar()
    var searchQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }

    // Only trigger search when searchQuery changes (with debounce)
    LaunchedEffect(searchQuery) {
        delay(300) // Debounce for 300ms
        viewModel.searchProducts(searchTerm = searchQuery.ifEmpty { null }, resetSearch = true)
    }

    // Detect when user scrolls to bottom
    val isScrolledToBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            totalItemsCount > 0 && lastVisibleItemIndex >= totalItemsCount - 3
        }
    }

    LaunchedEffect(isScrolledToBottom) {
        if (isScrolledToBottom) {
            viewModel.loadMoreProducts()
        }
    }

    when (val result = productSearchState.value) {
        is ProductSearchState.Error -> {
            snackBar.showError(result.message)
        }
        else -> {}
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Search Products",
                style = getPoppinsBold20(),
                color = Color(0xFF1A1D1E)
            )
            Spacer(modifier = Modifier.height(15.dp))

            SearchTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "Search...",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            when (val state = productSearchState.value) {
                is ProductSearchState.Loading -> {
                    ProductSearchListSkeleton(itemCount = 5)
                }

                is ProductSearchState.Success -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.weight(1f)
                    ) {
                        itemsIndexed(
                            items = state.data,
                            key = { index, item -> "product_$index" }
                        ) { index, item ->
                            SearchItem(
                                product = item,
                                onClick = { onProductSelected(item) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        if (state.isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    SearchItemSkeleton()
                                }
                            }
                        }
                    }
                }

                is ProductSearchState.Idle -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Start searching for products",
                            style = getPoppinsRegular12(),
                            color = Color.Gray
                        )
                    }
                }

                is ProductSearchState.Error -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            style = getPoppinsRegular12(),
                            color = Color.Red
                        )
                    }
                }
            }
        }

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
fun SearchItem(
    product: ProductItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = Color(0xFFF1F3F5),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(12.dp),
    ) {
            AsyncImage(
                model = product.images.firstOrNull() ?: "",
                contentDescription = product.product?.name ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = product.product?.name ?: "",
                    style = getPoppinsBold15(),
                    textAlign = TextAlign.Start,
                    color = Color(0XFF1A1D1E)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "(${product.product?.quantity} items)",
                    style = getPoppinsRegular12(),
                    textAlign = TextAlign.Start,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "₦${product.product?.price?.formatBalance()}",
                    style = getPoppinsBold16(),
                    textAlign = TextAlign.Start,
                    color = Color(0XFFFF8A00)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF4F5F7)),
                contentAlignment = Alignment.Center
            ) {
                Text("+", style = getPoppinsBold12(), color = Color(0xFF1A1D1E))
            }
        }
}
