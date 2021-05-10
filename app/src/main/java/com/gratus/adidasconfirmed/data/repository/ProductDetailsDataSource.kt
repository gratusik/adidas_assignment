package com.gratus.adidasconfirmed.data.repository

import androidx.lifecycle.LiveData
import com.gratus.adidasconfirmed.data.service.local.db.AdidasDataBase
import com.gratus.adidasconfirmed.data.service.remote.ProductDetailsService
import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.domain.repository.ProductDetailsRepository
import io.reactivex.Single
import javax.inject.Inject

// data source which hits the service api
class ProductDetailsDataSource @Inject constructor(
    private var adidasDataBase: AdidasDataBase,
    private var productDetailsService: ProductDetailsService
) : ProductDetailsRepository {

    override fun getProductDetails(productId: String): Single<ProductListResponseItem> {
        return productDetailsService.productDetailsService(productId)
    }

    // insert favourite products to room
    override fun insertProductDetails(productModel: ProductModel) {
        adidasDataBase.productDao().insert(productModel)
    }

    // delete when  products is removed from favorite to room
    override fun deleteProductDetails(productId: String) {
        adidasDataBase.productDao().deleteByProductId(productId)
    }

    // get product favorite details to toggle favourite icon in the details page
    override fun getProductDetailsLocal(productId: String): LiveData<ProductModel> {
        return adidasDataBase.productDao().getProductDetails(productId)
    }
}
