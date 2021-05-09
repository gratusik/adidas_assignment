package com.gratus.adidasconfirmed.domain.repository

import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import io.reactivex.Single

// repo to get product list
interface ProductListRepository {
    fun getProductList(): Single<ArrayList<ProductListResponseItem>>
}
