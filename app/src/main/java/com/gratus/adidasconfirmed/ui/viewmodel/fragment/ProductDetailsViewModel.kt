package com.gratus.adidasconfirmed.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.interactors.GetProductDetailsUseCase
import com.gratus.adidasconfirmed.interactors.InsertDeleteProductDetailsCacheUseCase
import com.gratus.adidasconfirmed.ui.viewmodel.state.ProductDetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val insertDeleteProductDetailsCacheUseCase: InsertDeleteProductDetailsCacheUseCase
) : ViewModel() {
    val productDetailsState = MutableLiveData<ProductDetailsState>()
    var productDetails: MutableLiveData<ProductListResponseItem> =
        MutableLiveData<ProductListResponseItem>()

    // interact with model to get the data - product details
    fun getProductDetails(productId: String) {
        productDetailsState.value = ProductDetailsState.Loading(true)
        getProductDetailsUseCase.getProductId(productId)
        getProductDetailsUseCase.execute(
            onSuccess = {
                productDetailsState.value = ProductDetailsState.ProductDetailsSuccess(it)
                productDetailsState.value = ProductDetailsState.Loading(false)
                productDetails.value = it
            },
            onError = {
                productDetailsState.value = ProductDetailsState.Error(it.localizedMessage)
                productDetailsState.value = ProductDetailsState.Loading(false)
            }
        )
    }

    fun insertProductDetails(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val productModel = ProductModel(productId, 1)
            insertDeleteProductDetailsCacheUseCase.insertProductDetailsCache(productModel)
        }
    }

    fun deleteProductDetails(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            insertDeleteProductDetailsCacheUseCase.deleteProductDetailsCache(productId)
        }
    }

    fun getProductDetailsCache(productId: String): LiveData<ProductModel> {
        return insertDeleteProductDetailsCacheUseCase.getProductDetailsCache(productId)
    }
}
