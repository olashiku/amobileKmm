package com.exquisite.a_mobile_kmm.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home.HomeScreen


@Preview(showBackground = true, name = "Home Screen", showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            getCategoryProduct = {},
            goToProductListing = { _, _ -> }
        )
    }
}
