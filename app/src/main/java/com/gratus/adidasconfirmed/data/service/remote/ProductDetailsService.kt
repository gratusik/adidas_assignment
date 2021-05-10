package com.gratus.adidasconfirmed.data.service.remote

import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.PRODUCT_ID
import com.gratus.adidasconfirmed.util.constants.ServiceConstants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

// get product details service 
interface ProductDetailsService {
    @GET(ServiceConstants.PRODUCT_DETAILS_URL)
    fun productDetailsService(@Path(PRODUCT_ID) productId: String): Single<ProductListResponseItem>
}
