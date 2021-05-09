package com.gratus.adidasconfirmed.data.service.remote

import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.util.constants.ServiceConstants
import io.reactivex.Single
import retrofit2.http.GET

// get product list service 
interface ProductListService {
    @GET(ServiceConstants.PRODUCT_LIST_URL)
    fun productListService(): Single<ArrayList<ProductListResponseItem>>
}
