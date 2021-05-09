package com.gratus.adidasconfirmed.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gratus.adidasconfirmed.data.service.remote.ProductListService
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
import org.mockito.Mockito.atLeastOnce
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProductListDataSourceTest : TestCase() {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var productListDataSource: ProductListDataSource

    @Mock
    private lateinit var productListService: ProductListService

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        productListDataSource = ProductListDataSource(productListService)
    }

    @Test
    fun fetchProductList_fromNetwork() {

        // Arrange
        Mockito.`when`(productListService.productListService())
            .thenReturn(Single.just(MockResponseUtil.mockedProductsList()))

        // Act
        productListDataSource.getProductList().test()

        // Assert
        Mockito.verify(productListService, atLeastOnce()).productListService()
    }
}
