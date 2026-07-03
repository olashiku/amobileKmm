package com.exquisite.a_mobile_kmm.feature.cart.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold20
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun  CartScreen(toCheckout:()->Unit,
    modifier: Modifier = Modifier, viewModel: CartViewModel = koinViewModel<CartViewModel>()) {

    val state = viewModel.cartState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.getAllItems()
    }

   val cartItems = state.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        // Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "My Cart", color = Color(0xff252525),
                    style = getPoppinsSemiBold18()
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            if(cartItems.isEmpty()){
                EmptyState("Cart Empty!", "You have not added any item to your cart")
            }else{
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)){

                    cartItems.forEach{ item ->
                        CartItemCard(item.productId,item.productImage,item.productName,item.productPrice,item.quantity,viewModel)
                    }

                }
            }
        }

        // Fixed bottom button
        if(cartItems.isNotEmpty()){
            Button(
                onClick = { toCheckout.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangeAccent
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Proceed to Checkout",
                    style = getPoppinsBold16(),
                    color = Color.White
                )
            }
        }

    }
}


private val OrangeAccent   = Color(0xFFFF8C00)
private val SurfaceGray    = Color(0xFFF2F2F2)

@Composable
fun CartItemCard(
    productId:Int,
    imageUrl: String = "",
    name: String = "",
    price: Double = 0.0,
    quantity: Int ,
    viewModel:CartViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {



                // ── Product image ─────────────────────────────────────────────────────
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(14.dp))

                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(Modifier.width(14.dp))

                // ── Name + price ──────────────────────────────────────────────────────
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        style = getPoppinsMedium16(),
                        color = Color(0xFF252525)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "₦${price.formatBalance()}",
                        style = getPoppinsBold18(),
                        color = Color(0xFF252525)
                    )
                }

                Spacer(Modifier.width(8.dp))

                // ── Quantity stepper ──────────────────────────────────────────────────
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Decrease button
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(SurfaceGray)
                            .border(1.dp, Color.LightGray, CircleShape)
                            .clickable {
                                viewModel.removeItem(productId,1,quantity)
                            }
                    ) {
                        Text("−",  style = getPoppinsBold20(),
                            color = Color(0xFF252525))
                    }

                    Text(
                        text = quantity.toString(),
                        style = getPoppinsMedium14(),
                        color = Color(0xFF252525)
                    )

                    // Increase button
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(OrangeAccent)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    viewModel.addItem(productId,name,imageUrl,price,1)
                                }
                            )
                    ) {
                        Text("+", style = getPoppinsBold20(), color = Color.White)
                    }
                }
            }

            // Delete button - positioned at top right
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 8.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFEBEE))
                    .clickable {
                        viewModel.removeItemById(productId)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFEF5350),
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Horizontal divider line
        HorizontalDivider(
            modifier = Modifier.padding(top = 12.dp),
            thickness = 1.dp,
            color = Color(0xFFE0E0E0)
        )
    }
}
