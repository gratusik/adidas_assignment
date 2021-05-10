package com.gratus.adidasconfirmed.domain.repository

import androidx.lifecycle.LiveData
import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import io.reactivex.Single

// repo to get product details, insert,  delete and get favourite products
interface ProductDetailsRepository {
    fun getProductDetails(productId: String): Single<ProductListResponseItem>
    fun insertProductDetails(productModel: ProductModel)
    fun deleteProductDetails(productId: String)
    fun getProductDetailsLocal(productId: String): LiveData<ProductModel>
}
