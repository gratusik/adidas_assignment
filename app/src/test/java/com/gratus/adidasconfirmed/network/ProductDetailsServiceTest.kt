package com.gratus.adidasconfirmed.network

import com.gratus.adidasconfirmed.data.service.remote.ProductDetailsService
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.util.MockResponseUtil
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class ProductDetailsServiceTest : MockServerSetup<ProductDetailsService>() {
    @Mock
    private lateinit var productDetailsService: ProductDetailsService

    @Before
    fun initService() {
        productDetailsService = retrofitService(ProductDetailsService::class.java)
    }

    @Test
    fun getProductDetails_fromNetwork() {
        enqueueResponse("productDetails.json", 200)
        val response = productDetailsService.productDetailsService("HI333")
        val productListResponseItem: ProductListResponseItem = response.blockingGet()
        assertEquals(productListResponseItem.price, MockResponseUtil.mockedProductsList()[0].price)
        assertEquals(
            productListResponseItem.imgUrl,
            MockResponseUtil.mockedProductsList()[0].imgUrl
        )
    }
}
