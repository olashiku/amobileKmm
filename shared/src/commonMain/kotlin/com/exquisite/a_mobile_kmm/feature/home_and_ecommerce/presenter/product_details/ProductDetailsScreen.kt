package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_details

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.cart_icon
import amobilekmm.shared.generated.resources.cart_icon_white
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButtonWithIcon
import com.exquisite.a_mobile_kmm.core.screen_components.QuantityCounter
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold24
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsLight16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular24
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
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
    cartItemCount: Int = 2,
    viewModel: ProductDetailsViewModel = koinViewModel<ProductDetailsViewModel>(),
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { productItem.images.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize().background(
            color = Color.White
        )
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp) // Space for the button
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Product Details",
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
                                contentDescription = "Back",
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
                                    contentDescription = "Back",
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
            Column{

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                ) { page ->
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        AsyncImage(
                            model = productItem.images[page],
                            contentDescription = "product images",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = modifier.height(20.dp))
                Column(
                    modifier = modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.wrapContentHeight()
                    ) {
                        repeat(pagerState.pageCount) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) Color(0xFFF09103)
                                        else Color.Transparent
                                    )
                                    .then(
                                        if (!isSelected) {
                                            Modifier.border(1.dp, Color(0xFFBEBEBE), CircleShape)
                                        } else Modifier
                                    )
                                    .clickable { scope.launch { pagerState.animateScrollToPage(index) } }
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(24.dp)){
                Text(text = productItem.product?.name?:"",
                    style = getPoppinsBold24(),
                    color = Color(0xFF252525))

                Spacer(modifier = modifier.height(8.dp))
                Text(text = "₦${productItem.product?.price?.formatBalance()}",
                    style = getPoppinsRegular24(),
                    color = Color(0xFF252525))
                Spacer(modifier = modifier.height(18.dp))
                Text(text = "PRODUCT DETAILS",
                    letterSpacing = 1.5.sp,
                    style = getPoppinsBold12(),
                    color = Color(0xFF252525))
                Spacer(modifier = modifier.height(10.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFEEEEEE),
                )
                Spacer(modifier = modifier.height(15.dp))
                Text(
                    text = productItem.product?.description?:"",
                    style = getPoppinsLight16(),
                    color = Color(0xFF252525),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Justify
                )
                Spacer(modifier = modifier.height(40.dp))

            }
        }

        // Fixed button at the bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ){
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEEEEEE))
            Spacer(modifier = modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
                QuantityCounter(
                    modifier = modifier.weight(2f),
                    initialQuantity = 1,
                    onQuantityChange = { newQuantity ->
                        println("Quantity changed to: $newQuantity")
                    }
                )
                PrimaryButtonWithIcon(
                    "Add to Cart",
                    Res.drawable.cart_icon_white,
                    {

                    },
                    modifier = modifier.weight(3f)
                )
            }

        }

    }
}


