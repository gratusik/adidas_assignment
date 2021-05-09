package com.gratus.adidasconfirmed.ui.viewmodel.state

import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem

sealed class ProductListState {
    data class Loading(var loading: Boolean) : ProductListState()
    data class Error(var message: String) : ProductListState()
    data class ProductListSuccess(val list: ArrayList<ProductListResponseItem>) :
        ProductListState()
}
