package com.gratus.adidasconfirmed.data.repository

import com.gratus.adidasconfirmed.data.service.remote.ProductListService
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.domain.repository.ProductListRepository
import io.reactivex.Single
import javax.inject.Inject

// data source which hits the service api
class ProductListDataSource @Inject constructor(
    private var productListService: ProductListService
) : ProductListRepository {

    override fun getProductList(): Single<ArrayList<ProductListResponseItem>> {
        return productListService.productListService()
    }
}
