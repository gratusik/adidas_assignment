package com.gratus.adidasconfirmed.interactors

import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.domain.repository.ProductDetailsRepository
import com.gratus.adidasconfirmed.interactors.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

// get product details use case which interact with view model and repository to get data
class GetProductDetailsUseCase @Inject constructor(private val repository: ProductDetailsRepository) :
    SingleUseCase<ProductListResponseItem>() {

    private var productId: String = ""

    fun getProductId(id: String) {
        productId = id
    }

    override fun buildUseCaseSingle(): Single<ProductListResponseItem> {
        return repository.getProductDetails(productId)
    }
}
