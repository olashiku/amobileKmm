package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.data_source

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.dao.CategoryProductDao
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.dao.ProductDao
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity.CategoryProductEntity
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductDataSource(
    private val categoryProductDao: CategoryProductDao,
    private val productDao: ProductDao
) {

    fun getAllCategoryProducts(): Flow<List<CategoryProductEntity>> {
        return categoryProductDao.getAllCategoryProducts()
    }

    suspend fun saveCategoryProducts(categoryProducts: List<CategoryProductEntity>) {
        categoryProductDao.deleteAll()
        categoryProductDao.insertAll(categoryProducts)
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun getProductsByCategory(categoryId: Int): Flow<List<ProductEntity>> {
        return productDao.getProductsByCategory(categoryId)
    }

    suspend fun saveProducts(products: List<ProductEntity>) {
        productDao.deleteAll()
        productDao.insertAll(products)
    }
}
