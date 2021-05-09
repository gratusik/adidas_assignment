package com.gratus.adidasconfirmed.network

import com.gratus.adidasconfirmed.data.service.remote.ProductListService
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.util.MockResponseUtil
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class ProductListServiceTest : MockServerSetup<ProductListService>() {
    @Mock
    private lateinit var productListService: ProductListService

    @Before
    fun initService() {
        productListService = retrofitService(ProductListService::class.java)
    }

    @Test
    fun getProductList_fromNetwork() {
        enqueueResponse("productList.json", 200)
        val response = productListService.productListService()
        val productListResponseItem: ProductListResponseItem = response.blockingGet()[0]
        assertEquals(productListResponseItem.price, MockResponseUtil.mockedProductsList()[0].price)
        assertEquals(
            productListResponseItem.imgUrl,
            MockResponseUtil.mockedProductsList()[0].imgUrl
        )
    }
}
