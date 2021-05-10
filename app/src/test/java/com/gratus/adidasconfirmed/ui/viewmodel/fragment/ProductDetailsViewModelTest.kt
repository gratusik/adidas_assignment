package com.gratus.adidasconfirmed.ui.viewmodel.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel
import com.gratus.adidasconfirmed.domain.repository.ProductDetailsRepository
import com.gratus.adidasconfirmed.interactors.GetProductDetailsUseCase
import com.gratus.adidasconfirmed.interactors.InsertDeleteProductDetailsCacheUseCase
import com.gratus.adidasconfirmed.ui.viewmodel.state.ProductDetailsState
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
class ProductDetailsViewModelTest : TestCase() {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var productDetailsRepository: ProductDetailsRepository

    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase

    private lateinit var insertDeleteProductDetailsCacheUseCase: InsertDeleteProductDetailsCacheUseCase

    private lateinit var productDetailsViewModel: ProductDetailsViewModel

    @Mock
    private lateinit var productDetailsState: Observer<ProductDetailsState>

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        setUpUseCases()
        setUpViewModel()
    }

    private fun setUpUseCases() {
        getProductDetailsUseCase =
            GetProductDetailsUseCase(productDetailsRepository)
        insertDeleteProductDetailsCacheUseCase =
            InsertDeleteProductDetailsCacheUseCase(productDetailsRepository)
    }

    private fun setUpViewModel() {
        productDetailsViewModel = ProductDetailsViewModel(
            getProductDetailsUseCase,
            insertDeleteProductDetailsCacheUseCase
        )
        productDetailsViewModel.productDetailsState.observeForever(productDetailsState)
    }

    @Test
    fun fetchProductDetails_returnsError() {
        getProductDetailsUseCase.getProductId("HI333")
        // Arrange
        Mockito.`when`(getProductDetailsUseCase.buildUseCaseSingle())
            .thenReturn(Single.error(Throwable("error")))

        // Act

        productDetailsViewModel.getProductDetails("HI333")

        // Assert
        Mockito.verify(productDetailsState).onChanged(ProductDetailsState.Loading(true))
        Mockito.verify(productDetailsState).onChanged(ProductDetailsState.Error("error"))
        Mockito.verify(productDetailsState).onChanged(ProductDetailsState.Loading(false))
    }

    @Test
    fun fetchProductDetails_returnsResponse() {
        getProductDetailsUseCase.getProductId("HI333")
        // Arrange
        Mockito.`when`(getProductDetailsUseCase.buildUseCaseSingle())
            .thenReturn(Single.just(MockResponseUtil.mockTheProduct()))

        // Act

        productDetailsViewModel.getProductDetails("HI333")

        // Assert
        Mockito.verify(productDetailsState).onChanged(ProductDetailsState.Loading(true))
        Mockito.verify(productDetailsState)
            .onChanged(ProductDetailsState.ProductDetailsSuccess(MockResponseUtil.mockTheProduct()))
        Mockito.verify(productDetailsState).onChanged(ProductDetailsState.Loading(false))
    }

    @Test
    fun getFavouriteLocal() {
        val productModel: MutableLiveData<ProductModel> = MutableLiveData()
        productModel.postValue(MockResponseUtil.mockTheProductLocal())
        insertDeleteProductDetailsCacheUseCase.insertProductDetailsCache(MockResponseUtil.mockTheProductLocal())
        // Arrange
        Mockito.`when`(insertDeleteProductDetailsCacheUseCase.getProductDetailsCache("HI333"))
            .thenReturn(productModel)

        // Act
        val productModelResponse: LiveData<ProductModel> =
            productDetailsViewModel.getProductDetailsCache("HI333")

        // Assert
        assertEquals(
            productModelResponse.value?.productId,
            MockResponseUtil.mockTheProductLocal().productId
        )
    }
}
