package com.gratus.adidasconfirmed.ui.viewmodel.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gratus.adidasconfirmed.domain.repository.ProductListRepository
import com.gratus.adidasconfirmed.interactors.GetProductListUseCase
import com.gratus.adidasconfirmed.ui.viewmodel.state.ProductListState
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
class ProductListViewModelTest : TestCase() {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var productListRepository: ProductListRepository

    private lateinit var getProductListUseCase: GetProductListUseCase

    private lateinit var productListViewModel: ProductListViewModel

    @Mock
    private lateinit var productListState: Observer<ProductListState>

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        setUpUseCases()
        setUpViewModel()
    }

    private fun setUpUseCases() {
        getProductListUseCase =
            GetProductListUseCase(productListRepository)
    }

    private fun setUpViewModel() {
        productListViewModel = ProductListViewModel(
            getProductListUseCase
        )
        productListViewModel.productListState.observeForever(productListState)
    }

    @Test
    fun fetchProductList_returnsError() {

        // Arrange
        Mockito.`when`(getProductListUseCase.buildUseCaseSingle())
            .thenReturn(Single.error(Throwable("error")))

        // Act
        productListViewModel.getProductList()

        // Assert
        Mockito.verify(productListState).onChanged(ProductListState.Loading(true))
        Mockito.verify(productListState).onChanged(ProductListState.Error("error"))
        Mockito.verify(productListState).onChanged(ProductListState.Loading(false))
    }

    @Test
    fun fetchProductList_returnsResponse() {
        // Arrange
        Mockito.`when`(getProductListUseCase.buildUseCaseSingle())
            .thenReturn(Single.just(MockResponseUtil.mockedProductsList()))

        // Act
        productListViewModel.getProductList()

        // Assert
        Mockito.verify(productListState).onChanged(ProductListState.Loading(true))
        Mockito.verify(productListState)
            .onChanged(ProductListState.ProductListSuccess(MockResponseUtil.mockedProductsList()))
        Mockito.verify(productListState).onChanged(ProductListState.Loading(false))
    }
}
