package com.gratus.adidasconfirmed.interactors

import androidx.lifecycle.LiveData
import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel
import com.gratus.adidasconfirmed.domain.repository.ProductDetailsRepository
import javax.inject.Inject

// insert, delete and get favourite products from local room db
class InsertDeleteProductDetailsCacheUseCase @Inject constructor(private val repository: ProductDetailsRepository) {

    fun insertProductDetailsCache(productModel: ProductModel) {
        repository.insertProductDetails(productModel)
    }

    fun deleteProductDetailsCache(productId: String) {
        repository.deleteProductDetails(productId)
    }

    fun getProductDetailsCache(productId: String): LiveData<ProductModel> {
        return repository.getProductDetailsLocal(productId)
    }
}
