package com.gratus.adidasconfirmed.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gratus.adidasconfirmed.data.service.local.db.AdidasDataBase
import com.gratus.adidasconfirmed.data.service.remote.ProductDetailsService
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.util.MockResponseUtil
import com.gratus.adidasconfirmed.util.RxSchedulerRule
import io.reactivex.Single
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProductDetailsDataSourceTest : TestCase() {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var productDetailsDataSource: ProductDetailsDataSource

    @Mock
    private lateinit var productDetailsService: ProductDetailsService

    @Mock
    private lateinit var adidasDataBase: AdidasDataBase

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        productDetailsDataSource = ProductDetailsDataSource(adidasDataBase, productDetailsService)
    }

    @Test
    fun fetchProductDetails_fromNetwork() {

        // Arrange
        Mockito.`when`(productDetailsService.productDetailsService("HI333"))
            .thenReturn(Single.just(MockResponseUtil.mockTheProduct()))

        // Act 
        val productListResponseItem: Single<ProductListResponseItem> =
            productDetailsDataSource.getProductDetails("HI333")

        // Assert
        assertEquals(
            productListResponseItem.blockingGet().id,
            MockResponseUtil.mockTheProduct().id
        )
    }
}
