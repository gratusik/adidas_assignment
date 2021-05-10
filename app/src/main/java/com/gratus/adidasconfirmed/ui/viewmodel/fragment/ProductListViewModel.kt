package com.gratus.adidasconfirmed.ui.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.interactors.GetProductListUseCase
import com.gratus.adidasconfirmed.ui.viewmodel.state.ProductListState
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase
) : ViewModel() {
    val productListState = MutableLiveData<ProductListState>()
    private var productList: ArrayList<ProductListResponseItem> = ArrayList()

    // interact with model to get the data - product list
    fun getProductList() {
        productListState.value = ProductListState.Loading(true)
        getProductListUseCase.execute(
            onSuccess = {
                productListState.value = ProductListState.ProductListSuccess(it)
                productListState.value = ProductListState.Loading(false)
                productList = it
            },
            onError = {
                productListState.value = ProductListState.Error(it.localizedMessage)
                productListState.value = ProductListState.Loading(false)
            }
        )
    }

    // search on text changed filter in product list
    fun onTextChanged(s: CharSequence) {
        val searchList = this.productList.filter {
            it.name.contains(s.toString()) || it.description.contains(s.toString())
        }
        productListState.postValue(ProductListState.ProductListSuccess(searchList as ArrayList<ProductListResponseItem>))
    }
}
