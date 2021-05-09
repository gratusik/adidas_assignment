package com.gratus.adidasconfirmed.interactors

import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.domain.repository.ProductListRepository
import com.gratus.adidasconfirmed.interactors.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

// get product list use case which interact with view model and repository to get data
class GetProductListUseCase @Inject constructor(private val repository: ProductListRepository) :
    SingleUseCase<ArrayList<ProductListResponseItem>>() {
    override fun buildUseCaseSingle(): Single<ArrayList<ProductListResponseItem>> {
        return repository.getProductList()
    }
}
